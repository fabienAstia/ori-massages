package com.fabien_astiasaran.ori_massages_api.dtos;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminUserResponse;
import jakarta.validation.constraints.NotNull;

public record ContactResponse(
        @NotNull AdminUserResponse user,
        MessageResponse message
) {
}
