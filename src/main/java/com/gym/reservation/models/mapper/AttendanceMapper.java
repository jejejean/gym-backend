package com.gym.reservation.models.mapper;

import com.gym.reservation.models.entity.Attendance;
import com.gym.reservation.models.request.AttendanceRequest;
import com.gym.reservation.models.response.AttendanceResponse;
import com.gym.shared.interfaces.MapperConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttendanceMapper implements MapperConverter<AttendanceRequest, AttendanceResponse, Attendance> {

    private final ModelMapper modelMapper;

    @Override
    public AttendanceResponse mapEntityToDto(Attendance entity) {
        return modelMapper.map(entity, AttendanceResponse.class);

    }

    @Override
    public Attendance mapDtoToEntity(AttendanceRequest request) {
        Attendance attendance = modelMapper.map(request, Attendance.class);


        attendance.setId(null);
        attendance.setAttended(false);
        return attendance;
    }
}