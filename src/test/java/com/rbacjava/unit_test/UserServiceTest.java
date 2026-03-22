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
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

    @Test
    void createUser_throwsWhenUsernameAlreadyExists() {
        UserRequestDto dto = new UserRequestDto("david@mail.com", "david", "password123");

        when(userRepository.existsByUsername("david")).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(dto);
        });

        verify(userRepository, never()).save(any());
    }

    @Test
    void not_found_role() {
        UserRequestDto dto = new UserRequestDto("david@mail.com", "david", "password123");
        when(userRepository.existsByUsername("david")).thenReturn(false);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class, () -> {
            userService.createUser(dto);
        });

        verify(userRepository, never()).save(any());
    }

    @Test
    void not_found_by_id() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.findById(10L);
        });
    }

    @Test
    void user_found() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(savedUser));

        UserResponseDto dto = userService.findById(10L);

        Assertions.assertSame(dto.getEmail(), savedUser.getEmail());
        Assertions.assertSame(dto.getUsername(), savedUser.getUsername());

        for (Role r : savedUser.getRoles()) {
            Assertions.assertTrue(dto.getRoles().contains(r.getName()));
        }
    }

    @Test
    void update_user_not_found_id() {
        UserRequestDto dto = new UserRequestDto(savedUser.getEmail(), savedUser.getUsername(), savedUser.getPassword());

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(10L, dto);
        });
    }

    @Test
    void updte_user_succesfully() {
        UserRequestDto dto = new UserRequestDto("newEmail@mail.com", "newUsername", "same password");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(savedUser));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDto response = userService.updateUser(1L, dto);

        Assertions.assertSame(response.getEmail(), dto.getEmail());
        Assertions.assertSame(response.getUsername(), dto.getUsername());

        verify(userRepository).save(any(User.class));
    }
}
