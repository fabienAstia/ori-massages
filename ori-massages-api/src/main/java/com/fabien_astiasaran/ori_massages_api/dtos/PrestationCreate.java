package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PrestationCreate(
        Long id,
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotBlank
        String price,
        @Valid
        DurationCreate duration
) {
}
