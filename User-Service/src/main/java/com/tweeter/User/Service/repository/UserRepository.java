package com.tweeter.User.Service.repository;

import com.tweeter.User.Service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginIdOrEmail(String loginId, String email);
    Boolean existsByLoginId(String loginId);
    Boolean existsByEmail(String email);
}
