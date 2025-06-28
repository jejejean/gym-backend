package com.gym.intermediateRelations.repository;

import com.gym.intermediateRelations.models.entity.MachineTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineTimeSlotRepository extends JpaRepository<MachineTimeSlot, Integer> {

    @Query("SELECT mts.capacity FROM MachineTimeSlot mts WHERE mts.machine.id = :machineId AND mts.timeSlot.id = :timeSlotId")
    MachineTimeSlot findByMachineIdAndTimeSlotId(@Param("machineId") Long machineId, @Param("timeSlotId") Long timeSlotId);
}
