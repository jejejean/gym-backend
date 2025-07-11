package com.gym.reservation.models.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gym.intermediateRelations.models.entity.MachineTimeSlot;
import com.gym.shared.interfaces.IHandleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "time_slots")
public class TimeSlot implements IHandleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    private Date date;

    @ManyToMany(mappedBy = "timeSlots")
    private List<Reserve> reserves;

    @OneToMany(mappedBy = "timeSlot", cascade = CascadeType.ALL)
    private List<SlotCapacity> slotCapacities;

    @OneToMany(mappedBy = "timeSlot", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MachineTimeSlot> machineTimeSlots = new ArrayList<>();

}
