package com.skhuthon.team4.report.controller;

import com.skhuthon.team4.global.common.ApiResponseTemplate;
import com.skhuthon.team4.member.domain.Member;
import com.skhuthon.team4.report.dto.ReportRequestDto;
import com.skhuthon.team4.report.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 신고
    @PostMapping
    public ApiResponseTemplate<Void> report(
            @AuthenticationPrincipal Member member,
            @Valid @RequestBody ReportRequestDto request
    ) {
        reportService.report(member, request);
        return ApiResponseTemplate.success();
    }
}