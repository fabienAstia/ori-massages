package com.fabien_astiasaran.ori_massages_api.repositories;

import com.fabien_astiasaran.ori_massages_api.entities.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
    Date findByDate(LocalDate date);
}
