package com.skhuthon.team4.member.dto;

import com.skhuthon.team4.member.domain.Member;

public record MemberProfileDto(
        Long id,
        String nickname,
        String profileImage,
        String email,
        boolean isNotification,
        int diaryCount,       // 내가 쓴 일기 수
        int receivedEmpathy,  // 내 일기가 받은 공감 수
        int givenEmpathy      // 내가 준 공감 수
) {}