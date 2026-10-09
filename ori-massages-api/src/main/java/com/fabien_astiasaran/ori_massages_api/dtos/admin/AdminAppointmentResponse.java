package com.fabien_astiasaran.ori_massages_api.dtos.admin;

import java.time.LocalDate;
import java.util.List;

public record AdminAppointmentResponse(
        Long id,
        String userFullName,
        String prestationName,
        String beginAt,
        String endReal,
        LocalDate dateMeeting,
        LocalDate dateCreation,
        boolean atHome,
        String locationName,
        String address,
        AdminAppointmentStatusRequest status,
        List<AdminPossibleStatus> possibleStatuses
) {
}
