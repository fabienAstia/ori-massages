package com.fabien_astiasaran.ori_massages_api.dtos;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminUserResponse;

public record Contact(
        AdminUserResponse user,
        String message
) {
}
