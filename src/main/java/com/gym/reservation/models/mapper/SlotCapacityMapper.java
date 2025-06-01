package com.gym.reservation.models.mapper;

import com.gym.reservation.models.entity.SlotCapacity;
import com.gym.reservation.models.request.SlotCapacityRequest;
import com.gym.reservation.models.response.SlotCapacityResponse;
import com.gym.shared.interfaces.MapperConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlotCapacityMapper implements MapperConverter<SlotCapacityRequest, SlotCapacityResponse, SlotCapacity> {

    private final ModelMapper modelMapper;

    @Override
    public SlotCapacityResponse mapEntityToDto(SlotCapacity entity) {
        return null;
    }

    @Override
    public SlotCapacity mapDtoToEntity(SlotCapacityRequest request) {
        return null;
    }
}
