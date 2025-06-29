package com.gym.dashboard.service;

import com.gym.dashboard.interfaces.DashboardService;
import com.gym.reservation.repository.AttendanceRepository;
import com.gym.reservation.repository.ReserveRepository;
import com.gym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ReserveRepository reserveRepository;
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    @Override
    public Long getTotalReserves() {
        return reserveRepository.countAllReserves();
    }

    @Override
    public Map<String, Integer> getAttendanceSummary() {
        int asistencia = attendanceRepository.countByAttended(true);
        int inasistencia = attendanceRepository.countByAttended(false);
        Map<String, Integer> result = new HashMap<>();
        result.put("asistencia", asistencia);
        result.put("inasistencia", inasistencia);
        return result;
    }

    @Override
    public Map<String, Integer> getReservesByMonth() {
        List<Object[]> results = reserveRepository.countReservesByMonth();
        Map<String, Integer> response = new HashMap<>();
        String[] meses = new DateFormatSymbols().getMonths();

        for (Object[] row : results) {
            int mes = ((Number) row[0]).intValue();
            int total = ((Number) row[1]).intValue();
            String nombreMes = meses[mes - 1].toLowerCase();
            response.put(nombreMes, total);
        }
        return response;
    }

    @Override
    public Map<String, Integer> getReservesByTipeMachine() {
        List<Object[]> results = reserveRepository.countReservesByTipeMachine();
        Map<String, Integer> response = new HashMap<>();
        for (Object[] row : results) {
            String tipeName = (String) row[0];
            Integer total = ((Number) row[1]).intValue();
            response.put(tipeName, total);
        }
        return response;
    }

    @Override
    public Map<String, Integer> getReservesByMachine() {
        List<Object[]> results = reserveRepository.countReservesByMachine();
        Map<String, Integer> response = new HashMap<>();
        for (Object[] row : results) {
            String machineName = (String) row[0];
            Integer total = ((Number) row[1]).intValue();
            response.put(machineName, total);
        }
        return response;
    }

    @Override
    public int getTotalUsers() {
        return userRepository.countByUserType();
    }
}
