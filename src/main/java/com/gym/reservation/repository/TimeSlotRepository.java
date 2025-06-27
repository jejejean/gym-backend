package com.gym.reservation.repository;

import com.gym.reservation.models.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    @Query("SELECT DISTINCT ts FROM TimeSlot ts WHERE ts.date = :date")
    List<TimeSlot> findTimeSlotByDate(Date date);
}
