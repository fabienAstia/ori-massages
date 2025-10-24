package com.fabien_astiasaran.ori_massages_api.dtos;

import java.time.LocalDateTime;

public record MessageResponse(
        UserResponse user,
        LocalDateTime time,
        String content
) {

}
