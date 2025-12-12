package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminDurationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.DurationCreate;
import com.fabien_astiasaran.ori_massages_api.services.DurationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/durations")
public class DurationController {

    private final DurationService durationService;

    public DurationController(DurationService durationService) {
        this.durationService = durationService;
    }

    @GetMapping
    public List<AdminDurationResponse> getDurations(){
        return durationService.getDurations();
    }

    @PostMapping
    public AdminDurationResponse createDuration(@Valid @RequestBody DurationCreate durationCreate){
        return durationService.createDuration(durationCreate);
    }

    @PostMapping("/{id}")
    public AdminDurationResponse editDuration(@PathVariable Long id,
                                              @Valid @RequestBody DurationCreate durationCreate){
        return durationService.editDuration(id, durationCreate);
    }

    @DeleteMapping("/{id}")
    public void deleteDuration(@PathVariable Long id){
        durationService.delete(id);
    }
}
