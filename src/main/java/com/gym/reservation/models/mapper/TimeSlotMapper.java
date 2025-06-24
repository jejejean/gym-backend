package com.gym.reservation.models.mapper;


import com.gym.intermediateRelations.models.entity.MachineTimeSlot;
import com.gym.intermediateRelations.models.request.MachineTimeSlotRequest;
import com.gym.intermediateRelations.models.response.MachineTimeSlotResponse;
import com.gym.reservation.models.entity.TimeSlot;
import com.gym.reservation.models.request.TimeSlotRequest;
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
    private final MapperConverter<MachineTimeSlotRequest, MachineTimeSlotResponse, MachineTimeSlot> machineTimeSlotMapper;
    @Override
    public TimeSlotResponse mapEntityToDto(TimeSlot entity) {
        TimeSlotResponse response = modelMapper.map(entity, TimeSlotResponse.class);
        List<MachineTimeSlotResponse> machineTimeSlotResponse = entity.getMachineTimeSlots().stream()
                .map(machineTimeSlotMapper::mapEntityToDto)
                .toList();
        response.setMachineResponse(machineTimeSlotResponse);
        return response;
    }

    @Override
    public TimeSlot mapDtoToEntity(TimeSlotRequest request) {
        return modelMapper.map(request, TimeSlot.class);
    }
}