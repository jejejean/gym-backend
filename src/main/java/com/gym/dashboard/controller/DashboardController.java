package com.gym.dashboard.controller;

import com.gym.dashboard.interfaces.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/totalReserves")
    private ResponseEntity<Object> getTotalReserves(){
        return new ResponseEntity<>(dashboardService.getTotalReserves(), HttpStatus.OK);
    }

    @GetMapping("/attendanceSummary")
    private ResponseEntity<Object> getTotalAttended(){
        return new ResponseEntity<>(dashboardService.getAttendanceSummary(), HttpStatus.OK);
    }

    @GetMapping("/reservesByMonth")
    public ResponseEntity<Object> getReservesByMonth() {
        return new ResponseEntity<>(dashboardService.getReservesByMonth(), HttpStatus.OK);
    }

    @GetMapping("/reservesByTipeMachine")
    public ResponseEntity<Object> getReservesByTipeMachine() {
        return new ResponseEntity<>(dashboardService.getReservesByTipeMachine(), HttpStatus.OK);
    }
    @GetMapping("/reservesByMachine")
    public ResponseEntity<Object> getReservesByMachine() {
        return new ResponseEntity<>(dashboardService.getReservesByMachine(), HttpStatus.OK);
    }

}
