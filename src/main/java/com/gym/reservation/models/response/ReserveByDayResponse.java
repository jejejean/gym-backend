package com.gym.reservation.models.response;

import com.gym.user.models.response.UserSimpleResponse;
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
public class ReserveByDayResponse {
    private Long id;
    private LocalDate reservationDate;
    private UserSimpleResponse userSimpleResponse;
    private List<TimeSlotResponse> timeSlotResponse;
    private AttendanceResponse attendanceResponse;
    private List<MachineResponse> machineResponse;
}
