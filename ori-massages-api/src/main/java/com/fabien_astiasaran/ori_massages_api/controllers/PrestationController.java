package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationEdit;
import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.PrestationResponse;
import com.fabien_astiasaran.ori_massages_api.services.PrestationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestations")
public class PrestationController {

    private PrestationService service;

    private PrestationController(PrestationService service){
        this.service = service;
    }

    @GetMapping("/active")
    public List<PrestationResponse> getAllActivePrestations(){
        return service.getAllActivePrestations();
    }

    @GetMapping
    public List<AdminPrestationResponse> getAllPrestations(){
        return service.getAllPrestations();
    }

    @PostMapping("/{id}")
    public void editPrestation(@PathVariable Long id, @Valid @ModelAttribute AdminPrestationEdit adminPrestationEdit){
        service.editPrestation(id, adminPrestationEdit);
    }

    @PostMapping()
    public void createPrestation(@Valid @ModelAttribute AdminPrestationEdit adminPrestationEdit){
        service.createPrestation(adminPrestationEdit);
    }
}
