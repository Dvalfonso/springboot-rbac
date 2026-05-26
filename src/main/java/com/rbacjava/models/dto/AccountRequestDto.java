package com.rbacjava.models.dto;

import com.rbacjava.models.dao.AccountType;
import com.rbacjava.models.dao.User;
import org.antlr.v4.runtime.misc.NotNull;

public record AccountRequestDto(
        Long userId ,
        AccountType type)
{}
