package com.skhuthon.team4.member.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record MemberUpdateAgeRequestDto(

        @NotNull(message = "나이를 입력해주세요.")
        @Min(value = 1, message = "나이는 1 이상이어야 합니다.")
        @Max(value = 100, message = "나이는 100 이하여야 합니다.")
        Integer age
) {}