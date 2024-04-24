package com.tweeter.Tweet.Service.service;

import com.tweeter.Tweet.Service.entity.Tweet;

import java.util.List;

public interface TweetService {
    Tweet createTweet(Tweet tweet);
    List<Tweet> getAllTweetByUserId(Long userId);
}
