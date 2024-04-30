package com.tweeter.Tweet.Service.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tweets")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tweetId;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false, length = 144)
    private String message;
    @Column(nullable = false)
    private Long userId;
    private Long parentTweetId;
}
