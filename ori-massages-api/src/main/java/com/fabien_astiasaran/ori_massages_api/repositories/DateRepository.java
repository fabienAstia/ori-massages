package com.fabien_astiasaran.ori_massages_api.repositories;

import com.fabien_astiasaran.ori_massages_api.entities.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
    Optional<Date> findByDate(LocalDate date);

    List<Date> findByDateBetween(LocalDate firstDay, LocalDate lastDay);

    @Query("SELECT d.date FROM Date d WHERE d.isAvailable")
    List<LocalDate> findAllAvailableDates();
}
