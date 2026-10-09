package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.*;
import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminAppointmentResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminUpdateAppointmentStatus;
import com.fabien_astiasaran.ori_massages_api.entities.*;
import com.fabien_astiasaran.ori_massages_api.exceptions.AppointmentNotFoundException;
import com.fabien_astiasaran.ori_massages_api.exceptions.PrestationNotFoundException;
import com.fabien_astiasaran.ori_massages_api.mappers.AddressMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.fabien_astiasaran.ori_massages_api.mappers.AppointmentMapper.toAdminResponse;
import static com.fabien_astiasaran.ori_massages_api.mappers.AppointmentStatusMapper.toAdminRequest;
import static com.fabien_astiasaran.ori_massages_api.services.AppointmentStatusService.getPossibleStatuses;
import static com.fabien_astiasaran.ori_massages_api.utils.TimeUtils.localTimeToString;

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

    private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);

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

    public List<AdminAppointmentResponse> getAppointments(){
        List<Appointment> appointments = appointmentRepository.findAll();
        appointments.sort(Comparator.comparing(a -> a.getStatus().getOrder()));
        return toAdminResponse(appointments);
    }

    @Transactional
    public Appointment createAppointment(AppointmentCreate appointmentCreate){
        log.info("appointmentCreate = {}", appointmentCreate);

        Long prestationId = appointmentCreate.slot().prestation().id();
        Prestation prestation = prestationRepository.findById(prestationId).orElseThrow(()->
                new PrestationNotFoundException(String.format("Prestation not found with ID: %d", prestationId)));
        Date date = dateService.findOrCreateDate(appointmentCreate.slot());

        Long workingHoursId = appointmentCreate.slot().workingHours().id();
        WorkingHours workingHours = workingHoursRepository.findById(workingHoursId).orElseThrow(()->
                new EntityNotFoundException(String.format("WorkingHours not found with ID: %d", workingHoursId)));

        Slot slot = slotService.createSlot(appointmentCreate, date, workingHours, prestation);
        User user = userService.findOrCreateUser(appointmentCreate);

        Location location = locationRepository.findById(appointmentCreate.locationId()).orElseThrow(()->
                new EntityNotFoundException(String.format("Location not found with this ID : %d", appointmentCreate.locationId())));
        Address address = resolveAddress(appointmentCreate, user, location);

        Appointment appointment = buildAndSaveAppointment(slot, user, address);
        messageService.createMessageIfPresent(appointmentCreate, user, appointment);
        return appointment;
    }

    @Transactional
    public AdminAppointmentResponse updateAppointment(Long id, AdminUpdateAppointmentStatus request) {
        Appointment toUpdate = appointmentRepository.findById(id).orElseThrow(() ->
                new AppointmentNotFoundException(String.format("No Appointment found with this ID: %s", id)));
        toUpdate.setStatus(request.targetStatus());
        return toAdminResponse(appointmentRepository.save(toUpdate));
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
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return appointmentRepository.save(appointment);
    }

}
