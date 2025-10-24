package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.Contact;
import com.fabien_astiasaran.ori_massages_api.dtos.ContactCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.ContactResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.UserCreate;
import com.fabien_astiasaran.ori_massages_api.entities.User;
import com.fabien_astiasaran.ori_massages_api.entities.Message;
import com.fabien_astiasaran.ori_massages_api.mappers.MessageMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.UserRepository;
import com.fabien_astiasaran.ori_massages_api.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.fabien_astiasaran.ori_massages_api.mappers.UserMapper.toResponse;

@Service
public class ContactService {

    private UserRepository userRepository;
    private MessageRepository messageRepository;

    public ContactService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Contact createContact(UserCreate userCreate){
        User user = userRepository.findByEmail(userCreate.email());
        if(user == null){
            User newUser = new User();
            newUser.setEmail(userCreate.email());
            newUser.setFirstname(userCreate.firstname());
            newUser.setLastname(userCreate.lastname());
            newUser.setPhoneNumber(userCreate.phoneNumber());
            user = newUser;
        }
        userRepository.save(user);
        Message message = new Message();
        message.setUser(user);
        message.setDateTime(LocalDateTime.now());
        message.setContent(userCreate.message());
        messageRepository.save(message);
        return new Contact(user, message);
    }
}
