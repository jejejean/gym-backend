package com.gym.user.repository;

import com.gym.user.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserEntityByEmail(String email);
    boolean existsUserEntityByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.userType = 'Cliente' ")
    List<User> findAllByUserType();

    boolean existsByEmail(String email);
}
