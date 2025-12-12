package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminWorkingHoursResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.WorkingHoursResponse;
import com.fabien_astiasaran.ori_massages_api.entities.WorkingHours;

import java.util.List;

import static com.fabien_astiasaran.ori_massages_api.utils.TimeUtils.localTimeToString;

public final class WorkingHoursMapper {

    private WorkingHoursMapper(){}

    public static List<AdminWorkingHoursResponse> toAdminResponse(List<WorkingHours> workingHours){
        return workingHours.stream().map(WorkingHoursMapper::toAdminResponse).toList();
    }

    public static AdminWorkingHoursResponse toAdminResponse(WorkingHours workingHours){
        return new AdminWorkingHoursResponse(
                workingHours.getId(),
                localTimeToString(workingHours.getStartTime()),
                localTimeToString(workingHours.getEndTime()),
                workingHours.getName()
        );
    }

    public static WorkingHoursResponse toResponse(WorkingHours workingHours){
        return new WorkingHoursResponse(
                workingHours.getId(),
                workingHours.getStartTime(),
                workingHours.getEndTime(),
                workingHours.getName()
        );
    }
}
