package com.gym.user.controller;

import com.gym.shared.interfaces.CrudInterface;
import com.gym.user.interfaces.UserService;
import com.gym.user.models.request.UserRequest;
import com.gym.user.models.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final CrudInterface<UserRequest, UserResponse> userServiceCrud;
    private final UserService userService;
    @GetMapping
    private ResponseEntity<Object> findAll(){
        return new ResponseEntity<>(userServiceCrud.findAll(), HttpStatus.OK);
    }

    @GetMapping("/userType")
    private ResponseEntity<Object> findById(){
        return new ResponseEntity<>(userService.findAllByUserType(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> findById(@PathVariable Long id){
        return new ResponseEntity<>(userServiceCrud.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userServiceCrud.create(userRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userServiceCrud.update(id, userRequest), HttpStatus.OK);
    }

    @PutMapping("/updateUserPlan/{id}")
    public ResponseEntity<Object> updateUserPlan(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.updateUserPlan(id, userRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return new ResponseEntity<>(userServiceCrud.delete(id),HttpStatus.NO_CONTENT);
    }
}
