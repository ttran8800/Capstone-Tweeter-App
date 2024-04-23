package com.tweeter.Tweet.Service.service;

import com.tweeter.Tweet.Service.entity.Tweet;
import com.tweeter.Tweet.Service.payload.TweetRequest;
import com.tweeter.Tweet.Service.payload.TweetResponse;

public interface TweetService {
    TweetResponse createTweet(TweetRequest tweetRequest);
}
