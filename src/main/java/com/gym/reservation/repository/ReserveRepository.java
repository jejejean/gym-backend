package com.gym.reservation.repository;

import com.gym.reservation.models.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    @Query("SELECT r FROM Reserve r WHERE r.user.idUser = :userId")
    List<Reserve> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Reserve r WHERE r.reservationDate = :reservationDate")
    List<Reserve> findAllByReservationDate(LocalDate reservationDate);
}
