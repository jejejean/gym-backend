package com.gym.reservation.interfaces;

import com.gym.reservation.models.request.TimeSlotRequest;
import com.gym.reservation.models.response.TimeSlotResponse;

import java.util.List;

public interface TimeSlotService {
    List<TimeSlotResponse> updateAll(TimeSlotRequest request);

    List<TimeSlotResponse> getAllTimeSlots();

    List<TimeSlotResponse> createAll(TimeSlotRequest request);
}
