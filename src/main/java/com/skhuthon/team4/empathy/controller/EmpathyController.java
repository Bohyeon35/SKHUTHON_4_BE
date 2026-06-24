package com.skhuthon.team4.empathy.controller;

import com.skhuthon.team4.empathy.dto.EmpathyResponseDto;
import com.skhuthon.team4.empathy.service.EmpathyService;
import com.skhuthon.team4.global.common.ApiResponseTemplate;
import com.skhuthon.team4.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diaries/{diaryId}/empathy")
@RequiredArgsConstructor
public class EmpathyController {

    private final EmpathyService empathyService;

    // 공감 토글 (공감 / 공감 취소)
    @PostMapping
    public ApiResponseTemplate<EmpathyResponseDto> toggleEmpathy(
            @AuthenticationPrincipal Member member,
            @PathVariable Long diaryId
    ) {
        return ApiResponseTemplate.success(empathyService.toggleEmpathy(member, diaryId));
    }

    // 공감 여부 조회
    @GetMapping
    public ApiResponseTemplate<EmpathyResponseDto> getEmpathyStatus(
            @AuthenticationPrincipal Member member,
            @PathVariable Long diaryId
    ) {
        return ApiResponseTemplate.success(empathyService.getEmpathyStatus(member, diaryId));
    }
}