package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DurationResponse(
        @NotNull Long id,
        @NotNull @Positive Integer value,
        @NotBlank String label,
        @NotNull @Positive Integer breakTime
) {
}
