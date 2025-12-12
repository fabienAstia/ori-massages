package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PrestationResponse(
        @NotNull Long id,
        @NotBlank String name,
        String description,
        Double price,
        @NotNull boolean active,
        @NotBlank String imagePath,
        @Valid DurationResponse duration
) {
}
