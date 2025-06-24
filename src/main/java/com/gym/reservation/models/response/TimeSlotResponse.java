package com.gym.reservation.models.response;

import com.gym.intermediateRelations.models.response.MachineTimeSlotResponse;
import com.gym.shared.interfaces.IHandleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeSlotResponse implements IHandleResponse {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Date date;
    private List<MachineTimeSlotResponse> machineResponse;
}
