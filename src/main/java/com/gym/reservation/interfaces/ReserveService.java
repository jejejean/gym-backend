package com.gym.reservation.interfaces;

import com.gym.reservation.models.response.ReserveByDayResponse;
import com.gym.reservation.models.response.ReserveResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReserveService {
    List<ReserveResponse> findAllByUserId(Long userId);
    List<String> getAllDatesWithReservationsByUserId(Long userId);
    List<ReserveByDayResponse> findAllByReservationDate(LocalDate reservationDate);
}
