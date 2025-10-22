package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record ServiceCreate(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotBlank
        String price,
        @NotBlank
        String duration
) {
}
