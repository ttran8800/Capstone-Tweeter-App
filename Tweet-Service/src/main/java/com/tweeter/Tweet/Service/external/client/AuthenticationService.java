package com.tweeter.Tweet.Service.external.client;

import com.tweeter.Tweet.Service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE/api/v1.0/tweets", configuration = FeignConfig.class)
public interface AuthenticationService {
    @PostMapping("/validateToken")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader);
}