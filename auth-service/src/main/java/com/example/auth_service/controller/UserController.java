package com.example.auth_service.controller;

import com.example.auth_service.dto.UserCreatedEvent;
import com.example.auth_service.service.UserService;
import com.example.auth_service.user.User;
import com.example.auth_service.service.UserEventProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class UserController {
    private final UserService userService;
    private final UserEventProducer userEventProducer;

    public UserController(UserService userService, UserEventProducer userEventProducer) {
        this.userEventProducer = userEventProducer;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        try {
            String token = userService.loginUser(user);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try {
            User savedUser = userService.registerUser(user);

            System.out.println("Sending UserCreatedEvent for user: " + savedUser.getUsername() + ", id: " + savedUser.getId());

            userEventProducer.sendUserCreated(
                    new UserCreatedEvent(savedUser.getId(), savedUser.getUsername())
            );

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try{
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
