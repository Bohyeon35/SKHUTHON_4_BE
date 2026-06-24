package com.skhuthon.team4.empathy.dto;

public record EmpathyResponseDto(
        Long diaryId,
        int empathyCount,
        boolean isEmpathized
) {}