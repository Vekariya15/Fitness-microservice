package com.fitness.userservice.controller;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping({"/{userId}"})
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PostMapping({"/register"})
    public ResponseEntity<UserResponse> getUserProfile(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping({"/{userId}/validate"})
    public ResponseEntity<Boolean> existsByUserID(@PathVariable String userId) {
        return ResponseEntity.ok(userService.existsByKeycloakID(userId));
    }
}
