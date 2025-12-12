package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.PrestationResponse;
import com.fabien_astiasaran.ori_massages_api.services.PrestationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/prestations")
public class PrestationController {

    private PrestationService service;

    private PrestationController(PrestationService service){
        this.service = service;
    }

    @GetMapping("/massages")
    public List<PrestationResponse> getMassages(){
        return service.getMassages();
    }

    @GetMapping("/facial-cares")
    public List<PrestationResponse> getFacialCare(){
        return service.getFacialCare();
    }

    @GetMapping
    public Set<AdminPrestationResponse> getAllPrestations(){
        return service.getAllPrestations();
    }
}
