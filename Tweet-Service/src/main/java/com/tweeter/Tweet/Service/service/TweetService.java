package com.tweeter.Tweet.Service.service;

import com.tweeter.Tweet.Service.entity.Tweet;
import com.tweeter.Tweet.Service.payload.TweetWithUserHandlePayload;

import java.util.List;

public interface TweetService {
    Tweet createTweet(Tweet tweet);
    List<Tweet> getAllTweetByUserId(Long userId);
    List<TweetWithUserHandlePayload> getAllTweetsWithUserHandle();
    List<TweetWithUserHandlePayload> getAllTweetReplyByParentTweetId(Long parentTweetId);

    TweetWithUserHandlePayload getTweetByTweetId(Long tweetId);

}
