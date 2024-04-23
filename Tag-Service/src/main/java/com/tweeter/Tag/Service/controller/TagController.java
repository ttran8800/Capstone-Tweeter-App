package com.tweeter.Tag.Service.controller;

import com.tweeter.Tag.Service.entity.Tag;
import com.tweeter.Tag.Service.external.client.AuthenticationService;
import com.tweeter.Tag.Service.service.TagService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/tweets")
public class TagController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TagService tagService;

    @PostMapping("/{tweetId}/addTag")
    public ResponseEntity<?> createTag (@RequestBody Tag tag, @RequestHeader("Authorization") String authHeader) {
        log.info("Testing for authHeader: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            log.info("right before authenticationService");
            ResponseEntity<String> response = authenticationService.validateToken(authHeader);
            log.info("In post mapping create tweet controller after validateToken call, response: " + response.getBody());
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("In post mapping create tweet controller, right before tweet service call");
                Tag newTag = tagService.createTag(tag);
                log.info("In post mapping create tweet controller, right after creating tweet");
                return new ResponseEntity<>(newTag, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
