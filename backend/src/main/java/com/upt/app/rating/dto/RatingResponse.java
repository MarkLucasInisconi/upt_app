package com.upt.app.rating.dto;

import com.upt.app.rating.Rating;

import java.time.LocalDateTime;

public record RatingResponse(
    Long id,
    Long postId,
    Long raterId,
    Integer value,
    LocalDateTime createdAt
) {
    public RatingResponse(Rating rating) {
        this(rating.getId(), rating.getCar().getId(), rating.getRater().getId(), rating.getValue(), rating.getCreatedAt());
    }
}
