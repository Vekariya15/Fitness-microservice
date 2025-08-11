package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;


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
        activity.setAdditionalMetrics(request.getAdditionalMetrics());
        Activity savedAcivity = activityRepository.save(activity);

        // publish to RabbitMQ for AI processing
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, savedAcivity);
        } catch (Exception e) {
            log.error("Failed to publish  activity to rabbitMQ : " + e);
            throw new RuntimeException(e);
        }
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
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
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
