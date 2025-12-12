package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminUserResponse;
import com.fabien_astiasaran.ori_massages_api.entities.User;

public final class UserMapper {

    private UserMapper(){}

    public static AdminUserResponse toResponse(User user){
        return new AdminUserResponse(
            user.getId(),
            user.getPhoneNumber(),
            user.getEmail(),
            user.getFullname()
        );
    }
}
