package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotNull;

public record ContactResponse(
        @NotNull UserResponse user,
        MessageResponse message
) {
}
