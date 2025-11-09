package com.upt.app.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentUpdateRequest(
    @NotBlank @Size(min = 1, max = 1000) String content
) {}
