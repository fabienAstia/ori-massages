package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AppointmentCreate;
import com.fabien_astiasaran.ori_massages_api.entities.Prestation;
import com.fabien_astiasaran.ori_massages_api.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock AppointmentRepository appointmentRepository;
    @Mock PrestationRepository prestationRepository;
    @Mock LocationRepository locationRepository;
    @Mock WorkingHoursRepository workingHoursRepository;
    @Mock AddressRepository addressRepository;
    @Mock StatusRepository statusRepository;

    @Mock DateService dateService;
    @Mock SlotService slotService;
    @Mock UserService userService;
    @Mock MessageService messageService;
    @Mock AddressService addressService;

    @InjectMocks AppointmentService appointmentService;

//    @Test
//    void createAppointment(){
//        AppointmentCreate appointmentCreate = new AppointmentCreate();
//        Prestation prestation = new Prestation();
//        prestation.setActive(true);
//        when(prestationRepository.findById(any())).thenReturn();
//    }
}