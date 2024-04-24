package com.tweeter.Tweet.Service.payload;

import com.tweeter.Tweet.Service.entity.Tweet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TweetWithUserHandlePayload {
    private Tweet tweet;
    private String loginId;
}
