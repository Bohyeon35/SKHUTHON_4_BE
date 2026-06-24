package com.skhuthon.team4.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequestDto(

        @NotBlank(message = "댓글 내용을 입력해주세요.")
        @Size(max = 200, message = "200자 이내로 입력해주세요.")
        String content
) {}