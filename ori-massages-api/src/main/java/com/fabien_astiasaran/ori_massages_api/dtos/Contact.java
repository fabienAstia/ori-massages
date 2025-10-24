package com.fabien_astiasaran.ori_massages_api.dtos;

import com.fabien_astiasaran.ori_massages_api.entities.Message;
import com.fabien_astiasaran.ori_massages_api.entities.User;

public record Contact(
        User user,
        Message message
) {
}
