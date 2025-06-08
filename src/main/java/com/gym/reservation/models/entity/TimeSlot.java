package com.gym.reservation.models.entity;


import com.gym.shared.interfaces.IHandleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
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

    private Integer capacity;

    @ManyToMany(mappedBy = "timeSlots")
    private List<Reserve> reserves;

    @OneToMany(mappedBy = "timeSlot", cascade = CascadeType.ALL)
    private List<SlotCapacity> slotCapacities;
}