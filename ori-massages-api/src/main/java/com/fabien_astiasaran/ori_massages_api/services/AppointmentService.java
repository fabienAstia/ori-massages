package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.*;
import com.fabien_astiasaran.ori_massages_api.entities.*;
import com.fabien_astiasaran.ori_massages_api.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private PrestationRepository prestationRepository;
    private LocationRepository locationRepository;
    private DateService dateService;
    private SlotService slotService;
    private WorkingHoursRepository workingHoursRepository;
    private UserService userService;
    private MessageRepository messageRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PrestationRepository prestationRepository, LocationRepository locationRepository, DateService dateService, SlotService slotService, WorkingHoursRepository workingHoursRepository, UserService userService, MessageRepository messageRepository) {
        this.appointmentRepository = appointmentRepository;
        this.prestationRepository = prestationRepository;
        this.locationRepository = locationRepository;
        this.dateService = dateService;
        this.slotService = slotService;
        this.workingHoursRepository = workingHoursRepository;
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    public void createAppointment(AppointmentCreate appointmentCreate){
        Prestation prestation = prestationRepository.findById(appointmentCreate.slot().prestation().id())
                .orElseThrow(()-> new EntityNotFoundException("Prestation not found"));
        Date date = dateService.findOrCreateDate(appointmentCreate.slot());
        WorkingHours workingHours = workingHoursRepository.findById(appointmentCreate.slot().workingHours().id())
                .orElseThrow(()-> new EntityNotFoundException("WorkingHours not found"));
        Slot slot = slotService.createSlot(appointmentCreate, date, workingHours, prestation);
        User user = userService.findOrCreateUser(appointmentCreate);
        createMessageIfPresent(appointmentCreate, user);
        Location location = locationRepository.findById(appointmentCreate.location().id())
                .orElseThrow(()-> new EntityNotFoundException("Location not found"));

        buildAndSaveAppointment(slot, user, location);
        System.out.println("appointmentCreate= " + appointmentCreate);
    }

    private void buildAndSaveAppointment(Slot slot, User user, Location location) {
        Appointment appointment = new Appointment();
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setComment("");
        appointment.setSlot(slot);
        appointment.setUser(user);
        appointment.setLocation(location);
        appointmentRepository.save(appointment);
    }

    private void createMessageIfPresent(AppointmentCreate appointmentCreate, User user) {
        Message message = new Message();
        if(!appointmentCreate.user().message().isBlank()){
            message.setContent(appointmentCreate.user().message());
            message.setUser(user);
            messageRepository.save(message);
        }
    }
}
