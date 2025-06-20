package com.gym.reservation.models.request;

import com.gym.shared.interfaces.IHandleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MachineRequest implements IHandleRequest {
    private Long id;
    private String name;
}
