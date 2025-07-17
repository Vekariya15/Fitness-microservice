package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.Activityservice;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
@AllArgsConstructor
public class ActivityController {
    private Activityservice activityservice;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request) {
        return ResponseEntity.ok(activityservice.trackActivity(request));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivites(@RequestHeader("X-User-ID") String userID) {
        return ResponseEntity.ok(activityservice.getUserActivites(userID));
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivitesById(@PathVariable String activityId) {
        return ResponseEntity.ok(activityservice.getActivitesById(activityId));
    }
}
