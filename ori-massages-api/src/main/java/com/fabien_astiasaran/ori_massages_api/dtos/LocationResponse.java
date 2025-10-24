package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LocationResponse(
        @NotNull
        Long id,

        @NotBlank @Size(max = 50)
        String name,

        String address,

        @NotBlank @Size(max=20)
        String imagePath
) {
}
