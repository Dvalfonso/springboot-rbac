package com.rbacjava.controllers;

import com.rbacjava.auth.jwt.JwtService;
import com.rbacjava.models.dto.AuthResponse;
import com.rbacjava.models.dto.LoginRequestDto;
import com.rbacjava.models.dto.RegisterRequestDto;
import com.rbacjava.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequestDto loginRequestDto) {
        // Map se serializa como { "token" : "asdfasdfasdf",}
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password())
        );

        String token = jwtService.generateToken((UserDetails) auth.getPrincipal());
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequestDto dto) {
        return authService.register(dto);
    }
}
