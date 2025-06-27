package com.gym.reservation.controller;

import com.gym.reservation.interfaces.TimeSlotService;
import com.gym.reservation.models.request.TimeSlotRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/timeSlot")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(timeSlotService.getAllTimeSlots(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateAll(@RequestBody TimeSlotRequest timeSlotRequest) {
        return new ResponseEntity<>(timeSlotService.updateAll(timeSlotRequest), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody TimeSlotRequest timeSlotRequest) {
        return new ResponseEntity<>(timeSlotService.createAll(timeSlotRequest), HttpStatus.CREATED);
    }

    @GetMapping("/machines/by-date")
    public ResponseEntity<Object> getMachinesByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return new ResponseEntity<>(timeSlotService.getMachineByDate(date), HttpStatus.OK);
    }

    @GetMapping("/timeSlots/by-date")
    public ResponseEntity<Object> getTimeSlotSummaryByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return new ResponseEntity<>(timeSlotService.getTimeSlotSummaryByDate(date), HttpStatus.OK);
    }
}
