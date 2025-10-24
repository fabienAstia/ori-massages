package com.fabien_astiasaran.ori_massages_api.repositories;

import com.fabien_astiasaran.ori_massages_api.entities.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestationRepository extends JpaRepository<Prestation, Long> {

    List<Prestation> findByTreatmentType_Name(String name);

    Prestation findByName(String name);
}
