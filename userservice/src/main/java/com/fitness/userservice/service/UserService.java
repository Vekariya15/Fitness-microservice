package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse getUserProfile(String userId) {
        return userRepository.findById(userId)
                .map(this::toUserResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponse register(RegisterRequest request) {
        return userRepository.existsByEmail(request.getEmail())
                ? toUserResponse(userRepository.findByEmail(request.getEmail()))
                : toUserResponse(userRepository.save(toUser(request)));
    }

    public Boolean existsByKeycloakID(String keycloakID) {
        return userRepository.existsByKeycloakID(keycloakID);
    }

    private User toUser(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setKeycloakID(request.getKeycloakID());
        return user;
    }

    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setEmail(user.getEmail());
        response.setId(user.getId());
        response.setKeycloakID(user.getKeycloakID());
        response.setPassword(user.getPassword());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdateAt(user.getUpdateAt());
        return response;
    }
}
