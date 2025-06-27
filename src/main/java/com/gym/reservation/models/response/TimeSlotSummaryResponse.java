package com.gym.reservation.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeSlotSummaryResponse {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Date date;
}
