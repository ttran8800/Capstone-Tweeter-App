package com.tweeter.Tweet.Service.controller;

import com.tweeter.Tweet.Service.entity.Tweet;
import com.tweeter.Tweet.Service.external.client.AuthenticationService;
import com.tweeter.Tweet.Service.service.TweetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1.0/tweets/tweet-service")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/{username}/addTweet")
    public ResponseEntity<?> createTweet(@RequestBody Tweet tweet, @RequestHeader("Authorization") String authHeader) {
        log.info("Testing for authHeader: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            log.info("right before authenticationService");
            ResponseEntity<String> response = authenticationService.validateToken(authHeader);
            log.info("In post mapping create tweet controller after validateToken call, response: " + response.getBody());
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("In post mapping create tweet controller, right before tweet service call");
                Tweet newTweet = tweetService.createTweet(tweet);
                log.info("In post mapping create tweet controller, right after creating tweet");
                return new ResponseEntity<>(newTweet, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{username}/all")
    public ResponseEntity<List<Tweet>> getAllTweetsByUserID(@PathVariable("username") Long userId, @RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            ResponseEntity<String> response = authenticationService.validateToken(authHeader);
            if (response.getStatusCode() == HttpStatus.OK) {
                List<Tweet> tweetList = tweetService.getAllTweetByUserId(userId);
                return new ResponseEntity<>(tweetList, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
