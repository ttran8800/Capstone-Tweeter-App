package com.tweeter.User.Service.service.impl;

import com.tweeter.User.Service.entity.Role;
import com.tweeter.User.Service.entity.User;
import com.tweeter.User.Service.repository.RoleRepository;
import com.tweeter.User.Service.repository.UserRepository;
import com.tweeter.User.Service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public boolean isUserExistByLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role \"USER\" not found"));
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User updateUser = userRepository.findByLoginIdOrEmail(user.getLoginId(), user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setContactNumber(user.getContactNumber());
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(updateUser);
        return updateUser;
    }
}
