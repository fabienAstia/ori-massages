package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.UserResponse;
import com.fabien_astiasaran.ori_massages_api.entities.User;

public class UserMapper {

    public static UserResponse toResponse(User user){
        return new UserResponse(
          user.getId(),
            user.getEmail(),
            user.getFirstname(),
            user.getLastname(),
            user.getPhoneNumber()
        );
    }
}
