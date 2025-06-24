package com.gym.intermediateRelations.models.request;

import com.gym.shared.interfaces.IHandleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MachineTimeSlotRequest implements IHandleRequest {
    private Long machine;
    private Long timeSlot;
    private Integer capacity;
}
