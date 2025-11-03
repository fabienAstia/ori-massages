package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AppointmentCreate;
import com.fabien_astiasaran.ori_massages_api.entities.Role;
import com.fabien_astiasaran.ori_massages_api.entities.User;
import com.fabien_astiasaran.ori_massages_api.repositories.RoleRepository;
import com.fabien_astiasaran.ori_massages_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public static final String USER = "USER";
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findOrCreateUser(AppointmentCreate appointmentCreate) {
        User user = userRepository.findByPhoneNumber(appointmentCreate.user().phoneNumber());
        Role role = roleRepository.findByLabel(USER);
        if(user == null){ user = new User();
            user.setPhoneNumber(appointmentCreate.user().phoneNumber());
            user.setEmail(appointmentCreate.user().email());
            user.setFullname(appointmentCreate.user().fullname());
            user.setRole(role);
            userRepository.save(user);
        }
        return user;
    }
}
