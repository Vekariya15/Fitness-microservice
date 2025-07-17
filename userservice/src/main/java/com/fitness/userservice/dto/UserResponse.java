package com.fitness.userservice.dto;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
public class UserResponse {

    private String id;
    private String email;

    private String password;
    private String firstName;
    private String lastName;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;
}
