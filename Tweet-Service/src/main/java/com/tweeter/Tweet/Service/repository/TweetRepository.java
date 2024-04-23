package com.tweeter.Tweet.Service.repository;

import com.tweeter.Tweet.Service.entity.Tweet;
import com.tweeter.Tweet.Service.payload.TweetRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
