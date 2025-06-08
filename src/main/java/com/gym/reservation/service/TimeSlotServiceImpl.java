package com.gym.reservation.service;

import com.gym.reservation.interfaces.TimeSlotService;
import com.gym.reservation.models.entity.TimeSlot;
import com.gym.reservation.models.request.TimeSlotRequest;
import com.gym.reservation.models.response.TimeSlotResponse;
import com.gym.reservation.repository.TimeSlotRepository;
import com.gym.shared.interfaces.MapperConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final MapperConverter<TimeSlotRequest, TimeSlotResponse, TimeSlot> timeSlotMapper;

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

        for (int i = 0; i < 13; i++) {
            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setStartTime(start.plusHours(i));
            timeSlot.setEndTime(start.plusHours(i + 1));
            timeSlot.setCapacity(request.getCapacity());
            timeSlot.setDate(request.getDate());
            timeSlots.add(timeSlot);
        }

        timeSlotRepository.saveAll(timeSlots);

        return timeSlots.stream()
                .map(timeSlotMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public List<TimeSlotResponse> updateAll(TimeSlotRequest request) {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        for (TimeSlot timeSlot : timeSlots) {
            timeSlot.setCapacity(request.getCapacity());
            timeSlot.setDate(request.getDate());
        }
        timeSlotRepository.saveAll(timeSlots);

        return timeSlots.stream().map(ts -> {
            TimeSlotResponse response = new TimeSlotResponse();
            response.setId(ts.getId());
            response.setStartTime(ts.getStartTime());
            response.setEndTime(ts.getEndTime());
            response.setCapacity(ts.getCapacity());
            response.setDate(ts.getDate());
            return response;
        }).toList();
    }

}
