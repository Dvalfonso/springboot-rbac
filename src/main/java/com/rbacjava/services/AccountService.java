package com.rbacjava.services;

import com.rbacjava.models.dao.Account;
import com.rbacjava.models.dao.AccountType;
import com.rbacjava.models.dto.AccountRequestDto;
import com.rbacjava.models.dto.AccountResponseDto;
import com.rbacjava.models.dto.UserResponseDto;
import com.rbacjava.repos.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {
        Long userId       = accountRequestDto.user().getId();
        AccountType type  = accountRequestDto.type();

        try {
            Account saveAccount = new Account(accountRequestDto.user(), accountRequestDto.type());
            Account newAccount = accountRepository.save(saveAccount);
            UserResponseDto userResponseDto = new UserResponseDto(accountRequestDto.user());
            return new AccountResponseDto(userResponseDto, newAccount.getType(), newAccount.getCreatedAt());

        } catch (RuntimeException e) {
            throw new RuntimeException("Cant create the account.");
        }
    }
}
