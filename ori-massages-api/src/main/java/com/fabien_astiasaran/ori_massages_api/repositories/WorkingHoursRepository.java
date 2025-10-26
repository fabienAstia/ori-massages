package com.fabien_astiasaran.ori_massages_api.repositories;

import com.fabien_astiasaran.ori_massages_api.entities.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {
    WorkingHours findByName(String name);
}
