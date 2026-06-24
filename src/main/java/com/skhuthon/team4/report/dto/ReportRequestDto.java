package com.skhuthon.team4.report.dto;

import com.skhuthon.team4.report.domain.ReportReason;
import com.skhuthon.team4.report.domain.ReportType;
import jakarta.validation.constraints.NotNull;

public record ReportRequestDto(

        @NotNull(message = "신고 대상 ID를 입력해주세요.")
        Long targetId,

        @NotNull(message = "신고 대상 유형을 입력해주세요.")
        ReportType reportType,

        @NotNull(message = "신고 사유를 입력해주세요.")
        ReportReason reason
) {}