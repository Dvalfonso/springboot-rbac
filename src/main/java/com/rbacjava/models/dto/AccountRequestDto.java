package com.rbacjava.models.dto;

import com.rbacjava.models.dao.AccountType;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDto(
        @NotNull(message = "userId is required")
        Long userId ,
        @NotNull(message = "type is required")
        AccountType type)
{}
