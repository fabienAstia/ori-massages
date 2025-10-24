package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.*;
import com.fabien_astiasaran.ori_massages_api.entities.*;
import com.fabien_astiasaran.ori_massages_api.repositories.*;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

@org.springframework.stereotype.Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private PrestationRepository prestationRepository;
    private ContactService contactService;
    private LocationRepository locationRepository;
    private DateRepository dateRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PrestationRepository prestationRepository, ContactService contactService, LocationRepository locationRepository, DateRepository dateRepository) {
        this.appointmentRepository = appointmentRepository;
        this.prestationRepository = prestationRepository;
        this.contactService = contactService;
        this.locationRepository = locationRepository;
        this.dateRepository = dateRepository;
    }

    public void createAppointment(AppointmentCreate appointmentCreate){
        Prestation prestation = prestationRepository.findById(appointmentCreate.prestation().id())
                .orElseThrow(()-> new EntityNotFoundException("Prestation not found"));
        Contact contact = contactService.createContact(appointmentCreate.user());
        User user = contact.user();
        Message message = contact.message();
        Location location = locationRepository.findById(appointmentCreate.location().id())
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        
        Appointment appointment = new Appointment();
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setComment(message.getContent());
//        appointment.setSlot(location);
        appointment.setUser(user);
        appointment.setLocation(location);

        System.out.println("appointmentCreate= " + appointmentCreate);
        appointmentRepository.save(appointment);
    }

}
