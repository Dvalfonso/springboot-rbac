package com.rbacjava.controllers;

import com.rbacjava.models.dto.AccountRequestDto;
import com.rbacjava.models.dto.AccountResponseDto;
import com.rbacjava.models.dto.UserRequestDto;
import com.rbacjava.models.dto.UserResponseDto;
import com.rbacjava.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/account")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    public ResponseEntity<AccountResponseDto> createUser(@RequestBody AccountRequestDto accountRequestDto) {
        AccountResponseDto accountResponseDto = accountService.createAccount(accountRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountResponseDto);
    }
}
