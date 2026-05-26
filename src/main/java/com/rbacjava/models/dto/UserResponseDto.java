package com.rbacjava.models.dto;

import com.rbacjava.models.dao.Role;
import com.rbacjava.models.dao.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();

        roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
        }
    }
}