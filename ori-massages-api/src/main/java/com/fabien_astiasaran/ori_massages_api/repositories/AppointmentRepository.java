package com.fabien_astiasaran.ori_massages_api.repositories;

import com.fabien_astiasaran.ori_massages_api.entities.Appointment;
import com.fabien_astiasaran.ori_massages_api.entities.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("""
            SELECT a.slot.date.date FROM Appointment a
            WHERE a.status in ('REGISTERED', 'CONFIRMED')
            """)
    Set<LocalDate> findBookedDates();

    @Query("""
            SELECT a.slot FROM Appointment a
            WHERE a.status in ('REGISTERED', 'CONFIRMED')
            """)
    Set<Slot> findBookedSlots();
}
