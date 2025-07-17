package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepo repo;

    public List<Recommendation> getUserRecommendation(String userId) {
        return repo.findByUserId(userId);

    }

    public Optional<Recommendation> getActivityRecommendation(String activityId) {
        return repo.findByActivityId(activityId);
    }
}
