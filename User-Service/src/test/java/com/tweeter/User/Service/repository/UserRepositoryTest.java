package com.tweeter.User.Service.repository;

import com.tweeter.User.Service.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUserObject_whenSaved_thenUserObjectReturnSavedObject(){
        User user = User.builder()
                .Id(1L)
                .firstName("mark")
                .lastName("mark")
                .build();

        userRepository.save(user);
        Assertions.assertThat(user).isNotNull();
    }
}
