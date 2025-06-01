package com.gym.reservation.models.mapper;


import com.gym.reservation.models.entity.TimeSlot;
import com.gym.reservation.models.request.TimeSlotRequest;
import com.gym.reservation.models.response.TimeSlotResponse;
import com.gym.shared.interfaces.MapperConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeSlotMapper implements MapperConverter<TimeSlotRequest, TimeSlotResponse, TimeSlot> {

    private final ModelMapper modelMapper;

    @Override
    public TimeSlotResponse mapEntityToDto(TimeSlot entity) {
        return modelMapper.map(entity, TimeSlotResponse.class);
    }

    @Override
    public TimeSlot mapDtoToEntity(TimeSlotRequest request) {
        return modelMapper.map(request, TimeSlot.class);
    }
}