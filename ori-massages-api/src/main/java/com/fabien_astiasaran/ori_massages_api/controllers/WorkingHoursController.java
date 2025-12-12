package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminWorkingHoursResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.WorkingHoursCreate;
import com.fabien_astiasaran.ori_massages_api.services.WorkingHoursService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workingHours")
public class WorkingHoursController {

    private final WorkingHoursService workingHoursService;

    public WorkingHoursController(WorkingHoursService workingHoursService) {
        this.workingHoursService = workingHoursService;
    }

    @GetMapping
    public List<AdminWorkingHoursResponse> getWorkingHours(){
        return workingHoursService.getWorkingHours();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminWorkingHoursResponse createWorkingHours(@Valid @RequestBody WorkingHoursCreate workingHoursCreate){
        return workingHoursService.createWorkingHours(workingHoursCreate);
    }

    @PostMapping("/{id}")
    public AdminWorkingHoursResponse editWorkingHours(@PathVariable Long id, @Valid @RequestBody WorkingHoursCreate workingHoursCreate){
        return workingHoursService.editWorkingHours(id, workingHoursCreate);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkingHour(@PathVariable Long id){
        workingHoursService.delete(id);
    }
}
