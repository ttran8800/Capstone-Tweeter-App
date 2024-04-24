package com.tweeter.Tweet.Service.external.client;

import com.tweeter.Tweet.Service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE/api/v1.0/tweets/user-service", configuration = FeignConfig.class)
public interface UserService {
    @PostMapping("/users/getUserById")
    public String getUserLoginId(@RequestBody Long userId);
}