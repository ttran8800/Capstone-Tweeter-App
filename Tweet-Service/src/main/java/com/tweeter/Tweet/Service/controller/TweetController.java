package com.tweeter.Tweet.Service.controller;

import com.tweeter.Tweet.Service.external.client.AuthenticationService;
import com.tweeter.Tweet.Service.payload.TweetRequest;
import com.tweeter.Tweet.Service.payload.TweetResponse;
import com.tweeter.Tweet.Service.service.TweetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/v1.0/tweets/tweet-service")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/{username}/addTweet")
    public ResponseEntity<?> createTweet(@RequestBody TweetRequest tweetRequest, @RequestHeader("Authorization") String authHeader) {
        log.info("Testing for authHeader: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            log.info("right before authenticationService");
            ResponseEntity<String> response = authenticationService.validateToken(authHeader);
            log.info("In post mapping create tweet controller after validateToken call, response: " + response.getBody());
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("In post mapping create tweet controller, right before tweet service call");
                TweetResponse tweetResponse = tweetService.createTweet(tweetRequest);
                log.info("In post mapping create tweet controller, right after creating tweet");
                return new ResponseEntity<>(tweetResponse.getMessage(), HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
