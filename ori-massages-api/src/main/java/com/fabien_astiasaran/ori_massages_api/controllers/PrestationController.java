package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationUpdate;
import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.PrestationResponse;
import com.fabien_astiasaran.ori_massages_api.services.PrestationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestations")
public class PrestationController {

    private PrestationService service;

    private PrestationController(PrestationService service){
        this.service = service;
    }

    @GetMapping
    public List<AdminPrestationResponse> getAllPrestations(){
        return service.getAllPrestations();
    }

    @GetMapping("/active")
    public List<PrestationResponse> getAllActivePrestations(){
        return service.getAllActivePrestations();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createPrestation(@Valid @ModelAttribute AdminPrestationCreate adminPrestationCreate){
        service.createPrestation(adminPrestationCreate);
    }

    @PostMapping("/{id}")
    public void updatePrestation(@PathVariable Long id, @Valid @ModelAttribute AdminPrestationUpdate adminPrestationUpdate){
        service.updatePrestation(id, adminPrestationUpdate);
    }

    @DeleteMapping("/{id}")
    public void deletePrestation(@PathVariable Long id){
        service.deletePrestation(id);
    }
}
