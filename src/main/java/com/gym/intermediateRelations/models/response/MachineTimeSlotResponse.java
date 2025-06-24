package com.gym.intermediateRelations.models.response;

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
    private Long machine;
    private Long timeSlot;
    private Integer capacity;
}
