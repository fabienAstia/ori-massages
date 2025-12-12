package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record TreatmentTypeCreate(
        @NotBlank String name,
        @NotBlank String description
) {
}
