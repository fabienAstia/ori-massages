package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AppointmentCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminUserResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Role;
import com.fabien_astiasaran.ori_massages_api.entities.User;
import com.fabien_astiasaran.ori_massages_api.mappers.UserMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.RoleRepository;
import com.fabien_astiasaran.ori_massages_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<AdminUserResponse> getUsers(){
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().map(UserMapper::toResponse).collect(Collectors.toSet());
    }
}
