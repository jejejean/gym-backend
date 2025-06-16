package com.gym.reservation.models.mapper;

import com.gym.reservation.models.entity.Machine;
import com.gym.reservation.models.request.MachineRequest;
import com.gym.reservation.models.response.MachineResponse;
import com.gym.shared.interfaces.MapperConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MachineMapper implements MapperConverter<MachineRequest, MachineResponse, Machine> {

    private final ModelMapper modelMapper;

    @Override
    public MachineResponse mapEntityToDto(Machine entity) {
        return modelMapper.map(entity, MachineResponse.class);
    }

    @Override
    public Machine mapDtoToEntity(MachineRequest request) {
        return modelMapper.map(request, Machine.class);
    }
}
