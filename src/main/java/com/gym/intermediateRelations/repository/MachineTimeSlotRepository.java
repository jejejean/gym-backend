package com.gym.intermediateRelations.repository;

import com.gym.intermediateRelations.models.entity.MachineTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineTimeSlotRepository extends JpaRepository<MachineTimeSlot, Integer> {
}
