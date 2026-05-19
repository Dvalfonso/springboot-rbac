package com.rbacjava.dataInitializer;

import com.rbacjava.models.dao.Role;
import com.rbacjava.models.dao.User;
import com.rbacjava.repos.RoleRepository;
import com.rbacjava.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${app.admin.email}")
    private String adminEmail;
    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setUsername("admin1");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() ->new RuntimeException("ROLE_ADMIN not found"));

            admin.getRoles().add(adminRole);

            userRepository.save(admin);
        }
    }
}
