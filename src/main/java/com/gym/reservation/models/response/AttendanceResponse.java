package com.gym.reservation.models.response;

import com.gym.shared.interfaces.IHandleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceResponse implements IHandleResponse {
    private Long id;
    private Boolean attended;
    private LocalDateTime checkinTime;
}
