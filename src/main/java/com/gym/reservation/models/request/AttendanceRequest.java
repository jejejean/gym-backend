package com.gym.reservation.models.request;

import com.gym.shared.interfaces.IHandleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceRequest implements IHandleRequest {
    private Long id;
    private Boolean attended;
    private LocalDateTime checkinTime;
    private ReserveRequest reserveRequest;
}
