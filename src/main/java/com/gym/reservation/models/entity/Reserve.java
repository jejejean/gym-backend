package com.gym.reservation.models.entity;
import com.gym.shared.interfaces.IHandleEntity;
import com.gym.user.models.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reserve")
public class Reserve implements IHandleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "reserve_time_slots",
            joinColumns = @JoinColumn(name = "reserve_id"),
            inverseJoinColumns = @JoinColumn(name = "time_slot_id")
    )
    private List<TimeSlot> timeSlots;

    @ManyToMany
    @JoinTable(
            name = "reserve_machine",
            joinColumns = @JoinColumn(name = "reserve_id"),
            inverseJoinColumns = @JoinColumn(name = "machine_id")
    )
    private List<Machine> machine;

    @OneToOne(mappedBy = "reserve", cascade = CascadeType.ALL)
    private Attendance attendance;

}