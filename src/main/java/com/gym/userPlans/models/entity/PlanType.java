package com.gym.userPlans.models.entity;
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
@Table(name = "plan_types")
public class PlanType implements IHandleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "duration_days")
    private Integer durationDays;

    private Double price;

    @OneToMany(mappedBy = "planType", cascade = CascadeType.ALL)
    private List<UserPlan> userPlans;

}