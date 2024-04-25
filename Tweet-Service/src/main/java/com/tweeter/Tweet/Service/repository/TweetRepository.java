package com.tweeter.Tweet.Service.repository;

import com.tweeter.Tweet.Service.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findAllByUserId(Long userId);
    List<Tweet> findAllByParentTweetId(Long parentTweetId);
}