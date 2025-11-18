package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.Contact;
import com.fabien_astiasaran.ori_massages_api.dtos.ContactCreate;
import com.fabien_astiasaran.ori_massages_api.entities.User;
import com.fabien_astiasaran.ori_massages_api.entities.Message;
import com.fabien_astiasaran.ori_massages_api.mappers.UserMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.UserRepository;
import com.fabien_astiasaran.ori_massages_api.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class ContactService {

    private UserRepository userRepository;
    private MessageRepository messageRepository;

    public ContactService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Contact createContact(ContactCreate contact){
        User user = userRepository.findByPhoneNumber(contact.user().phoneNumber());
        if(user == null){
            User newUser = new User();
            newUser.setPhoneNumber(contact.user().phoneNumber());
            newUser.setEmail(contact.user().email());
            newUser.setFullname(contact.user().fullname());
            user = newUser;
            userRepository.save(user);
        }
        if(!isBlank(contact.message())){
            Message message = new Message();
            message.setAuthor(user);
            message.setMsgDate(LocalDateTime.now());
            message.setContent(contact.message());
            messageRepository.save(message);
        }
        return new Contact(
                UserMapper.toResponse(user),
                contact.message());
    }
}
