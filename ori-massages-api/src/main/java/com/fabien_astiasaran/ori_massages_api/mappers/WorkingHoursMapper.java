package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.WorkingHoursResponse;
import com.fabien_astiasaran.ori_massages_api.entities.WorkingHours;

public class WorkingHoursMapper {

    public static WorkingHoursResponse toResponse(WorkingHours workingHours){
        return new WorkingHoursResponse(
                workingHours.getId(),
              workingHours.getStartTime(),
              workingHours.getEndTime(),
                workingHours.getName()
        );
    }

    public static WorkingHours toEntity(WorkingHours workingHours){
        WorkingHours entity = new WorkingHours();
        entity.setStartTime(workingHours.getStartTime());
        entity.setEndTime(workingHours.getEndTime());
        entity.setName(workingHours.getName());
        return entity;
    }

//    public static WorkingHours toEntity(WorkingHoursResponse workingHours){
//        WorkingHours entity = new WorkingHours();
//        entity.setStartTime(workingHours.getStartTime());
//        entity.setEndTime(workingHours.getEndTime());
//        entity.setName(workingHours.getName());
//        return entity;
//    }
}
