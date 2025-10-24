package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.PrestationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Prestation;
import com.fabien_astiasaran.ori_massages_api.services.PrestationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/soins-visage")
    public List<PrestationResponse> getFacialCare(){
        return service.getFacialCare();
    }
}
