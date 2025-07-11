package com.gym.reservation.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gym.intermediateRelations.models.entity.MachineTimeSlot;
import com.gym.shared.interfaces.IHandleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "machine")
public class Machine implements IHandleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "machines")
    private List<Reserve> reserves;

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MachineTimeSlot> machineTimeSlots;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_maquina")
    private TipeMachine tipeMachine;
}
