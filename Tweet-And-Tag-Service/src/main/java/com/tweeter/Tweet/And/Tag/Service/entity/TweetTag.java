package com.tweeter.Tweet.And.Tag.Service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tweets_tags", uniqueConstraints = {@UniqueConstraint(columnNames = {"tweetId", "tagId"})})
public class TweetTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tweetTagId;

    @Column(nullable = false)
    private Long tweetId;

    @Column(nullable = false)
    private Long tagId;
}
