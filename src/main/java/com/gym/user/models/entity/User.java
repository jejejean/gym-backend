package com.gym.user.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gym.reservation.models.entity.Reserve;
import com.gym.shared.interfaces.IHandleEntity;
import com.gym.userPlans.models.entity.UserPlan;
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
@Table(name = "users")
public class User implements IHandleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    private String username;

    private String email;

    private String password;

    private String phone;

    @Column(name = "user_type")
    private String userType;

    private String status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    @JsonBackReference
    private List<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPlan> userPlans;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reserve> reserves;
}