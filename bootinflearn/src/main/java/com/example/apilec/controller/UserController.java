package com.example.apilec.controller;

import com.example.apilec.domain.User;
import com.example.apilec.exception.UserNotFoundException;
import com.example.apilec.service.UserService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    //의존성 주입 방법중 하나
    public UserController(UserService service) {
        this.userService=service;
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.findAll();
    }
    @GetMapping("/users/{id}")
    public User findUser(@PathVariable int id) {
        User user = userService.findOne(id);
        if(user==null) {
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
            User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userService.deleteById(id);

        if(user==null) {
            throw new UserNotFoundException(String.format("ID[%s]not found",id));
        }
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        User updateUser = userService.updateById(id,user);
        if(user==null) {
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(updateUser.getId())
                .toUri();
        return ResponseEntity.accepted().build();
    }

}
