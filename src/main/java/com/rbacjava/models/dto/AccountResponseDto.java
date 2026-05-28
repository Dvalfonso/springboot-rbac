package com.rbacjava.models.dto;

import com.rbacjava.models.dao.AccountType;

import java.time.LocalDateTime;

public record AccountResponseDto(UserResponseDto user, AccountType accountType, LocalDateTime createdAt, String cbu) {}
