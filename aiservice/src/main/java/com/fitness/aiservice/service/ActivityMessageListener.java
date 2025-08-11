package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAiService aiService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity(Activity activity) {
        log.info("received activity from processing {}", activity.getId());
        log.info("Generated recomandation : {}", aiService.generateRecommendation(activity));
    }
}
