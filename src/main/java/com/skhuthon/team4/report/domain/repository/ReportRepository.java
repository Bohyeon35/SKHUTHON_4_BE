package com.skhuthon.team4.report.domain.repository;

import com.skhuthon.team4.member.domain.Member;
import com.skhuthon.team4.report.domain.Report;
import com.skhuthon.team4.report.domain.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    // 중복 신고 체크
    boolean existsByMemberAndTargetIdAndReportType(Member member, Long targetId, ReportType reportType);

    // 신고 횟수 집계
    int countByTargetIdAndReportType(Long targetId, ReportType reportType);
}