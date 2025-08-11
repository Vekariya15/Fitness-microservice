package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.ActivityType;
import com.fitness.activityservice.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ActivityControllerTest {

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testTrackActivity() {
        // Arrange
        ActivityRequest request = new ActivityRequest();
        request.setUserID("user123");
        request.setType(ActivityType.RUNNING);
        request.setDuration(30);
        request.setCaloriesBurned(250);
        request.setStartTime(LocalDateTime.of(2025, 7, 28, 10, 0));
        request.setAdditionalMetrics(new HashMap<>());

        ActivityResponse response = new ActivityResponse();
        response.setId("activity001");
        response.setUserID("user123");
        response.setType(ActivityType.RUNNING);
        response.setDuration(30);
        response.setCaloriesBurned(250);
        response.setStartTime(request.getStartTime());
        response.setAdditionalMetrics(request.getAdditionalMetrics());
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        when(activityService.trackActivity(request)).thenReturn(response);

        // Act
        ResponseEntity<ActivityResponse> result = activityController.trackActivity(request);

        // Assert
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(activityService, times(1)).trackActivity(request);
    }

    @Test
    void testGetActivitesById() {
        String userId = "user123";

        ActivityResponse response = new ActivityResponse();

        response.setId("68788ac3be281064710aa896");
        response.setUserID("user123");
        response.setType(ActivityType.RUNNING);
        response.setDuration(45);
        response.setCaloriesBurned(350);
        response.setStartTime(LocalDateTime.of(2025, 7, 17, 8, 30, 0));
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("heartRate", 120);
        metrics.put("steps", 6000);
        metrics.put("location", "Central Park");
        response.setAdditionalMetrics(metrics);  // Keep using the same misspelled method if it's in your DTO
        response.setCreatedAt(LocalDateTime.of(2025, 7, 17, 11, 1, 47, 500_000_000));  // .5 seconds
        response.setUpdatedAt(LocalDateTime.of(2025, 7, 17, 11, 1, 47, 500_000_000));

        when(activityService.getActivitesById(userId)).thenReturn(response);

        ResponseEntity<ActivityResponse> result = activityController.getActivitesById(userId);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(activityService, times(1)).getActivitesById(userId);
    }

//    private List<ActivityResponse> generateMockActivities(int count, String userId) {
//        List<ActivityResponse> responses = new ArrayList<>();
//
//        for (int i = 0; i < count; i++) {
//            Map<String, Object> metrics = new HashMap<>();
//            metrics.put("heartRate", 100 + (i % 40));
//            metrics.put("steps", 5000 + (i * 10));
//            metrics.put("location", "Central Park");
//
//            ActivityResponse response = ActivityResponse.builder()
//                    .id("activity" + i)
//                    .userID(userId)
//                    .type(ActivityType.RUNNING)
//                    .duration(30 + (i % 60))
//                    .caloriesBurned(300 + (i % 100))
//                    .startTime(LocalDateTime.of(2025, 7, 17, 6, 0).plusMinutes(i))
//                    .addtionalMAtrics(metrics)
//                    .createdAt(LocalDateTime.now())
//                    .updatedAt(LocalDateTime.now())
//                    .build();
//
//            responses.add(response);
//        }
//
//        return responses;
//    }


//    @Test
//    void testGetUserActivities_withLargeDataSet() {
//        // Arrange
//        String userId = "user123";
//        List<ActivityResponse> mockResponses = generateMockActivities(1000, userId);
//        when(activityService.getUserActivites(userId)).thenReturn(mockResponses);
//
//        // Act
//        ResponseEntity<List<ActivityResponse>> result = activityController.getUserActivites(userId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertNotNull(result.getBody());
//        assertEquals(1000, result.getBody().size());
//        verify(activityService, times(1)).getUserActivites(userId);
//    }

}
