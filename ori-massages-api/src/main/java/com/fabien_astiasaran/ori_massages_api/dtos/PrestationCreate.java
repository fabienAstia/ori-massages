package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PrestationCreate(
        Long id,
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String price,
        @NotBlank String durationLabel,
        @NotNull Long durationId
) {
}
