package com.skhuthon.team4.member.dto;

import com.skhuthon.team4.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {

    private final Long id;
    private final String nickname;
    private final String profileImage;
    private final String email;
    private final boolean notification;
    private final Integer age;

    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .email(member.getEmail())
                .notification(member.isNotification())
                .age(member.getAge())
                .build();
    }
}