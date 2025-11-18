package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.DurationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.PrestationResponse;
import com.fabien_astiasaran.ori_massages_api.repositories.PrestationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            massages.add(
                    new PrestationResponse(
                            prestation.getId(),
                            prestation.getName(),
                            prestation.getDescription(),
                            prestation.getPrice(),
                            prestation.isActive(),
                            prestation.getImagePath(),
                            new DurationResponse(
                                    prestation.getDuration().getId(),
                                    prestation.getDuration().getValue(),
                                    prestation.getDuration().getLabel(),
                                    prestation.getDuration().getBreakTime())
                    )
            )
        );
        return massages;
    }

    public List<PrestationResponse> getFacialCare() {
        List<PrestationResponse> facialCare = new ArrayList<>();
        prestationRepository.findByTreatmentType_Name(FACIAL_CARE_LABEL).forEach(prestation ->
            facialCare.add(
                    new PrestationResponse(
                            prestation.getId(),
                            prestation.getName(),
                            prestation.getDescription(),
                            prestation.getPrice(),
                            prestation.isActive(),
                            prestation.getImagePath(),
                            new DurationResponse(
                                    prestation.getDuration().getId(),
                                    prestation.getDuration().getValue(),
                                    prestation.getDuration().getLabel(),
                                    prestation.getDuration().getBreakTime())
                    )
            )
        );
        return facialCare;
    }
}
