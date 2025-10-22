package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record LocationCreate(
        @NotBlank String name,
        String address
) {
}
