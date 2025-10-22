package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AppointmentCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.Contact;
import com.fabien_astiasaran.ori_massages_api.entities.*;
import com.fabien_astiasaran.ori_massages_api.repositories.*;

@org.springframework.stereotype.Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private ServiceRepository serviceRepository;
    private ContactService contactService;
    private LocationRepository locationRepository;
    private DateRepository dateRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, ServiceRepository serviceRepository, ContactService contactService, LocationRepository locationRepository, DateRepository dateRepository) {
        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
        this.contactService = contactService;
        this.locationRepository = locationRepository;
        this.dateRepository = dateRepository;
    }

    public void createAppointment(AppointmentCreate appointmentCreate){
        Service service = serviceRepository.findByName(appointmentCreate.service().name());
        Contact contact = contactService.createContact(appointmentCreate.customer());
        User user = contact.user();
//        Message message = contact.message();
        Location location = locationRepository.findByName(appointmentCreate.location());
//        Date date = dateRepository.findByDateValue();
        Appointment appointment = new Appointment();
        appointment.setUser(user);
//        appointment.s(location);
//        appointment.setBeginAt(appointmentCreate.date());
//        appointment.setBeginAt(LocalDateTime.of(appointment.));

        System.out.println("appointmentCreate= " + appointmentCreate);
        //
    }

}
