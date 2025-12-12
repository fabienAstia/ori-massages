package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.PrestationResponse;
import com.fabien_astiasaran.ori_massages_api.mappers.PrestationMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.PrestationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PrestationService {

    private final static String MASSAGE_LABEL = "Massages";
    private final static String FACIAL_CARE_LABEL = "Soins visage";
    private PrestationRepository prestationRepository;

    public PrestationService(PrestationRepository prestationRepository) {
        this.prestationRepository = prestationRepository;
    }

    public List<PrestationResponse> getMassages(){
        List<PrestationResponse> massages = new ArrayList<>();
        prestationRepository.findByTreatmentType_Name(MASSAGE_LABEL).forEach(prestation ->
            massages.add(PrestationMapper.toResponse(prestation))
        );
        return massages;
    }

    public List<PrestationResponse> getFacialCare() {
        List<PrestationResponse> facialCare = new ArrayList<>();
        prestationRepository.findByTreatmentType_Name(FACIAL_CARE_LABEL).forEach(prestation ->
            facialCare.add(PrestationMapper.toResponse(prestation))
        );
        return facialCare;
    }

    public Set<AdminPrestationResponse> getAllPrestations(){
        Set<AdminPrestationResponse> allPrestations = new HashSet<>();
        prestationRepository.findAll().forEach(prestation ->
                allPrestations.add(PrestationMapper.toAdminResponse(prestation)));
        return allPrestations;
    }
}
