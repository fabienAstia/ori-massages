package com.fabien_astiasaran.ori_massages_api.dtos.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminUserResponse(
        @NotNull Long id,
        @NotBlank String phoneNumber,
        @NotBlank String email,
        String fullname
) {
}
