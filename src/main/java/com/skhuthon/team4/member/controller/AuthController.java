package com.skhuthon.team4.member.controller;

import com.skhuthon.team4.global.auth.oauth.CustomOAuth2User;
import com.skhuthon.team4.global.common.ApiResponseTemplate;
import com.skhuthon.team4.global.exception.BusinessException;
import com.skhuthon.team4.global.exception.ErrorCode;
import com.skhuthon.team4.member.domain.Member;
import com.skhuthon.team4.member.domain.repository.MemberRepository;
import com.skhuthon.team4.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberRepository memberRepository;

    // 내 정보 조회
    @GetMapping("/me")
    public ApiResponseTemplate<MemberResponseDto> getMyInfo(
            @AuthenticationPrincipal Member member) {

        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        return ApiResponseTemplate.success(MemberResponseDto.from(findMember));
    }

    // POST /api/auth/logout - 로그아웃
    @PostMapping("/logout")
    public ApiResponseTemplate<Void> logout() {
        return ApiResponseTemplate.success();
    }
}