package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminAppointmentStatusRequest;
import com.fabien_astiasaran.ori_massages_api.entities.AppointmentStatus;

public final class AppointmentStatusMapper {

    private AppointmentStatusMapper(){}

    public static AdminAppointmentStatusRequest toAdminRequest(AppointmentStatus status){
        return new AdminAppointmentStatusRequest(
                status.name(),
                status.getLabel(),
                status.getOrder(),
                status.getColor()
        );
    }
}
