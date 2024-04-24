package com.tweeter.User.Service.Loader;


import com.tweeter.User.Service.entity.Role;
import com.tweeter.User.Service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        try {

            Role role = roleRepository.findByName("USER").orElseGet(() -> {
                Role newRole = new Role();
                newRole.setName("USER");
                return newRole;
            });

            roleRepository.save(role);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
