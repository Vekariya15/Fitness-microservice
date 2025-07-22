package com.loadBalancer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@RestController
public class MessageController {

    @Autowired
    private RestTemplate restTemplate;

    private final WebClient userServiceWebClient;


    @GetMapping("/get-message")
    public String getMessage() {

        String serviceAUrl = "http://client-service/message";

        return restTemplate.getForObject(serviceAUrl, String.class);

    }
}
