package com.skhuthon.team4.member.controller;

import com.skhuthon.team4.global.auth.jwt.JwtTokenProvider;
import com.skhuthon.team4.global.common.ApiResponseTemplate;
import com.skhuthon.team4.global.exception.BusinessException;
import com.skhuthon.team4.global.exception.ErrorCode;
import com.skhuthon.team4.member.domain.Member;
import com.skhuthon.team4.member.domain.repository.MemberRepository;
import com.skhuthon.team4.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 내 정보 조회
    @GetMapping("/me")
    public ApiResponseTemplate<MemberResponseDto> getMyInfo(
            @AuthenticationPrincipal Member member) {

        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        return ApiResponseTemplate.success(MemberResponseDto.from(findMember));
    }

    // Refresh Token으로 새 Access Token 발급
    @PostMapping("/refresh")
    public ApiResponseTemplate<Map<String, String>> refresh(
            @RequestBody Map<String, String> body
    ) {
        String refreshToken = body.get("refreshToken");

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        String tokenType = jwtTokenProvider.getTokenType(refreshToken);
        if (!"refresh".equals(tokenType)) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        Long memberId = jwtTokenProvider.getMemberIdFromToken(refreshToken);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (!refreshToken.equals(member.getRefreshToken())) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(memberId);

        return ApiResponseTemplate.success(Map.of("accessToken", newAccessToken));
    }

    // 로그아웃 (Refresh Token 삭제)
    @PostMapping("/logout")
    public ApiResponseTemplate<Void> logout(
            @AuthenticationPrincipal Member member
    ) {
        if (member != null) {
            Member findMember = memberRepository.findById(member.getId())
                    .orElse(null);
            if (findMember != null) {
                findMember.clearRefreshToken();
                memberRepository.save(findMember);
            }
        }
        return ApiResponseTemplate.success();
    }
}