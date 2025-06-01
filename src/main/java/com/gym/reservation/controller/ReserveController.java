package com.gym.reservation.controller;

import com.gym.reservation.interfaces.ReserveService;
import com.gym.reservation.models.request.ReserveRequest;
import com.gym.reservation.models.response.ReserveResponse;
import com.gym.shared.interfaces.CrudInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
