//package com.fitness.userservice.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//public class UserControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @Sql(scripts = "/sql/init-user.sql")
//    void shouldGetUserFromDatabase() throws Exception {
//        mockMvc.perform(get("/users/user123"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("user1@gmail.com"))
//                .andExpect(jsonPath("$.firstName").value("user1"));
//    }
//}
