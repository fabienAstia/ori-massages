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

    public Appointment createAppointment(AppointmentCreate appointmentCreate){
        Prestation prestation = prestationRepository.findById(appointmentCreate.slot().prestation().id())
                .orElseThrow(()-> new EntityNotFoundException("Prestation not found"));
        Date date = dateService.findOrCreateDate(appointmentCreate.slot());
        WorkingHours workingHours = workingHoursRepository.findById(appointmentCreate.slot().workingHours().id())
                .orElseThrow(()-> new EntityNotFoundException("WorkingHours not found"));
        Slot slot = slotService.createSlot(appointmentCreate, date, workingHours, prestation);
        User user = userService.findOrCreateUser(appointmentCreate);
        Message message = createMessageIfPresent(appointmentCreate, user);
        Location location = locationRepository.findById(appointmentCreate.location().id())
                .orElseThrow(()-> new EntityNotFoundException("Location not found"));

        return buildAndSaveAppointment(slot, user, location, message);
    }

    private Appointment buildAndSaveAppointment(Slot slot, User user, Location location, Message message) {
        Appointment appointment = new Appointment();
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setComment(message != null ? message.getContent() : null);
        appointment.setAddress(setAddress(user, location));
        appointment.setSlot(slot);
        appointment.setUser(user);
        appointment.setLocation(location);
        return appointmentRepository.save(appointment);
    }

    private static String setAddress(User user, Location location) {
        return location.getName().equals("A domicile")
                ? user.getUserAddress()
                : location.getAddress();
    }

    private Message createMessageIfPresent(AppointmentCreate appointmentCreate, User user) {
        if(!appointmentCreate.user().message().isBlank()){
            Message message = new Message();
            message.setUser(user);
            message.setDateTime(LocalDateTime.now());
            message.setContent(appointmentCreate.user().message());
            return messageRepository.save(message);
        }
        return null;
    }
}
