package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminTreatmentTypeResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.TreatmentTypeCreate;
import com.fabien_astiasaran.ori_massages_api.services.TreatmentTypeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/types")
public class TreatmentTypeController {

    private final TreatmentTypeService service;

    public TreatmentTypeController(TreatmentTypeService service) {
        this.service = service;
    }

    @GetMapping
    public Set<AdminTreatmentTypeResponse> getTreatmentTypes(){
        return service.getTreatmentTypes();
    }

    @PostMapping ("/{id}")
    public AdminTreatmentTypeResponse editTreatmentType(@PathVariable Long id, @Valid @RequestBody TreatmentTypeCreate treatmentTypeCreate){
        return service.editTreatmentType(id, treatmentTypeCreate);
    }

    @PostMapping
    public AdminTreatmentTypeResponse createTreatmentType( @Valid @RequestBody TreatmentTypeCreate treatmentTypeCreate){
        return service.createTreatmentType(treatmentTypeCreate);
    }

    @DeleteMapping ("/{id}")
    public void deleteTreatmentType(@PathVariable Long id){
        service.delete(id);
    }
}
