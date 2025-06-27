package com.gym.intermediateRelations.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gym.reservation.models.entity.Machine;
import com.gym.reservation.models.entity.TimeSlot;
import com.gym.shared.interfaces.IHandleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "machine_timeslots")
public class MachineTimeSlot implements IHandleEntity {
    @EmbeddedId
    private MachineTimeSlotId id = new MachineTimeSlotId();

    @ManyToOne
    @MapsId("machineId")
    @JoinColumn(name = "machine_id")
    @JsonBackReference
    private Machine machine;

    @ManyToOne
    @MapsId("timeSlotId")
    @JoinColumn(name = "time_slot_id")
    @JsonBackReference
    private TimeSlot timeSlot;

    @Column(nullable = false)
    private Integer capacity;
}
