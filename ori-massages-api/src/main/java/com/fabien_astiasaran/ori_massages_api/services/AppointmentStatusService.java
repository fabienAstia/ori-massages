package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminAppointmentStatusRequest;
import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPossibleStatus;
import com.fabien_astiasaran.ori_massages_api.entities.AppointmentStatus;
import com.fabien_astiasaran.ori_massages_api.mappers.AppointmentStatusMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.fabien_astiasaran.ori_massages_api.mappers.PossibleStatusMapper.toAdminRequest;

@Service
public class AppointmentStatusService {

    public List<AdminAppointmentStatusRequest> getAppointmentStatuses() {
        return Arrays.stream(AppointmentStatus.values())
                .map(appointmentStatus -> AppointmentStatusMapper.toAdminRequest(appointmentStatus))
                .sorted(Comparator.comparing(AdminAppointmentStatusRequest::order))
                .toList();
    }

    public static List<AdminPossibleStatus> getPossibleStatuses(AppointmentStatus status){
        List<AppointmentStatus> possibleStatuses = new ArrayList<>();
        if(status == AppointmentStatus.CONFIRMED){
            possibleStatuses = List.of(
                    AppointmentStatus.COMPLETED,
                    AppointmentStatus.CANCELLED,
                    AppointmentStatus.NO_SHOW
            );
        }
        return toAdminRequest(possibleStatuses);
    }
}
