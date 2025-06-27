package com.gym.reservation.repository;

import com.gym.reservation.models.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    @Query("SELECT DISTINCT m FROM Machine m JOIN m.machineTimeSlots mts JOIN mts.timeSlot ts WHERE ts.date = :date")
    List<Machine> findMachineByDate(Date date);
}
