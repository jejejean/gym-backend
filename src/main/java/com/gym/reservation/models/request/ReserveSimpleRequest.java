package com.gym.reservation.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReserveSimpleRequest {
    private Long id;
    private AttendanceRequest attendanceRequest;
}
