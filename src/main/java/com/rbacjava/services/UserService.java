package com.rbacjava.services;

import com.rbacjava.models.dao.Role;
import com.rbacjava.models.dao.User;
import com.rbacjava.models.dto.UserRequestDto;
import com.rbacjava.models.dto.UserResponseDto;
import com.rbacjava.repos.RoleRepository;
import com.rbacjava.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserResponseDto createUser(UserRequestDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        user.getRoles().add(userRole);

        User saved = userRepository.save(user);

        return toResponseDto(saved);
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(this::toResponseDto).toList();
    }

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with that id"));
        return toResponseDto(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with that id"));

        user.setEmail(userRequestDto.getEmail());
        user.setUsername(userRequestDto.getUsername());

        User updatedUser = userRepository.save(user);
        return toResponseDto(updatedUser);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())
        );
    }
}
