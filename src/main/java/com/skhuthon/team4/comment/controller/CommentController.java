package com.skhuthon.team4.comment.controller;

import com.skhuthon.team4.comment.dto.CommentRequestDto;
import com.skhuthon.team4.comment.dto.CommentResponseDto;
import com.skhuthon.team4.comment.service.CommentService;
import com.skhuthon.team4.global.common.ApiResponseTemplate;
import com.skhuthon.team4.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/diaries/{diaryId}/comments")
    public ApiResponseTemplate<CommentResponseDto> createComment(
            @AuthenticationPrincipal Member member,
            @PathVariable Long diaryId,
            @Valid @RequestBody CommentRequestDto request
    ) {
        return ApiResponseTemplate.success(commentService.createComment(member, diaryId, request));
    }

    // 댓글 목록 조회
    @GetMapping("/api/diaries/{diaryId}/comments")
    public ApiResponseTemplate<List<CommentResponseDto>> getComments(
            @PathVariable Long diaryId
    ) {
        return ApiResponseTemplate.success(commentService.getComments(diaryId));
    }

    // 댓글 삭제
    @DeleteMapping("/api/comments/{commentId}")
    public ApiResponseTemplate<Void> deleteComment(
            @AuthenticationPrincipal Member member,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(member, commentId);
        return ApiResponseTemplate.success();
    }
}