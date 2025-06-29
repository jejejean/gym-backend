package com.gym.reservation.controller;

import com.gym.reservation.interfaces.ReserveService;
import com.gym.reservation.models.request.ReserveRequest;
import com.gym.reservation.models.request.ReserveSimpleRequest;
import com.gym.reservation.models.response.ReserveResponse;
import com.gym.shared.interfaces.CrudInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reserve")
public class ReserveController {

    private final CrudInterface<ReserveRequest, ReserveResponse> reserveServiceCrud;
    private final ReserveService reserveService;

    @GetMapping()
    private ResponseEntity<Object> findAll(){
        return new ResponseEntity<>(reserveServiceCrud.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    private ResponseEntity<Object> findAllByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(reserveService.findAllByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> findById(@PathVariable Long id){
        return new ResponseEntity<>(reserveServiceCrud.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ReserveRequest reserveRequest) {
        return new ResponseEntity<>(reserveServiceCrud.create(reserveRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ReserveRequest reserveRequest) {
        return new ResponseEntity<>(reserveServiceCrud.update(id, reserveRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return new ResponseEntity<>(reserveServiceCrud.delete(id),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/dates/{id}")
    public ResponseEntity<Object> getAllDatesWithReservationsByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(reserveService.getAllDatesWithReservationsByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/by-date/{reservationDate}")
    public ResponseEntity<Object> findAllByReservationDate(@PathVariable String reservationDate) {
        return new ResponseEntity<>(reserveService.findAllByReservationDate(LocalDate.parse(reservationDate)), HttpStatus.OK);
    }

    @PutMapping("update/attendance/{id}")
    public ResponseEntity<Object> getAttendanceByUserId(@PathVariable Long id, @RequestBody ReserveSimpleRequest reserveRequest) {
        return new ResponseEntity<>(reserveService.getAttendanceByUserId(id, reserveRequest), HttpStatus.OK);
    }

    @GetMapping("/resendReservationReminder/{reserveId}")
    public ResponseEntity<Object> resendReservationReminder(@PathVariable Long reserveId) {
        reserveService.resendReservationReminder(reserveId);
        return ResponseEntity.ok("Recordatorio enviado correctamente.");
    }
}
