package com.gym.reservation.models.request;

import com.gym.shared.interfaces.IHandleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeSlotRequest implements IHandleRequest {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
}
