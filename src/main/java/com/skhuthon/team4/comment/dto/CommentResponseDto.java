package com.skhuthon.team4.comment.dto;

import com.skhuthon.team4.comment.domain.Comment;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        Long diaryId,
        Long memberId,
        String nickname,
        String profileImage,
        String content,
        LocalDateTime createdAt
) {
    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getDiary().getId(),
                comment.getMember().getId(),
                comment.getMember().getNickname(),
                comment.getMember().getProfileImage(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}