package com.skhuthon.team4.diary.service;

import com.skhuthon.team4.comment.domain.repository.CommentRepository;
import com.skhuthon.team4.diary.domain.Diary;
import com.skhuthon.team4.diary.domain.repository.DiaryRepository;
import com.skhuthon.team4.diary.dto.DiaryRequestDto;
import com.skhuthon.team4.diary.dto.DiaryResponseDto;
import com.skhuthon.team4.global.exception.BusinessException;
import com.skhuthon.team4.global.exception.ErrorCode;
import com.skhuthon.team4.global.filter.BadWordFilter;
import com.skhuthon.team4.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final CommentRepository commentRepository;
    private final BadWordFilter badWordFilter;

    // POST /api/diaries - 일기 작성 (하루 1개 제한)
    @Transactional
    public DiaryResponseDto createDiary(Member member, DiaryRequestDto request) {
        LocalDate today = LocalDate.now();

        if (diaryRepository.existsByMemberAndDiaryDate(member, today)) {
            throw new BusinessException(ErrorCode.DIARY_ALREADY_EXISTS);
        }

        // 금칙어 체크
        if (badWordFilter.containsBadWord(request.content())) {
            throw new BusinessException(ErrorCode.BAD_WORD_DETECTED);
        }

        Diary diary = Diary.builder()
                .member(member)
                .title(request.title())
                .content(request.content())
                .diaryDate(today)
                .isPublic(request.isPublic() != null ? request.isPublic() : true)
                .build();

        Diary saved = diaryRepository.save(diary);
        return DiaryResponseDto.from(saved, 0);
    }

    // 최신순/랜덤 피드
    public List<DiaryResponseDto> getFeed(String sort) {
        List<Diary> diaries = "random".equalsIgnoreCase(sort)
                ? diaryRepository.findAllRandom()
                : diaryRepository.findAllByIsPublicTrueOrderByCreatedAtDesc();

        return diaries.stream()
                .map(d -> DiaryResponseDto.from(d, commentRepository.countByDiary(d)))
                .toList();
    }

    // 핫 피드
    public List<DiaryResponseDto> getHotFeed() {
        return diaryRepository.findTop10ByIsPublicTrueOrderByEmpathyCountDescCreatedAtDesc()
                .stream()
                .map(d -> DiaryResponseDto.from(d, commentRepository.countByDiary(d)))
                .toList();
    }

    public List<DiaryResponseDto> getMyDiaries(Member member, int year, int month, String visibility) {
        List<Diary> diaries;

        if ("private".equals(visibility)) {
            // 비공개 일기만
            diaries = diaryRepository.findByMemberAndIsPublicFalseOrderByCreatedAtDesc(member);
        } else {
            // 전체 (공개 + 비공개)
            diaries = diaryRepository.findByMemberAndYearAndMonth(member, year, month);
        }

        return diaries.stream()
                .map(d -> DiaryResponseDto.from(d, commentRepository.countByDiary(d)))
                .toList();
    }

    // DELETE /api/diaries/{id}
    @Transactional
    public void deleteDiary(Member member, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.DIARY_NOT_FOUND));

        if (!diary.getMember().getId().equals(member.getId())) {
            throw new BusinessException(ErrorCode.DIARY_ACCESS_DENIED);
        }

        diaryRepository.delete(diary);
    }

    // GET /api/diaries/{id} - 일기 단건 조회
    public DiaryResponseDto getDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.DIARY_NOT_FOUND));

        return DiaryResponseDto.from(diary, commentRepository.countByDiary(diary));
    }

    // GET /api/diaries/search?keyword=
    // 검색 메서드 추가
    public List<DiaryResponseDto> searchDiaries(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }
        return diaryRepository.searchByContent(keyword.trim())
                .stream()
                .map(d -> DiaryResponseDto.from(d, commentRepository.countByDiary(d)))
                .toList();
    }

    // PATCH /api/diaries/{id} - 일기 수정
    @Transactional
    public DiaryResponseDto updateDiary(Member member, Long diaryId, DiaryRequestDto request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.DIARY_NOT_FOUND));

        if (!diary.getMember().getId().equals(member.getId())) {
            throw new BusinessException(ErrorCode.DIARY_ACCESS_DENIED);
        }

        // 금칙어 체크
        if (badWordFilter.containsBadWord(request.content())) {
            throw new BusinessException(ErrorCode.BAD_WORD_DETECTED);
        }

        diary.update(
                request.title() != null ? request.title() : diary.getTitle(),
                request.content() != null ? request.content() : diary.getContent(),
                request.isPublic() != null ? request.isPublic() : diary.isPublic()
        );

        return DiaryResponseDto.from(diary, commentRepository.countByDiary(diary));
    }
}