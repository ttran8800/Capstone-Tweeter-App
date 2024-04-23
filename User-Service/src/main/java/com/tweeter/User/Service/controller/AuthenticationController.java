package com.tweeter.User.Service.controller;

import com.tweeter.User.Service.payload.LoginDTO;
import com.tweeter.User.Service.payload.LoginResponse;
import com.tweeter.User.Service.payload.RegisterDTO;
import com.tweeter.User.Service.payload.RegisterResponse;
import com.tweeter.User.Service.service.AuthenticationService;
import com.tweeter.User.Service.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@RequestMapping("/api/v1.0/tweets/user-service")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtDecoder jwtDecoder;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> login(@RequestBody RegisterDTO registerDTO) {
        try {
            RegisterResponse response = authenticationService.register(registerDTO);
            HttpStatus status = HttpStatus.CREATED; // Default successful status

            switch (response.getErrorType()) {
                case "LoginId":
                case "Email":
                    status = HttpStatus.CONFLICT;
                    break;
                case "Exception":
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                    break;
            }

            return new ResponseEntity<>(response, status);
        } catch (RuntimeException ex) {
            log.info("Registration error: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(new RegisterResponse(true, "Exception", "Error during registration: " + ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
        try {
            LoginResponse response = authenticationService.login(loginDTO);
            if (response.isError()) {
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.info("Login error: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponse(true, "Error during login: " + ex.getMessage(), null));
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        log.info("in authentication controller. authHeader: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Jwt jwt = jwtDecoder.decode(token); // Validate the token

                String loginId = jwt.getSubject();
                log.info("in authentication controller, validatetoken. Token decoded and loginId: " + loginId);
                if (userService.isUserExistByLoginId(loginId)) {
                    log.info("user exist: should reuturn responseentity OK");
                    return ResponseEntity.ok("Token is valid");
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authorized to create tweet");
                }
            } catch (JwtException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
            }
        }
        return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
