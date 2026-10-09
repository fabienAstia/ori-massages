package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminAppointmentResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Appointment;

import java.util.List;

import static com.fabien_astiasaran.ori_massages_api.mappers.AppointmentStatusMapper.toAdminRequest;
import static com.fabien_astiasaran.ori_massages_api.services.AppointmentStatusService.getPossibleStatuses;
import static com.fabien_astiasaran.ori_massages_api.utils.TimeUtils.localTimeToString;

public final class AppointmentMapper {

    private AppointmentMapper(){}

    public static List<AdminAppointmentResponse> toAdminResponse(List<Appointment> appointments){
        return appointments.stream().map(AppointmentMapper::toAdminResponse).toList();
    }

    public static AdminAppointmentResponse toAdminResponse(Appointment appointment){
        return new AdminAppointmentResponse(
                appointment.getId(),
                appointment.getUser().getFullname(),
                appointment.getSlot().getPrestation().getName(),
                localTimeToString(appointment.getSlot().getBeginAt()),
                localTimeToString(appointment.getSlot().getEndAt()),
                appointment.getSlot().getDate().getDate(),
                appointment.getCreatedAt().toLocalDate(),
                appointment.getAddress().getLocation().isAtHome(),
                appointment.getAddress().getLocation().getName(),
                AddressMapper.getFullAddress(appointment.getAddress()),
                toAdminRequest(appointment.getStatus()),
                getPossibleStatuses(appointment.getStatus())
        );
    }
}
