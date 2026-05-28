package com.rbacjava.controllers;

import com.rbacjava.models.dto.AccountRequestDto;
import com.rbacjava.models.dto.AccountResponseDto;
import com.rbacjava.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/accounts")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDto> createUser(@RequestBody AccountRequestDto accountRequestDto) {
        AccountResponseDto accountResponseDto = accountService.createAccount(accountRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable Long id) {
        System.out.println("LLega al controller con id ");
        System.out.print(id);
        AccountResponseDto accountResponseDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountResponseDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountResponseDto>> getAccounts() {
        List<AccountResponseDto> accountResponseDtoList = accountService.getAccounts();
        return ResponseEntity.ok(accountResponseDtoList);
    }
}
