package com.rbacjava.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;
}
