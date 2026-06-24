package com.skhuthon.team4.diary.dto;

import com.skhuthon.team4.diary.domain.Diary;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DiaryResponseDto(
        Long id,
        Long memberId,
        String nickname,
        String profileImage,
        String title,       // ← title 먼저
        String content,     // ← content 다음
        LocalDate diaryDate,
        int empathyCount,
        int commentCount,
        boolean isPublic,
        LocalDateTime createdAt
) {
    public static DiaryResponseDto from(Diary diary, int commentCount) {
        return new DiaryResponseDto(
                diary.getId(),
                diary.getMember().getId(),
                diary.getMember().getNickname(),
                diary.getMember().getProfileImage(),
                diary.getTitle(),
                diary.getContent(),
                diary.getDiaryDate(),
                diary.getEmpathyCount(),
                commentCount,
                diary.isPublic(),
                diary.getCreatedAt()
        );
    }

    public static DiaryResponseDto from(Diary diary) {
        return from(diary, 0);
    }
}