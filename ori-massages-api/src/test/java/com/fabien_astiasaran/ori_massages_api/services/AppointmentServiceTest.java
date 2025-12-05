package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.*;
import com.fabien_astiasaran.ori_massages_api.entities.*;
import com.fabien_astiasaran.ori_massages_api.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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

    @Mock DateService dateService;
    @Mock SlotService slotService;
    @Mock UserService userService;
    @Mock MessageService messageService;
    @Mock AddressService addressService;

    @InjectMocks AppointmentService appointmentService;

    @Test
    void createAppointment(){
        AppointmentCreate appointmentCreate = new AppointmentCreate(
                getSlotCreate(),
                new UserCreate(
                        "06 70 97 08 76",
                        "toto@gmail.com",
                        "fabien Astia"
                        ),
                new AddressCreate(
                        "110",
                        "avenue Gambetta",
                        "",
                        "75020",
                        "Paris"
                ),
                1L,
                "message content"
        );

        Slot slot = new Slot();
        slot.setId(1L);

        User user = new User();
        Address address = new Address();
        Location location = new Location();
        location.setAtHome(true);

        Appointment saved = new Appointment();
        saved.setSlot(slot);
        saved.setUser(user);
        saved.setAddress(address);
        saved.setStatus(AppointmentStatus.REGISTERED);

        when(prestationRepository.findById(any())).thenReturn(Optional.of(new Prestation()));
        when(dateService.findOrCreateDate(any())).thenReturn(new Date());
        when(workingHoursRepository.findById(any())).thenReturn(Optional.of(new WorkingHours()));
        when(slotService.createSlot(any(), any(), any(), any())).thenReturn(slot);
        when(userService.findOrCreateUser(any())).thenReturn(user);
        when(locationRepository.findById(any())).thenReturn(Optional.of(location));
        when(addressService.findOrCreateAddress(any())).thenReturn(address);
        when(appointmentRepository.save(any())).thenReturn(new Appointment());
        when(messageService.createMessageIfPresent(any(), any(), any())).thenReturn(null);
        when(appointmentRepository.save(any())).thenReturn(saved);


        Appointment result = appointmentService.createAppointment(appointmentCreate);
        assertNotNull(result);
        assertEquals(slot, result.getSlot());
        assertEquals(user, result.getUser());
        assertEquals(address, result.getAddress());
        assertEquals(AppointmentStatus.REGISTERED, result.getStatus());
    }

    private static SlotCreate getSlotCreate() {
        return new SlotCreate(1L,
                LocalTime.of(10, 30),
                LocalTime.of(11, 15),
                LocalTime.of(11, 10),
                LocalDate.of(2025, 11, 25),
                new WorkingHoursResponse(1L,
                        LocalTime.of(9, 30),
                        LocalTime.of(13, 00),
                        "Morning WorkingHours"
                ),
                new PrestationResponse(1L,
                        "45min massage",
                        "description",
                        60.0,
                        true,
                        "imagePath",
                        new DurationResponse(1L,
                                45,
                                "little massage",
                                5
                        )
                )
        );
    }
}