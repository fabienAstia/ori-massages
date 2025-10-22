package com.fabien_astiasaran.ori_massages_api.dtos;

import com.fabien_astiasaran.ori_massages_api.entities.User;
import com.fabien_astiasaran.ori_massages_api.entities.Message;
import jakarta.validation.constraints.NotNull;

public record Contact(
        @NotNull
        User user,
        Message message
) {
}
