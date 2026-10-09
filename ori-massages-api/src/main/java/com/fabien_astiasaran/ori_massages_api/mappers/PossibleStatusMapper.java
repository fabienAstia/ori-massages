package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPossibleStatus;
import com.fabien_astiasaran.ori_massages_api.entities.AppointmentStatus;

import java.util.List;

public final class PossibleStatusMapper {

    private PossibleStatusMapper(){}

    public static List<AdminPossibleStatus> toAdminRequest(List<AppointmentStatus> possibleStatuses){
        return possibleStatuses.stream().map(PossibleStatusMapper::toAdminRequest).toList();
    }

    public static AdminPossibleStatus toAdminRequest(AppointmentStatus possibleStatus){
        return new AdminPossibleStatus(possibleStatus, possibleStatus.getLabel(), possibleStatus.getColor());
    }
}
