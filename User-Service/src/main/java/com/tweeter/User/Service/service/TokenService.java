package com.tweeter.User.Service.service;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String generateJwt(Authentication authentication);
}
