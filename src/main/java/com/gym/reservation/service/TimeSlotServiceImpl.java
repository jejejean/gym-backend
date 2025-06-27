package com.gym.reservation.service;

import com.gym.intermediateRelations.models.entity.MachineTimeSlot;
import com.gym.intermediateRelations.repository.MachineTimeSlotRepository;
import com.gym.reservation.interfaces.TimeSlotService;
import com.gym.reservation.models.entity.Machine;
import com.gym.reservation.models.entity.TimeSlot;
import com.gym.reservation.models.request.MachineRequest;
import com.gym.reservation.models.request.TimeSlotRequest;
import com.gym.reservation.models.response.MachineResponse;
import com.gym.reservation.models.response.TimeSlotResponse;
import com.gym.reservation.models.response.TimeSlotSummaryResponse;
import com.gym.reservation.repository.MachineRepository;
import com.gym.reservation.repository.TimeSlotRepository;
import com.gym.shared.interfaces.MapperConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final MapperConverter<TimeSlotRequest, TimeSlotResponse, TimeSlot> timeSlotMapper;
    private final MachineRepository machineRepository;
    private final MachineTimeSlotRepository machineTimeSlotRepository;
    private final MapperConverter<MachineRequest, MachineResponse, Machine> machineMapper;

    @Override
    public List<TimeSlotResponse> getAllTimeSlots() {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        return timeSlots.stream()
                .map(timeSlotMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public List<TimeSlotResponse> createAll(TimeSlotRequest request) {
        List<TimeSlot> timeSlots = new java.util.ArrayList<>();
        java.time.LocalTime start = java.time.LocalTime.of(9, 0);
        java.time.LocalTime end = java.time.LocalTime.of(22, 0);

        List<Machine> machines = machineRepository.findAllById(request.getIdsMachine());

        java.time.LocalTime current = start;
        while (current.isBefore(end)) {
            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setStartTime(current);
            timeSlot.setEndTime(current.plusMinutes(10));
            timeSlot.setDate(request.getDate());
            timeSlotRepository.save(timeSlot);

            for (Machine machine : machines) {
                MachineTimeSlot mts = new MachineTimeSlot();
                mts.setMachine(machine);
                mts.setTimeSlot(timeSlot);
                mts.setCapacity(request.getCapacity());
                machineTimeSlotRepository.save(mts);
            }

            timeSlots.add(timeSlot);
            current = current.plusMinutes(10);
        }

        return timeSlots.stream()
                .map(timeSlotMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public List<TimeSlotResponse> updateAll(TimeSlotRequest request) {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        for (TimeSlot timeSlot : timeSlots) {
            timeSlot.setDate(request.getDate());
        }
        timeSlotRepository.saveAll(timeSlots);

        return timeSlots.stream().map(ts -> {
            TimeSlotResponse response = new TimeSlotResponse();
            response.setId(ts.getId());
            response.setStartTime(ts.getStartTime());
            response.setEndTime(ts.getEndTime());
            response.setDate(ts.getDate());
            return response;
        }).toList();
    }

    @Override
    public List<MachineResponse> getMachineByDate(Date date) {
        List<Machine> machines = machineRepository.findMachineByDate(date);
        return machines.stream()
                .map(machineMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public List<TimeSlotSummaryResponse> getTimeSlotSummaryByDate(Date date) {
        List<TimeSlot> timeSlots = timeSlotRepository.findTimeSlotByDate(date);
        return timeSlots.stream()
                .map(ts -> {
                    TimeSlotSummaryResponse summary = new TimeSlotSummaryResponse();
                    summary.setId(ts.getId());
                    summary.setStartTime(ts.getStartTime());
                    summary.setEndTime(ts.getEndTime());
                    summary.setDate(ts.getDate());
                    return summary;
                })
                .toList();
    }

}
