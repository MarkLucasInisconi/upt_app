package com.upt.app.rating.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingUpsertRequest(
    @NotNull Long postId,
    @NotNull @Min(1) @Max(5) Integer value
) {}
