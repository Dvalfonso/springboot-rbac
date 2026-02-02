package com.rbacjava.services;

import com.rbacjava.auth.jwt.JwtService;
import com.rbacjava.models.dao.Role;
import com.rbacjava.models.dao.User;
import com.rbacjava.models.dto.AuthResponse;
import com.rbacjava.models.dto.RegisterRequestDto;
import com.rbacjava.repos.RoleRepository;
import com.rbacjava.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepo,
                       RoleRepository roleRepo,
                       PasswordEncoder encoder,
                       JwtService jwtService) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequestDto dto) {

        if (userRepo.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already exists");
        }

        Role userRole = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        User user = new User();
        user.setEmail(dto.email());
        user.setUsername(dto.username());
        user.setPassword(encoder.encode(dto.password()));
        user.getRoles().add(userRole);

        userRepo.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
