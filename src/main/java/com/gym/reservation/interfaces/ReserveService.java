package com.gym.reservation.interfaces;

import com.gym.reservation.models.request.ReserveRequest;
import com.gym.reservation.models.request.ReserveSimpleRequest;
import com.gym.reservation.models.response.AttendanceResponse;
import com.gym.reservation.models.response.ReserveByDayResponse;
import com.gym.reservation.models.response.ReserveResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReserveService {
    List<ReserveResponse> findAllByUserId(Long userId);
    List<String> getAllDatesWithReservationsByUserId(Long userId);
    List<ReserveByDayResponse> findAllByReservationDate(LocalDate reservationDate);
    AttendanceResponse getAttendanceByUserId(Long id, ReserveSimpleRequest request);
    void resendReservationReminder(Long reserveId);
}
