package com.tweeter.Tweet.Service.service.impl;

import com.tweeter.Tweet.Service.entity.Tweet;
import com.tweeter.Tweet.Service.external.client.AuthenticationService;
import com.tweeter.Tweet.Service.payload.TweetRequest;
import com.tweeter.Tweet.Service.payload.TweetResponse;
import com.tweeter.Tweet.Service.repository.TweetRepository;
import com.tweeter.Tweet.Service.service.TweetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public TweetResponse createTweet(TweetRequest tweetRequest) {
        log.info("in tweet-service right before creating tweet");
        TweetResponse tweetResponse = new TweetResponse();
            Tweet newTweet = Tweet.builder()
                    .userId(tweetRequest.getUserId())
                    .message(tweetRequest.getTweetMessage())
                    .date(tweetRequest.getDate())
                    .build();
            log.info("in tweet-service right before saving tweet");
            tweetRepository.save(newTweet);
            tweetResponse.setError(false);
            tweetResponse.setMessage("Success");
            return tweetResponse;
    }
}
