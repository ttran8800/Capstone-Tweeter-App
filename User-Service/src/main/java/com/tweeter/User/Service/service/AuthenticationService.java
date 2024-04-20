package com.tweeter.User.Service.service;

import com.tweeter.User.Service.payload.LoginDTO;
import com.tweeter.User.Service.payload.LoginResponse;
import com.tweeter.User.Service.payload.RegisterDTO;
import com.tweeter.User.Service.payload.RegisterResponse;

public interface AuthenticationService {
    LoginResponse login(LoginDTO loginDTO);
    RegisterResponse register(RegisterDTO registerDTO);
}
