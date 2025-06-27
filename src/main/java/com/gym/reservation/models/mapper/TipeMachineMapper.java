package com.gym.reservation.models.mapper;

import com.gym.reservation.models.entity.TipeMachine;
import com.gym.reservation.models.response.TipeMachineResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TipeMachineMapper {

    private final ModelMapper modelMapper;

    public TipeMachineResponse mapEntityToDto(TipeMachine entity) {
        return modelMapper.map(entity, TipeMachineResponse.class);
    }
}
