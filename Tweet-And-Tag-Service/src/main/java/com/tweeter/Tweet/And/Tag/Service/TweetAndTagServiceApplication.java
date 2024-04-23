package com.tweeter.Tweet.And.Tag.Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TweetAndTagServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetAndTagServiceApplication.class, args);
	}

}
