package com.fitness.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnUserProfile() throws Exception {
        // Arrange
        String userID = "user123";
        UserResponse response = new UserResponse();
        response.setId(userID);
        response.setKeycloakID("keycloak123");
        response.setEmail("user1@gmail.com");
        response.setFirstName("user1");
        response.setLastName("lastname");
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdateAt(LocalDateTime.now());

        when(userService.getUserProfile(userID)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/users/{userId}", userID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userID))
                .andExpect(jsonPath("$.email").value("user1@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("user1"))
                .andExpect(jsonPath("$.lastName").value("lastname"));
    }
}
