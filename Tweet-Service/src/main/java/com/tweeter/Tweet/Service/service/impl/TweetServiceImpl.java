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
        log.info("tweetId: " + tweet.getTweetId());
        log.info("parentTweetId: " + tweet.getParentTweetId());
            Tweet newTweet = Tweet.builder()
                    .userId(tweet.getUserId())
                    .message(tweet.getMessage())
                    .date(tweet.getDate())
                    .build();
            if (tweet.getParentTweetId() == null) {
                log.info("10");
                newTweet.setParentTweetId(null);
                log.info("11: " + newTweet.getParentTweetId());
            } else if (tweet.getParentTweetId() == 0){
                log.info("12");
                newTweet.setParentTweetId(tweet.getTweetId());
                log.info("13: " + newTweet.getParentTweetId());
            } else {
                log.info("14");
//                newTweet.setParentTweetId(tweet.getParentTweetId());
                log.info("15: " + newTweet.getParentTweetId());
            }
            log.info("in tweet-service right before saving tweet" + newTweet);
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

    @Override
    public List<TweetWithUserHandlePayload> getAllTweetReplyByParentTweetId(Long parentTweetId) {
        log.info("in tweet service impl, right before getting tweetList 3");
        List<Tweet> tweetReplyList = tweetRepository.findAllByParentTweetId(parentTweetId);
        log.info("4: " + tweetReplyList);
        List<TweetWithUserHandlePayload> payload = new ArrayList<>();
        for (Tweet per : tweetReplyList) {
            TweetWithUserHandlePayload eachPayload = new TweetWithUserHandlePayload();
            eachPayload.setTweet(per);
            log.info("right before calling userService 5");
            String loginId = userService.getUserLoginId(per.getUserId());
            log.info("right after userService 6");
            eachPayload.setLoginId(loginId);
            payload.add(eachPayload);
        }
        return payload;
    }

    @Override
    public TweetWithUserHandlePayload getTweetByTweetId(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new RuntimeException("Tweet not found by tweetId: " + tweetId));
        String loginId = userService.getUserLoginId(tweet.getUserId());
        return new TweetWithUserHandlePayload(tweet, loginId);
    }
}
