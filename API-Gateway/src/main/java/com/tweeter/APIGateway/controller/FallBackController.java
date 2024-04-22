package com.tweeter.APIGateway.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @PostMapping("/userServiceFallBack")
    public String userServiceFallBack() {
        return "User Service is down";
    }
}
