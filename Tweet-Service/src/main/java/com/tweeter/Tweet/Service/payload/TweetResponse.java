package com.tweeter.Tweet.Service.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TweetResponse {
    private boolean error;
    private String message;
}
