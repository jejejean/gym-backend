package com.gym.reservation.models.response;

import com.gym.shared.interfaces.IHandleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReserveResponse implements IHandleResponse {
    private Long id;
    private String details;
    private LocalDate reservationDate;
    private Long userId;
    private List<TimeSlotResponse> timeSlotResponse;
    private AttendanceResponse attendanceResponse;
}
