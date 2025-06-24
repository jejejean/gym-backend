package com.gym.intermediateRelations.models.entity;

import jakarta.persistence.Embeddable;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class MachineTimeSlotId implements Serializable {
    private Long machineId;
    private Long timeSlotId;
}