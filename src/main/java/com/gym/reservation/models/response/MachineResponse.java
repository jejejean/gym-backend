package com.gym.reservation.models.response;

import com.gym.shared.interfaces.IHandleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MachineResponse implements IHandleResponse {
    private Long id;
    private String name;
    private Long tipeMachineId;
}
