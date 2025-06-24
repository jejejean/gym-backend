package com.gym.intermediateRelations.models.mapper;

import com.gym.intermediateRelations.models.entity.MachineTimeSlot;
import com.gym.intermediateRelations.models.request.MachineTimeSlotRequest;
import com.gym.intermediateRelations.models.response.MachineTimeSlotResponse;
import com.gym.shared.interfaces.MapperConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MachineTimeSlotMapper implements MapperConverter<MachineTimeSlotRequest, MachineTimeSlotResponse, MachineTimeSlot> {

    private final ModelMapper modelMapper;

    @Override
    public MachineTimeSlotResponse mapEntityToDto(MachineTimeSlot entity) {
        return modelMapper.map(entity, MachineTimeSlotResponse.class);
    }

    @Override
    public MachineTimeSlot mapDtoToEntity(MachineTimeSlotRequest request) {
        return modelMapper.map(request, MachineTimeSlot.class);
    }
}
