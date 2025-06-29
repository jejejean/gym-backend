package com.gym.dashboard.interfaces;

import java.util.Map;

public interface DashboardService {
    Long getTotalReserves();
    int getTotalUsers();
    Map<String, Integer> getAttendanceSummary();
    Map<String, Integer> getReservesByMonth();
    Map<String, Integer> getReservesByTipeMachine();
    Map<String, Integer> getReservesByMachine();
}
