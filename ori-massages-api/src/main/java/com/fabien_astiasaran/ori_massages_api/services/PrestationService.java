package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationEdit;
import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.PrestationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Duration;
import com.fabien_astiasaran.ori_massages_api.entities.Prestation;
import com.fabien_astiasaran.ori_massages_api.entities.TreatmentType;
import com.fabien_astiasaran.ori_massages_api.mappers.PrestationMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.PrestationRepository;
import com.fabien_astiasaran.ori_massages_api.utils.ImageCategory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrestationService {

    private final PrestationRepository prestationRepository;
    private final TreatmentTypeService treatmentTypeService;
    private final DurationService durationService;
    private final ImageService imageService;

    public PrestationService(PrestationRepository prestationRepository, TreatmentTypeService treatmentTypeService, DurationService durationService, ImageService imageService) {
        this.prestationRepository = prestationRepository;
        this.treatmentTypeService = treatmentTypeService;
        this.durationService = durationService;
        this.imageService = imageService;
    }

    public List<AdminPrestationResponse> getAllPrestations(){
        return prestationRepository.findAll().stream().map(PrestationMapper::toAdminResponse).toList();
    }

    public List<PrestationResponse> getAllActivePrestations() {
        return prestationRepository.findAllByActiveTrue().stream().map(PrestationMapper::toResponse).toList();
    }

    @Transactional
    public void editPrestation(Long id, AdminPrestationEdit newRequest){
        Prestation prestation = prestationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prestation is not found"));
        prestation.setName(newRequest.getName());
        TreatmentType type = treatmentTypeService.getTreatmentType(newRequest.getTypeName());
        prestation.setTreatmentType(type);
        Duration duration = durationService.getDuration(newRequest.getDurationLabel());
        prestation.setDuration(duration);
        prestation.setActive(newRequest.getActive());
        MultipartFile image = newRequest.getImage();
        handleImage(image, prestation);
        prestationRepository.save(prestation);
    }

    private void handleImage(MultipartFile image, Prestation prestation) {
        if(image != null && !image.isEmpty()){
            String imageId = imageService.buildImageId(image);
            imageService.storeImage(image, imageId, ImageCategory.PRESTATION);
            imageService.deletePreviousImage(prestation.getImagePath(), ImageCategory.PRESTATION);
            prestation.setImagePath(imageId);
        }
    }

    public void createPrestation(AdminPrestationEdit adminPrestationEdit) {
    }


}
