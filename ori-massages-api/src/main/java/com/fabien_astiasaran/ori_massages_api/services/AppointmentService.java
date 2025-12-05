package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.*;
import com.fabien_astiasaran.ori_massages_api.entities.*;
import com.fabien_astiasaran.ori_massages_api.mappers.AddressMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fabien_astiasaran.ori_massages_api.services.SlotService.localTimeToString;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private PrestationRepository prestationRepository;
    private LocationRepository locationRepository;
    private DateService dateService;
    private SlotService slotService;
    private WorkingHoursRepository workingHoursRepository;
    private UserService userService;
    private MessageService messageService;
    private AddressService addressService;
    private AddressRepository addressRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PrestationRepository prestationRepository, LocationRepository locationRepository, DateService dateService, SlotService slotService, WorkingHoursRepository workingHoursRepository, UserService userService, MessageService messageService, AddressService addressService, AddressRepository addressRepository) {
        this.appointmentRepository = appointmentRepository;
        this.prestationRepository = prestationRepository;
        this.locationRepository = locationRepository;
        this.dateService = dateService;
        this.slotService = slotService;
        this.workingHoursRepository = workingHoursRepository;
        this.userService = userService;
        this.messageService = messageService;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
    }

    public Appointment createAppointment(AppointmentCreate appointmentCreate){
        System.out.println("appointmentCreate = " + appointmentCreate);
        Prestation prestation = prestationRepository.findById(appointmentCreate.slot().prestation().id())
                .orElseThrow(()-> new EntityNotFoundException("Prestation not found"));
        Date date = dateService.findOrCreateDate(appointmentCreate.slot());
        WorkingHours workingHours = workingHoursRepository.findById(appointmentCreate.slot().workingHours().id())
                .orElseThrow(()-> new EntityNotFoundException("WorkingHours not found"));
        Slot slot = slotService.createSlot(appointmentCreate, date, workingHours, prestation);
        User user = userService.findOrCreateUser(appointmentCreate);

        Location location = locationRepository.findById(appointmentCreate.locationId())
                .orElseThrow(()-> new EntityNotFoundException("Location not found"));
        Address address = resolveAddress(appointmentCreate, user, location);

        Appointment appointment = buildAndSaveAppointment(slot, user, address);
        messageService.createMessageIfPresent(appointmentCreate, user, appointment);
        return appointment;
    }

    public Address resolveAddress(AppointmentCreate appointmentCreate, User user, Location location) {
        if(location.isAtHome()){
            Address address = addressService.findOrCreateAddress(appointmentCreate.address());
            address.setLocation(location);
            address.setUser(user);
            return addressRepository.save(address);
        } else {
            return addressRepository.findByLocation(location);
        }
    }

    private Appointment buildAndSaveAppointment(Slot slot, User user, Address address) {
        Appointment appointment = new Appointment();
        appointment.setSlot(slot);
        appointment.setUser(user);
        appointment.setAddress(address);
        appointment.setStatus(AppointmentStatus.REGISTERED);
        return appointmentRepository.save(appointment);
    }

    public Set<AppointmentResponse> getAppointments(){
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream().map(appointment ->
                new AppointmentResponse(
                        appointment.getId(),
                        appointment.getUser().getFullname(),
                        appointment.getSlot().getPrestation().getName(),
                        appointment.getSlot().getDate().getDate(),
                        localTimeToString(appointment.getSlot().getBeginAt()),
                        localTimeToString(appointment.getSlot().getEndAt()),
                        appointment.getAddress().getLocation().isAtHome(),
                        appointment.getAddress().getLocation().getName(),
                        AddressMapper.getFullAddress(appointment.getAddress()),
                        appointment.getStatus()
                )
        ).collect(Collectors.toSet());
    }
}
