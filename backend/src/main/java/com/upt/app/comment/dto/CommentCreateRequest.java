package com.upt.app.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentCreateRequest(
    @NotNull Long postId,
    @NotBlank @Size(min = 1, max = 1000) String content
) {}
