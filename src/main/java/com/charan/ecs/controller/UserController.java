package com.charan.ecs.controller;

import com.charan.ecs.dto.UserDto;
import com.charan.ecs.service.interfaces.UserServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceInterface userServiceInterface;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int userId){
        UserDto userDto = userServiceInterface.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<?> getUserById(@PathVariable("username") String username){
        UserDto userDto = userServiceInterface.getUserByUsername(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto) {
        UserDto newUserDto = userServiceInterface.addUser(userDto);
        if(Objects.nonNull(newUserDto)){
            return ResponseEntity.status(HttpStatus.CREATED).body(newUserDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Failed/UserId already exists!");
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        UserDto newUserDto = userServiceInterface.updateUser(userDto);
        if(Objects.nonNull(newUserDto)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(newUserDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Failed/UserNotFound/UsernameCantBeUpdated!");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int userId) {
        boolean isDeleted = userServiceInterface.deleteUserById(userId);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("User Deleted Successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found!");
    }
}
