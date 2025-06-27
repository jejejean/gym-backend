package com.gym.intermediateRelations.models.response;

import com.gym.reservation.models.response.MachineResponse;
import com.gym.reservation.models.response.TimeSlotSummaryResponse;
import com.gym.shared.interfaces.IHandleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MachineTimeSlotResponse implements IHandleResponse {
    private MachineResponse machine;
    private TimeSlotSummaryResponse timeSlot;
    private Integer capacity;
}
