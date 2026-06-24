package com.skhuthon.team4.report.service;

import com.skhuthon.team4.comment.domain.Comment;
import com.skhuthon.team4.comment.domain.repository.CommentRepository;
import com.skhuthon.team4.diary.domain.Diary;
import com.skhuthon.team4.diary.domain.repository.DiaryRepository;
import com.skhuthon.team4.global.exception.BusinessException;
import com.skhuthon.team4.global.exception.ErrorCode;
import com.skhuthon.team4.member.domain.Member;
import com.skhuthon.team4.report.domain.Report;
import com.skhuthon.team4.report.domain.ReportType;
import com.skhuthon.team4.report.domain.repository.ReportRepository;
import com.skhuthon.team4.report.dto.ReportRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final DiaryRepository diaryRepository;
    private final CommentRepository commentRepository;

    private static final int DIARY_REPORT_THRESHOLD = 3;
    private static final int COMMENT_REPORT_THRESHOLD = 3;

    @Transactional
    public void report(Member member, ReportRequestDto request) {

        // 중복 신고 체크
        if (reportRepository.existsByMemberAndTargetIdAndReportType(
                member, request.targetId(), request.reportType())) {
            throw new BusinessException(ErrorCode.REPORT_ALREADY_EXISTS);
        }

        // 본인 글 신고 불가
        if (request.reportType() == ReportType.DIARY) {
            Diary diary = diaryRepository.findById(request.targetId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.DIARY_NOT_FOUND));

            if (diary.getMember().getId().equals(member.getId())) {
                throw new BusinessException(ErrorCode.CANNOT_REPORT_OWN);
            }

            // 신고 저장
            reportRepository.save(Report.builder()
                    .member(member)
                    .targetId(request.targetId())
                    .reportType(request.reportType())
                    .reason(request.reason())
                    .build());

            // 신고 3회 이상 → 자동 비공개
            int count = reportRepository.countByTargetIdAndReportType(
                    request.targetId(), ReportType.DIARY);
            if (count >= DIARY_REPORT_THRESHOLD) {
                diary.updatePublic(false);
            }

        } else if (request.reportType() == ReportType.COMMENT) {
            Comment comment = commentRepository.findById(request.targetId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

            if (comment.getMember().getId().equals(member.getId())) {
                throw new BusinessException(ErrorCode.CANNOT_REPORT_OWN);
            }

            // 신고 저장
            reportRepository.save(Report.builder()
                    .member(member)
                    .targetId(request.targetId())
                    .reportType(request.reportType())
                    .reason(request.reason())
                    .build());

            // 신고 3회 이상 → 자동 삭제
            int count = reportRepository.countByTargetIdAndReportType(
                    request.targetId(), ReportType.COMMENT);
            if (count >= COMMENT_REPORT_THRESHOLD) {
                commentRepository.delete(comment);
            }
        }
    }
}