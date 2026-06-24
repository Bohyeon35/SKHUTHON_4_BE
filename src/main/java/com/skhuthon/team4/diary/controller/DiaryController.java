package com.skhuthon.team4.diary.controller;

import com.skhuthon.team4.diary.dto.DiaryRequestDto;
import com.skhuthon.team4.diary.dto.DiaryResponseDto;
import com.skhuthon.team4.diary.service.DiaryService;
import com.skhuthon.team4.global.common.ApiResponseTemplate;
import com.skhuthon.team4.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    // 일기 작성
    @PostMapping
    public ApiResponseTemplate<DiaryResponseDto> createDiary(
            @AuthenticationPrincipal Member member,
            @Valid @RequestBody DiaryRequestDto request
    ) {
        return ApiResponseTemplate.success(diaryService.createDiary(member, request));
    }

    // 전체 피드 (latest / random)
    @GetMapping("/feed")
    public ApiResponseTemplate<List<DiaryResponseDto>> getFeed(
            @RequestParam(defaultValue = "latest") String sort
    ) {
        return ApiResponseTemplate.success(diaryService.getFeed(sort));
    }

    // 핫 피드 top 10
    @GetMapping("/hot")
    public ApiResponseTemplate<List<DiaryResponseDto>> getHotFeed() {
        return ApiResponseTemplate.success(diaryService.getHotFeed());
    }

    // 내 일기 (년/월 필터 + 나만보기)
    @GetMapping("/me")
    public ApiResponseTemplate<List<DiaryResponseDto>> getMyDiaries(
            @AuthenticationPrincipal Member member,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false, defaultValue = "all") String visibility
    ) {
        int y = (year != null) ? year : LocalDate.now().getYear();
        int m = (month != null) ? month : LocalDate.now().getMonthValue();
        return ApiResponseTemplate.success(diaryService.getMyDiaries(member, y, m, visibility));
    }

    // 일기 삭제
    @DeleteMapping("/{id}")
    public ApiResponseTemplate<Void> deleteDiary(
            @AuthenticationPrincipal Member member,
            @PathVariable Long id
    ) {
        diaryService.deleteDiary(member, id);
        return ApiResponseTemplate.success();
    }

    // 일기 단건 조회
    @GetMapping("/{id}")
    public ApiResponseTemplate<DiaryResponseDto> getDiary(
            @PathVariable Long id
    ) {
        return ApiResponseTemplate.success(diaryService.getDiary(id));
    }

    // 일기 검색
    @GetMapping("/search")
    public ApiResponseTemplate<List<DiaryResponseDto>> searchDiaries(
            @RequestParam String keyword
    ) {
        return ApiResponseTemplate.success(diaryService.searchDiaries(keyword));
    }

    // 일기 수정
    @PatchMapping("/{diaryId}")
    public ApiResponseTemplate<DiaryResponseDto> updateDiary(
            @AuthenticationPrincipal Member member,
            @PathVariable Long diaryId,
            @RequestBody DiaryRequestDto request
    ) {
        return ApiResponseTemplate.success(diaryService.updateDiary(member, diaryId, request));
    }
}