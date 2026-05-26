package com.rbacjava.services;

import com.rbacjava.Exceptions.UserNotFoundException;
import com.rbacjava.models.dao.Account;
import com.rbacjava.models.dao.AccountType;
import com.rbacjava.models.dao.User;
import com.rbacjava.models.dto.AccountRequestDto;
import com.rbacjava.models.dto.AccountResponseDto;
import com.rbacjava.models.dto.UserResponseDto;
import com.rbacjava.repos.AccountRepository;
import com.rbacjava.repos.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {
        User user = userRepository.findById(accountRequestDto.userId())
                .orElseThrow(() ->
                        new UserNotFoundException(accountRequestDto.userId()));

        Account account = new Account(user, accountRequestDto.type());
        Account savedAccount = accountRepository.save(account);
        return new AccountResponseDto(new UserResponseDto(user), accountRequestDto.type(), savedAccount.getCreatedAt());
    }
}
