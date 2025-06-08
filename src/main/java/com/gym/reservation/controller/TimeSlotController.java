package com.gym.reservation.controller;

import com.gym.reservation.interfaces.TimeSlotService;
import com.gym.reservation.models.request.TimeSlotRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
