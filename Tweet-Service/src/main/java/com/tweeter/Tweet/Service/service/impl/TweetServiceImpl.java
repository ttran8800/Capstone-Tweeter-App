package com.tweeter.Tweet.Service.service.impl;

import com.tweeter.Tweet.Service.entity.Tweet;
import com.tweeter.Tweet.Service.external.client.AuthenticationService;
import com.tweeter.Tweet.Service.external.client.UserService;
import com.tweeter.Tweet.Service.payload.TweetWithUserHandlePayload;
import com.tweeter.Tweet.Service.repository.TweetRepository;
import com.tweeter.Tweet.Service.service.TweetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public Tweet createTweet(Tweet tweet) {
        log.info("in tweet-service right before creating tweet");
            Tweet newTweet = Tweet.builder()
                    .userId(tweet.getUserId())
                    .message(tweet.getMessage())
                    .date(tweet.getDate())
                    .build();
            log.info("in tweet-service right before saving tweet");
            tweetRepository.save(newTweet);

            return newTweet;
    }

    @Override
    public List<Tweet> getAllTweetByUserId(Long userId) {
        return tweetRepository.findAllByUserId(userId);
    }

    @Override
    public List<TweetWithUserHandlePayload> getAllTweetsWithUserHandle() {
        log.info("in tweet service impl, right before getting tweetList");
        List<Tweet> tweetList = tweetRepository.findAll();
        List<TweetWithUserHandlePayload> payload = new ArrayList<>();
        for (Tweet per : tweetList) {
            TweetWithUserHandlePayload eachPayload = new TweetWithUserHandlePayload();
            eachPayload.setTweet(per);
            log.info("right before calling userService");
            String loginId = userService.getUserLoginId(per.getUserId());
            log.info("right after userService");
            eachPayload.setLoginId(loginId);
            payload.add(eachPayload);
        }

        return payload;
    }
}
