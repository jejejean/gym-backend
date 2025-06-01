package com.gym.userPlans.models.entity;

import com.gym.shared.interfaces.IHandleEntity;
import com.gym.user.models.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_plans")
public class UserPlan implements IHandleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private String status; // ACTIVO, VENCIDO, CANCELADO

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_plan_type")
    private PlanType planType;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User user;
}