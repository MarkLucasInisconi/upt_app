package com.upt.app.car.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CarUpdateRequest(
    @NotBlank @Size(min = 3, max = 100) String title,
    @NotBlank @Size(min = 1, max = 40) String make,
    @NotBlank @Size(min = 1, max = 40) String model,
    @Min(1900) @Max(2100) Integer year,
    String description
) {}
