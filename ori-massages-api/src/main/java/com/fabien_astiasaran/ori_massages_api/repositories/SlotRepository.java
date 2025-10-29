package com.fabien_astiasaran.ori_massages_api.repositories;

import com.fabien_astiasaran.ori_massages_api.entities.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
}
