package com.gym.reservation.models.request;

import com.gym.shared.interfaces.IHandleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReserveRequest implements IHandleRequest {
    private Long id;
    private Long userId;
    private List<Long> timeSlotId;
    private LocalDate reservationDate;
    private AttendanceRequest attendanceRequest;
    private List<MachineRequest> machineRequest;
    private Map<String, Map<String, Integer>> capacityInfo;

}
