package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.*;
import com.fabien_astiasaran.ori_massages_api.entities.*;
import com.fabien_astiasaran.ori_massages_api.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    public static final String AT_HOME = "À domicile";
    public static final String TREATMENT_ROOM = "Espace soin";
    public static final String REGISTERED = "ENREGISTRÉ";
    private AppointmentRepository appointmentRepository;
    private PrestationRepository prestationRepository;
    private LocationRepository locationRepository;
    private DateService dateService;
    private SlotService slotService;
    private WorkingHoursRepository workingHoursRepository;
    private UserService userService;
    private MessageService messageService;
    private StatusRepository statusRepository;
    private AddressService addressService;
    private AddressRepository addressRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PrestationRepository prestationRepository, LocationRepository locationRepository, DateService dateService, SlotService slotService, WorkingHoursRepository workingHoursRepository, UserService userService, MessageService messageService, StatusRepository statusRepository, AddressService addressService, AddressRepository addressRepository) {
        this.appointmentRepository = appointmentRepository;
        this.prestationRepository = prestationRepository;
        this.locationRepository = locationRepository;
        this.dateService = dateService;
        this.slotService = slotService;
        this.workingHoursRepository = workingHoursRepository;
        this.userService = userService;
        this.messageService = messageService;
        this.statusRepository = statusRepository;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
    }

    public Appointment createAppointment(AppointmentCreate appointmentCreate){
        Prestation prestation = prestationRepository.findById(appointmentCreate.slot().prestation().id())
                .orElseThrow(()-> new EntityNotFoundException("Prestation not found"));
        Date date = dateService.findOrCreateDate(appointmentCreate.slot());
        WorkingHours workingHours = workingHoursRepository.findById(appointmentCreate.slot().workingHours().id())
                .orElseThrow(()-> new EntityNotFoundException("WorkingHours not found"));
        Slot slot = slotService.createSlot(appointmentCreate, date, workingHours, prestation);
        User user = userService.findOrCreateUser(appointmentCreate);

        Location location = locationRepository.findById(appointmentCreate.address().locationId())
                .orElseThrow(()-> new EntityNotFoundException("Location not found"));
        Address address = resolveAddress(appointmentCreate, location.getName(), user, location);
        Status status = statusRepository.findByStatusLabel(REGISTERED);

        Appointment appointment = buildAndSaveAppointment(slot, user, address, status);
        messageService.createMessageIfPresent(appointmentCreate, user, appointment);
        return appointment;
    }

    public Address resolveAddress(AppointmentCreate appointmentCreate, String locationName, User user, Location location) {
        Address address = addressService.findOrCreateAddress(appointmentCreate.address());
        address.setLocation(location);
        if(locationName.equalsIgnoreCase(AT_HOME)){
            address.setUser(user);
        }
        return addressRepository.save(address);
    }

    private Appointment buildAndSaveAppointment(Slot slot, User user, Address address, Status status) {
        Appointment appointment = new Appointment();
        appointment.setSlot(slot);
        appointment.setUser(user);
        appointment.setAddress(address);
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

}
