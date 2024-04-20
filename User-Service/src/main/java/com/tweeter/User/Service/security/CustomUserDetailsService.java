package com.tweeter.User.Service.security;

import com.tweeter.User.Service.entity.User;
import com.tweeter.User.Service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLoginIdOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login ID or email: " + username));
        return new CustomUserDetails(user);
    }
}