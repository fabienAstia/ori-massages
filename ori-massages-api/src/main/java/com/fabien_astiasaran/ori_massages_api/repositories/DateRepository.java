package com.fabien_astiasaran.ori_massages_api.repositories;

import com.fabien_astiasaran.ori_massages_api.entities.Date;
import com.fabien_astiasaran.ori_massages_api.entities.DateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
    Optional<Date> findByDate(LocalDate date);

    @Query("SELECT d FROM Date d WHERE d.dateStatus = :status")
    List<Date> findAllByDateStatus(DateStatus status);
}
