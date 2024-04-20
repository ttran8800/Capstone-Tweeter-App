package com.tweeter.User.Service.service.impl;

import com.tweeter.User.Service.entity.User;
import com.tweeter.User.Service.payload.LoginDTO;
import com.tweeter.User.Service.payload.LoginResponse;
import com.tweeter.User.Service.payload.RegisterDTO;
import com.tweeter.User.Service.payload.RegisterResponse;
import com.tweeter.User.Service.repository.RoleRepository;
import com.tweeter.User.Service.service.AuthenticationService;
import com.tweeter.User.Service.service.TokenService;
import com.tweeter.User.Service.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @Override
    public LoginResponse login(LoginDTO loginDTO) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword())
            );

            String token = tokenService.generateJwt(authentication);

            return new LoginResponse(false, "Login Success", token);
        } catch (BadCredentialsException e) {
            return new LoginResponse(true, "Authentication Error: " + e.getMessage(), "");
        }
    }

    @Override
    public RegisterResponse register(RegisterDTO registerDTO) {

        try {
            if (userService.isUserExistByLoginId(registerDTO.getLoginId())) {
                return new RegisterResponse(true, "LoginId", "Login ID already in use!", null);
            }
            if (userService.isUserExistByEmail(registerDTO.getEmail())) {
                return new RegisterResponse(true, "Email", "Email already in use", null);
            }
            User user = new User();
            user.setFirstName(registerDTO.getFirstName());
            user.setLastName(registerDTO.getLastName());
            user.setEmail(registerDTO.getEmail());
            user.setLoginId(registerDTO.getLoginId());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setContactNumber(registerDTO.getContactNumber());

            user.getRoles().add(roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role USER not found")));

            User savedUser = userService.saveUser(user);

            return new RegisterResponse(false, "none", "Registered Successfully", savedUser);
        } catch (Exception e) {
            return new RegisterResponse(true, "Exception", "Registration failed due to an internal error" + e.getMessage(), null);
        }

    }
}

