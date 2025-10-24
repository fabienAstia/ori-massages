package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.MessageResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Message;

public class MessageMapper {

    public static MessageResponse toResponse(Message message){
        return new MessageResponse(
                UserMapper.toResponse(message.getUser()),
                message.getDateTime(),
                message.getContent()
        );
    }
}
