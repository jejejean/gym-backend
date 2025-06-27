package com.gym.reservation.interfaces;

import com.gym.reservation.models.request.TimeSlotRequest;
import com.gym.reservation.models.response.MachineResponse;
import com.gym.reservation.models.response.TimeSlotResponse;
import com.gym.reservation.models.response.TimeSlotSummaryResponse;

import java.util.Date;
import java.util.List;

public interface TimeSlotService {
    List<TimeSlotResponse> updateAll(TimeSlotRequest request);
    List<TimeSlotResponse> getAllTimeSlots();
    List<TimeSlotResponse> createAll(TimeSlotRequest request);
    List<MachineResponse> getMachineByDate(Date date);
    List<TimeSlotSummaryResponse> getTimeSlotSummaryByDate(Date date);
}
