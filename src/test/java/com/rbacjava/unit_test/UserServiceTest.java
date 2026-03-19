package com.rbacjava.unit_test;

import com.rbacjava.models.dao.Role;
import com.rbacjava.models.dao.User;
import com.rbacjava.models.dto.UserRequestDto;
import com.rbacjava.models.dto.UserResponseDto;
import com.rbacjava.repos.RoleRepository;
import com.rbacjava.repos.UserRepository;
import com.rbacjava.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private PasswordEncoder passwordEncoder;

    // Inyecta los mocks en el constructor automaticamente
    @InjectMocks
    private UserService userService;

    private Role defaultRole;
    private User savedUser;

    @BeforeEach
    void setup() {
        defaultRole = new Role("ROLE_USER");
        savedUser = new User();
        savedUser.setEmail("testemail@mail.com");
        savedUser.setUsername("testname");
        savedUser.setPassword("encodedpassword");
        savedUser.getRoles().add(defaultRole);
    }

    @Test
    void succesfully_create_user() {
        UserRequestDto userRequestDto = new UserRequestDto("testemail@mail.com", "testname", "testpassword123");

        when(userRepository.existsByUsername(userRequestDto.getUsername())).thenReturn(false);
        when(passwordEncoder.encode("testpassword123")).thenReturn("encodedPassword");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(defaultRole));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDto result = userService.createUser(userRequestDto);

        Assertions.assertEquals("testemail@mail.com", result.getEmail());
        Assertions.assertEquals("testname", result.getUsername());
        Assertions.assertTrue(result.getRoles().contains("ROLE_USER"));
        verify(userRepository).save(any(User.class));
    }
}
