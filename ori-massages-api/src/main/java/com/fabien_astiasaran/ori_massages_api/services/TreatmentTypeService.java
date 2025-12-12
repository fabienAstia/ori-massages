package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminTreatmentTypeResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.TreatmentTypeCreate;
import com.fabien_astiasaran.ori_massages_api.entities.TreatmentType;
import com.fabien_astiasaran.ori_massages_api.mappers.TreatmentTypeMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.TreatmentTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TreatmentTypeService {

    private final TreatmentTypeRepository treatmentTypeRepository;

    public TreatmentTypeService(TreatmentTypeRepository treatmentTypeRepository) {
        this.treatmentTypeRepository = treatmentTypeRepository;
    }

    public Set<AdminTreatmentTypeResponse> getTreatmentTypes(){
        List<TreatmentType> types = treatmentTypeRepository.findAll();
        return TreatmentTypeMapper.toResponse(types);
    }

    public AdminTreatmentTypeResponse editTreatmentType(Long id, TreatmentTypeCreate treatmentTypeCreate){
        TreatmentType modifiedType = treatmentTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Type not found"));
        modifiedType.setName(treatmentTypeCreate.name());
        modifiedType.setDescription(treatmentTypeCreate.description());
        TreatmentType persisted = treatmentTypeRepository.save(modifiedType);
        return TreatmentTypeMapper.toResponse(persisted);
    }

    public AdminTreatmentTypeResponse createTreatmentType(TreatmentTypeCreate treatmentTypeCreate){
        TreatmentType newType = new TreatmentType();
        newType.setName(treatmentTypeCreate.name());
        newType.setDescription(treatmentTypeCreate.description());
        TreatmentType persisted = treatmentTypeRepository.save(newType);
        return TreatmentTypeMapper.toResponse(persisted);
    }

    public void delete(Long id){
        treatmentTypeRepository.deleteById(id);
    }
}
