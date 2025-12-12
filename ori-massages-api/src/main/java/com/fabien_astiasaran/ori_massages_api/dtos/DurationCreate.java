package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DurationCreate(
//        @NotNull Long id,
        @NotNull Integer value,
        @NotBlank String label,
        @NotNull Integer breakTime
) {
}
