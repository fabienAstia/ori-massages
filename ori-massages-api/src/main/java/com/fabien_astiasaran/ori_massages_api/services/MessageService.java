package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AppointmentCreate;
import com.fabien_astiasaran.ori_massages_api.entities.Appointment;
import com.fabien_astiasaran.ori_massages_api.entities.Message;
import com.fabien_astiasaran.ori_massages_api.entities.User;
import com.fabien_astiasaran.ori_massages_api.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessageIfPresent(AppointmentCreate appointmentCreate, User user, Appointment appointment) {
        var msg = appointmentCreate.message();
        if(!msg.isBlank()){
            Message message = new Message();
            message.setAuthor(user);
            message.setContent(msg);
            message.setAppointment(appointment);
            return messageRepository.save(message);
        }
        return null;
    }
}
