package com.fabien_astiasaran.ori_massages_api.services.order;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminPrestationUpdate;
import com.fabien_astiasaran.ori_massages_api.entities.Prestation;
import com.fabien_astiasaran.ori_massages_api.repositories.PrestationRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NormalizeOrder {
    private final PrestationRepository prestationRepository;

    public NormalizeOrder(PrestationRepository prestationRepository) {
        this.prestationRepository = prestationRepository;
    }

    public Prestation whenCreating(Prestation toCreate, Long typeId, Integer newOrder){
        List<Prestation> prestations = getPrestationsByTypeIdSortedByDisplayOrder(typeId);
        checkValidBondsWhenCreating(newOrder, prestations);
        prestations.add(newOrder - 1, toCreate);
        reassignOrder(prestations);
        return toCreate;
    }

    public Prestation whenUpdating(Prestation toUpdate, Long newTypeId, Integer newOrder){
        List<Prestation> prestations = getPrestationsByTypeIdSortedByDisplayOrder(newTypeId);
        checkValidBondsWhenEditing(newOrder, prestations);
        Long previousTypeId = toUpdate.getTreatmentType().getId();

        if(newTypeId.equals(previousTypeId)){
            if(toUpdate.getDisplayOrder() == newOrder){
                return toUpdate;
            }
            prestations.remove(toUpdate);
            prestations.add(newOrder - 1, toUpdate);
            reassignOrder(prestations);
        }else{
            List<Prestation> previousList = getPrestationsByTypeIdSortedByDisplayOrder(toUpdate.getTreatmentType().getId());
            previousList.remove(toUpdate);
            reassignOrder(previousList);
            prestations.add(newOrder - 1, toUpdate);
            reassignOrder(prestations);
        }
        return toUpdate;
    }

    public void whenDeleting(Prestation toDelete){
        List<Prestation> prestations = getPrestationsByTypeIdSortedByDisplayOrder(toDelete.getTreatmentType().getId());
        prestations.remove(toDelete);
        reassignOrder(prestations);
    }

    private void reassignOrder(List<Prestation> prestations) {
        for(int i = 0; i < prestations.size(); i++){
            Prestation p = prestations.get(i);
            p.setDisplayOrder(i + 1);
        }
    }

    public List<Prestation> getPrestationsByTypeIdSortedByDisplayOrder(Long typeId){
        List<Prestation> prestationsByType = prestationRepository.findByTreatmentTypeId(typeId);
        prestationsByType.sort(Comparator.comparing(Prestation::getDisplayOrder));
        return prestationsByType;
    }

    private static void checkValidBondsWhenCreating(Integer newOrder, List<Prestation> prestations) {
        if(newOrder > prestations.size() + 1){
            throw new IllegalArgumentException(
                    String.format("Order of prestation must be between 1 and %d", prestations.size() + 1));
        }
    }

    private static void checkValidBondsWhenEditing(Integer newOrder, List<Prestation> prestations) {
        if(newOrder > prestations.size()){
            throw new IllegalArgumentException(
                    String.format("Order of prestation must be between 1 and %d", prestations.size()));
        }
    }

}
