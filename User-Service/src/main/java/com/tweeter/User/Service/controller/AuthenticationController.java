package com.tweeter.User.Service.controller;

import com.tweeter.User.Service.payload.RegisterDTO;
import com.tweeter.User.Service.payload.LoginDTO;
import com.tweeter.User.Service.payload.RegisterResponse;
import com.tweeter.User.Service.payload.LoginResponse;
import com.tweeter.User.Service.service.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/tweets")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

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
            return ResponseEntity.badRequest().body(new RegisterResponse(true, "Exception", "Error during registration: " + ex.getMessage(), null));
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
}
