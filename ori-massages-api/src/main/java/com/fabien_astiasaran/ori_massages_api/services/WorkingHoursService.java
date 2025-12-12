package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminWorkingHoursResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.WorkingHoursCreate;
import com.fabien_astiasaran.ori_massages_api.entities.WorkingHours;
import com.fabien_astiasaran.ori_massages_api.repositories.WorkingHoursRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

import static com.fabien_astiasaran.ori_massages_api.mappers.WorkingHoursMapper.toAdminResponse;

@Service
public class WorkingHoursService {

    private final WorkingHoursRepository workingHoursRepository;

    public WorkingHoursService(WorkingHoursRepository workingHoursRepository) {
        this.workingHoursRepository = workingHoursRepository;
    }

    public List<AdminWorkingHoursResponse> getWorkingHours(){
        List<WorkingHours> workingHours = workingHoursRepository.findAll();
        return toAdminResponse(workingHours);
    }

    public AdminWorkingHoursResponse createWorkingHours(WorkingHoursCreate workingHoursCreate){
        WorkingHours newWorkingHours = new WorkingHours();
        newWorkingHours.setStartTime(LocalTime.parse(workingHoursCreate.startTime()));
        newWorkingHours.setEndTime(LocalTime.parse(workingHoursCreate.endTime()));
        newWorkingHours.setName(workingHoursCreate.name());
        return toAdminResponse(workingHoursRepository.save(newWorkingHours));
    }

    public AdminWorkingHoursResponse editWorkingHours(Long id, WorkingHoursCreate workingHoursCreate){
        WorkingHours workingHours = workingHoursRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("WorkingHours not found"));
        workingHours.setStartTime(LocalTime.parse(workingHoursCreate.startTime()));
        workingHours.setEndTime(LocalTime.parse(workingHoursCreate.endTime()));
        workingHours.setName(workingHoursCreate.name());
        return toAdminResponse(workingHoursRepository.save(workingHours));
    }

    public void delete(Long id){
        workingHoursRepository.deleteById(id);
    }
}
