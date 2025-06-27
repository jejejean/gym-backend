package com.gym.intermediateRelations.models.mapper;

import com.gym.intermediateRelations.models.entity.MachineTimeSlot;
import com.gym.intermediateRelations.models.request.MachineTimeSlotRequest;
import com.gym.intermediateRelations.models.response.MachineTimeSlotResponse;
import com.gym.reservation.models.entity.Machine;
import com.gym.reservation.models.entity.TimeSlot;
import com.gym.reservation.models.request.MachineRequest;
import com.gym.reservation.models.request.TimeSlotRequest;
import com.gym.reservation.models.response.MachineResponse;
import com.gym.reservation.models.response.TimeSlotResponse;
import com.gym.reservation.models.response.TimeSlotSummaryResponse;
import com.gym.shared.interfaces.MapperConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MachineTimeSlotMapper implements MapperConverter<MachineTimeSlotRequest, MachineTimeSlotResponse, MachineTimeSlot> {

    private final ModelMapper modelMapper;

    @Lazy
    @Autowired
    private MapperConverter<MachineRequest, MachineResponse, Machine> machineMapper;

    @Lazy
    @Autowired
    private MapperConverter<TimeSlotRequest, TimeSlotResponse, TimeSlot> timeSlotMapper;

    @Override
    public MachineTimeSlotResponse mapEntityToDto(MachineTimeSlot entity) {
        MachineTimeSlotResponse dto = new MachineTimeSlotResponse();
        dto.setCapacity(entity.getCapacity());
        dto.setMachine(machineMapper.mapEntityToDto(entity.getMachine()));
        // Mapeo manual del resumen de TimeSlot
        TimeSlot timeSlot = entity.getTimeSlot();
        TimeSlotSummaryResponse timeSlotSummary = new TimeSlotSummaryResponse();
        timeSlotSummary.setId(timeSlot.getId());
        timeSlotSummary.setStartTime(timeSlot.getStartTime());
        timeSlotSummary.setEndTime(timeSlot.getEndTime());
        timeSlotSummary.setDate(timeSlot.getDate());

        dto.setTimeSlot(timeSlotSummary);
        return dto;
    }

    @Override
    public MachineTimeSlot mapDtoToEntity(MachineTimeSlotRequest request) {
        return modelMapper.map(request, MachineTimeSlot.class);
    }
}
