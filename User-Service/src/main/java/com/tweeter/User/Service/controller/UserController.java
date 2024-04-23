package com.tweeter.User.Service.controller;

import com.tweeter.User.Service.entity.User;
import com.tweeter.User.Service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1.0/tweets/user-service")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtDecoder jwtDecoder;

    @PutMapping("/users/updateUser")
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody User user) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/users/loggedInUser")
    public ResponseEntity<User> getLoggedInUser(Authentication authentication, @RequestHeader("Authorization") String authHeader) {
        if (authentication == null || authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            String token = authHeader.substring(7); // Ensure this substring is safe to perform with proper checks
            Jwt jwt = jwtDecoder.decode(token);

            String loginId = jwt.getSubject();
            User user = userService.getUser(loginId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (JwtException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Specific exception for JWT errors
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Catch all for other exceptions
        }
    }
}
