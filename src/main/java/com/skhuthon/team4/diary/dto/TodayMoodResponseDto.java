package com.skhuthon.team4.diary.dto;

import java.time.LocalDateTime;

public record TodayMoodResponseDto(
        long totalCount,
        int count100,
        int count75,
        int count50,
        int count25,
        int count0,
        long ratio100,
        long ratio75,
        long ratio50,
        long ratio25,
        long ratio0,
        int representativeEmotion,
        LocalDateTime start,
        LocalDateTime end
) {
    public static TodayMoodResponseDto empty() {
        return new TodayMoodResponseDto(
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                50,
                LocalDateTime.now().minusDays(1).withHour(21),
                LocalDateTime.now().withHour(21)
        );
    }
}