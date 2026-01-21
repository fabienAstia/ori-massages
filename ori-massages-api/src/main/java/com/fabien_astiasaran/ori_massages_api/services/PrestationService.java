package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationUpdate;
import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.PrestationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Duration;
import com.fabien_astiasaran.ori_massages_api.entities.Prestation;
import com.fabien_astiasaran.ori_massages_api.entities.TreatmentType;
import com.fabien_astiasaran.ori_massages_api.mappers.PrestationMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.PrestationRepository;
import com.fabien_astiasaran.ori_massages_api.services.order.NormalizeOrder;
import com.fabien_astiasaran.ori_massages_api.utils.ImageCategory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class PrestationService {

    private final PrestationRepository prestationRepository;
    private final TreatmentTypeService treatmentTypeService;
    private final DurationService durationService;
    private final ImageService imageService;
    private final NormalizeOrder normalizeOrder;

    public PrestationService(PrestationRepository prestationRepository, TreatmentTypeService treatmentTypeService, DurationService durationService, ImageService imageService, NormalizeOrder normalizeOrder) {
        this.prestationRepository = prestationRepository;
        this.treatmentTypeService = treatmentTypeService;
        this.durationService = durationService;
        this.imageService = imageService;
        this.normalizeOrder = normalizeOrder;
    }

    public List<AdminPrestationResponse> getAllPrestations(){
        return prestationRepository.findAll().stream().map(PrestationMapper::toAdminResponse).toList();
    }

    public List<PrestationResponse> getAllActivePrestations() {
        return prestationRepository.findAllByActiveTrueOrderByDisplayOrder().stream().map(PrestationMapper::toResponse).toList();
    }

    @Transactional
    public AdminPrestationResponse createPrestation(AdminPrestationCreate newRequest) {
        Prestation newPrestation = normalizeOrder.whenCreating(new Prestation(), newRequest.getTypeId(), newRequest.getDisplayOrder());
        newPrestation.setName(newRequest.getName());
        newPrestation.setDescription(newRequest.getDescription());
        newPrestation.setPrice(Double.valueOf(newRequest.getPrice()));
        newPrestation.setActive(newRequest.getActive());
        handleImage(newRequest.getImage(), newPrestation);
        Duration duration = durationService.getDurationById(newRequest.getDurationId());
        TreatmentType type = treatmentTypeService.getTreatmentTypeById(newRequest.getTypeId());
        newPrestation.setTreatmentType(type);
        newPrestation.setDuration(duration);
        return PrestationMapper.toAdminResponse(prestationRepository.save(newPrestation));
    }

    @Transactional
    public void updatePrestation(Long id, AdminPrestationUpdate newRequest){
        Prestation prestation = getPrestationById(id);
        normalizeOrder.whenUpdating(prestation, newRequest.getTypeId(), newRequest.getDisplayOrder());
        prestation.setName(newRequest.getName());
        TreatmentType type = treatmentTypeService.getTreatmentTypeById(newRequest.getTypeId());
        prestation.setTreatmentType(type);
        Duration duration = durationService.getDurationById(newRequest.getDurationId());
        prestation.setDuration(duration);
        prestation.setActive(newRequest.getActive());
        handleImage(newRequest.getImage(), prestation);
        prestationRepository.save(prestation);
    }

    @Transactional
    public void deletePrestation(Long id) {
        Prestation toDelete = getPrestationById(id);
        normalizeOrder.whenDeleting(toDelete);
        prestationRepository.deleteById(id);
    }

    private Prestation getPrestationById(Long id) {
        return prestationRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(String.format("Prestation not found with this ID : %d", id)));
    }

    private void handleImage(MultipartFile image, Prestation prestation) {
        if(image != null && !image.isEmpty()){
            String imageId = imageService.buildImageId(image);
            imageService.storeImage(image, imageId, ImageCategory.PRESTATION);
            if(prestation.getImagePath() != null && !prestation.getImagePath().isBlank()){
                imageService.deletePreviousImage(prestation.getImagePath(), ImageCategory.PRESTATION);
            }
            prestation.setImagePath(imageId);
        }
    }
}
