package com.gym.reservation.models.mapper;


import com.gym.reservation.models.entity.Machine;
import com.gym.reservation.models.entity.TimeSlot;
import com.gym.reservation.models.request.MachineRequest;
import com.gym.reservation.models.request.TimeSlotRequest;
import com.gym.reservation.models.response.MachineResponse;
import com.gym.reservation.models.response.TimeSlotResponse;
import com.gym.shared.interfaces.MapperConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TimeSlotMapper implements MapperConverter<TimeSlotRequest, TimeSlotResponse, TimeSlot> {

    private final ModelMapper modelMapper;
    private final MapperConverter<MachineRequest, MachineResponse, Machine> machineMapper;
    @Override
    public TimeSlotResponse mapEntityToDto(TimeSlot entity) {
        TimeSlotResponse response = modelMapper.map(entity, TimeSlotResponse.class);
        List<MachineResponse> machineResponses = entity.getMachines().stream()
                .map(machineMapper::mapEntityToDto)
                .toList();
        response.setMachineResponse(machineResponses);
        return response;
    }

    @Override
    public TimeSlot mapDtoToEntity(TimeSlotRequest request) {
        return modelMapper.map(request, TimeSlot.class);
    }
}