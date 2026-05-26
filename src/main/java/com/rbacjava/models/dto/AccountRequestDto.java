package com.rbacjava.models.dto;

import com.rbacjava.models.dao.AccountType;
import com.rbacjava.models.dao.User;

public record AccountRequestDto(User user , AccountType type){}
