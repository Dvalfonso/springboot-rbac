package com.rbacjava.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserRequestDto {
    private String email;
    private String username;
    private String password;
}
