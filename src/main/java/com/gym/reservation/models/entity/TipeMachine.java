package com.gym.reservation.models.entity;

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
@Table(name = "tipo_maquina")
public class TipeMachine implements IHandleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "tipeMachine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Machine> machines;
}
