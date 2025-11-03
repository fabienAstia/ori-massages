package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserResponse(
        @NotNull Long id,
        @NotBlank String phoneNumber,
        @NotBlank String email,
        String fullname
) {
}
