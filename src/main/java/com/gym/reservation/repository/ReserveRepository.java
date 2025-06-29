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

    @Query("SELECT COUNT(r) FROM Reserve r")
    Long countAllReserves();

    @Query("SELECT MONTH(r.reservationDate) as mes, COUNT(r) as total FROM Reserve r GROUP BY MONTH(r.reservationDate)")
    List<Object[]> countReservesByMonth();

    @Query("SELECT tm.name, COUNT(r) FROM Reserve r JOIN r.machines m JOIN m.tipeMachine tm GROUP BY tm.name")
    List<Object[]> countReservesByTipeMachine();

    @Query("SELECT m.name, COUNT(r) FROM Reserve r JOIN r.machines m GROUP BY m.name")
    List<Object[]> countReservesByMachine();
}
