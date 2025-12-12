package com.fabien_astiasaran.ori_massages_api.dtos;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminUserResponse;

import java.time.LocalDateTime;

public record MessageResponse(
        AdminUserResponse user,
        LocalDateTime time,
        String content
) {

}
