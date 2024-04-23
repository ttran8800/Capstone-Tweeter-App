package com.tweeter.Tweet.Service.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TweetRequest {
    private Long userId;
    private String tweetMessage;
    private Date date;
    private String token;
}