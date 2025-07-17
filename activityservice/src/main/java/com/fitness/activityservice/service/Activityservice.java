package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Activityservice {
    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;

    public ActivityResponse trackActivity(ActivityRequest request) {
        boolean isvalidUser = userValidationService.validateUser(request.getUserID());
        if (!isvalidUser) {
            throw new RuntimeException("Invalid user : " + request.getUserID());
        }
        Activity activity = new Activity();
        activity.setUserID(request.getUserID());
        activity.setType(request.getType());
        activity.setDuration(request.getDuration());
        activity.setCaloriesBurned(request.getCaloriesBurned());
        activity.setStartTime(request.getStartTime());
        activity.setAddtionalMAtrics(request.getAddtionalMAtrics());
        Activity savedAcivity = activityRepository.save(activity);
        return mapToResponse(savedAcivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserID(activity.getUserID());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAddtionalMAtrics(activity.getAddtionalMAtrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }

    public List<ActivityResponse> getUserActivites(String userId) {
        List<Activity> activities = activityRepository.findByUserID(userId);
        return activities.stream().map(this::mapToResponse).toList();
    }

    public ActivityResponse getActivitesById(String activityId) {
        return activityRepository.findById(activityId).map(this::mapToResponse).orElseThrow(() -> new RuntimeException("Activity not found on id : " + activityId));
    }


}
