package com.fitness.aiservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "recommendations")
@Builder
public class Recommendation {
    @Id
    private String Id;
    private String activityId;
    private String userId;
    private String activityType;
    private String recommendation;
    private List<String> improvements;
    private List<String> suggestion;
    private List<String> safety;
    @CreatedDate
    private LocalDateTime createdAt;

}
