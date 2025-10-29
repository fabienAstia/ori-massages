package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AppointmentCreate;
import com.fabien_astiasaran.ori_massages_api.entities.User;
import com.fabien_astiasaran.ori_massages_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findOrCreateUser(AppointmentCreate appointmentCreate) {
        User user = userRepository.findByEmail(appointmentCreate.user().email());
        if(user == null){ user = new User();
            user.setEmail(appointmentCreate.user().email());
            user.setPhoneNumber(appointmentCreate.user().phoneNumber());
            user.setFirstname(appointmentCreate.user().firstname());
            user.setLastname(appointmentCreate.user().lastname());
            if(appointmentCreate.location().name().equals("A domicile")){
                user.setUserAddress(appointmentCreate.user().address());
            }
            userRepository.save(user);
        }
        return user;
    }
}
