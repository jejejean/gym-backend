package com.gym.reservation.models.mapper;

import com.gym.exceptions.NotFoundException;
import com.gym.reservation.models.entity.Attendance;
import com.gym.reservation.models.entity.Machine;
import com.gym.reservation.models.entity.Reserve;
import com.gym.reservation.models.entity.TimeSlot;
import com.gym.reservation.models.request.AttendanceRequest;
import com.gym.reservation.models.request.MachineRequest;
import com.gym.reservation.models.request.ReserveRequest;
import com.gym.reservation.models.request.TimeSlotRequest;
import com.gym.reservation.models.response.AttendanceResponse;
import com.gym.reservation.models.response.MachineResponse;
import com.gym.reservation.models.response.ReserveResponse;
import com.gym.reservation.models.response.TimeSlotResponse;
import com.gym.reservation.repository.MachineRepository;
import com.gym.reservation.repository.TimeSlotRepository;
import com.gym.shared.Constants.ExceptionMessages;
import com.gym.shared.interfaces.MapperConverter;
import com.gym.user.models.entity.User;
import com.gym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReserveMapper implements MapperConverter<ReserveRequest, ReserveResponse, Reserve> {

    private final ModelMapper modelMapper;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    private final MapperConverter<TimeSlotRequest, TimeSlotResponse, TimeSlot> timeSlotMapper;
    private final MapperConverter<AttendanceRequest, AttendanceResponse, Attendance> AttendanceMapper;
    private final MapperConverter<MachineRequest, MachineResponse, Machine> machineMapper;
    private final MachineRepository machineRepository;

    @Override
    public ReserveResponse mapEntityToDto(Reserve entity) {
        ReserveResponse reserveResponse = modelMapper.map(entity, ReserveResponse.class);
        List<TimeSlotResponse> timeSlotResponses = entity.getTimeSlots().stream()
                .map(timeSlotMapper::mapEntityToDto)
                .toList();
        List<MachineResponse> machineResponses = entity.getMachines().stream()
                .map(machineMapper::mapEntityToDto)
                .toList();
        AttendanceResponse attendanceResponse = AttendanceMapper.mapEntityToDto(entity.getAttendance());
        reserveResponse.setTimeSlotResponse(timeSlotResponses);
        reserveResponse.setUserId(entity.getUser().getIdUser());
        reserveResponse.setAttendanceResponse(attendanceResponse);
        reserveResponse.setMachineResponse(machineResponses);
        return reserveResponse;
    }

    @Override
    public Reserve mapDtoToEntity(ReserveRequest request) {
        Reserve reserve = modelMapper.map(request, Reserve.class);
        Attendance attendance =  AttendanceMapper.mapDtoToEntity(request.getAttendanceRequest());
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND));
        List<TimeSlot> timeSlots = request.getTimeSlotId().stream()
                .map(id -> timeSlotRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(ExceptionMessages.TIME_SLOT_NOT_FOUND)))
                .toList();

        List<Machine> machines = request.getMachineRequest().stream()
                .map(machineReq -> machineRepository.findById(machineReq.getId())
                        .orElseThrow(() -> new NotFoundException(ExceptionMessages.MACHINE_NOT_FOUND)))
                .toList();

        reserve.setId(null);
        reserve.setUser(user);
        reserve.setAttendance(attendance);
        reserve.setTimeSlots(timeSlots);
        reserve.setMachines(machines);
        return reserve;
    }
}
