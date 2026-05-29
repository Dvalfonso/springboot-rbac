package com.rbacjava.services;

import com.rbacjava.Exceptions.AccountNotFoundException;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {
        System.out.println(accountRequestDto.userId());
        User user = userRepository.findById(accountRequestDto.userId())
                .orElseThrow(() ->
                        new UserNotFoundException(accountRequestDto.userId()));

        Account account = new Account(user, accountRequestDto.type());
        Account savedAccount = accountRepository.save(account);

        return new AccountResponseDto(new UserResponseDto(user), accountRequestDto.type(), savedAccount.getCreatedAt(), savedAccount.getCbu());
    }

    public AccountResponseDto getAccountById(Long accountId) {
        System.out.println("Llega al service con id ");
        System.out.print(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException(accountId)
        );
        
        return new AccountResponseDto(new UserResponseDto(account.getUser()), account.getType(), account.getCreatedAt(), account.getCbu());
    }

    public List<AccountResponseDto> getAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(acc -> new AccountResponseDto(
                        new UserResponseDto(acc.getUser()),
                        acc.getType(),
                        acc.getCreatedAt(),
                        acc.getCbu()
                ))
                .toList();
    }

    public AccountResponseDto updateAccount(AccountRequestDto accountRequestDto) {
        User user = userRepository.findById(accountRequestDto.userId()).orElseThrow(
                () -> new UserNotFoundException(accountRequestDto.userId())
        );
        Account account = new Account(user, accountRequestDto.type());
        Account savedAccount = accountRepository.save(account);

        return new AccountResponseDto(new UserResponseDto(user), accountRequestDto.type(), savedAccount.getCreatedAt(), savedAccount.getCbu());
    }

    public void deleteAccount(Long id) {
        userRepository.deleteById(id);
    }
}
