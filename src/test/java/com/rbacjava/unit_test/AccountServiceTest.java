package com.rbacjava.unit_test;

import com.rbacjava.Exceptions.AccountNotFoundException;
import com.rbacjava.Exceptions.UserNotFoundException;
import com.rbacjava.models.dao.Account;
import com.rbacjava.models.dao.AccountType;
import com.rbacjava.models.dao.User;
import com.rbacjava.models.dto.AccountRequestDto;
import com.rbacjava.models.dto.AccountResponseDto;
import com.rbacjava.repos.AccountRepository;
import com.rbacjava.repos.UserRepository;
import com.rbacjava.services.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountService accountService;

    private User user;
    private Account account;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setEmail("david@mail.com");
        user.setUsername("david");

        account = new Account(user, AccountType.SAVINGS);
        account.setId(1L);
        account.setCbu("1234567890123456789012");
        account.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void create_account_successfully() {
        AccountRequestDto dto = new AccountRequestDto(1L, AccountType.SAVINGS);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountResponseDto response = accountService.createAccount(dto);

        Assertions.assertEquals("david@mail.com", response.user().getEmail());
        Assertions.assertEquals("david", response.user().getUsername());
        Assertions.assertEquals(AccountType.SAVINGS, response.accountType());

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void get_account_by_id_successfully() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        AccountResponseDto response = accountService.getAccountById(1L);

        Assertions.assertEquals(AccountType.SAVINGS, response.accountType());
        Assertions.assertEquals("david", response.user().getUsername());
        Assertions.assertEquals(account.getCbu(), response.cbu());
    }

    @Test
    void get_account_by_id_not_found() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAccountById(1L);
        });
    }

    @Test
    void get_all_accounts() {
        Account account2 = new Account(user, AccountType.CHECKING);
        account2.setId(2L);
        account2.setCbu("9999999999999999999999");
        account2.setCreatedAt(LocalDateTime.now());

        when(accountRepository.findAll()).thenReturn(List.of(account, account2));

        List<AccountResponseDto> result = accountService.getAccounts();

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(AccountType.SAVINGS, result.get(0).accountType());
        Assertions.assertEquals(AccountType.CHECKING, result.get(1).accountType());
    }

    @Test
    void update_account_successfully() {
        AccountRequestDto dto = new AccountRequestDto(1L, AccountType.CHECKING);

        Account updatedAccount = new Account(user, AccountType.CHECKING);
        updatedAccount.setCbu("2222222222222222222222");
        updatedAccount.setCreatedAt(LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountRepository.save(any(Account.class))).thenReturn(updatedAccount);

        AccountResponseDto response = accountService.updateAccount(dto);

        Assertions.assertEquals(AccountType.CHECKING, response.accountType());
        Assertions.assertEquals("david", response.user().getUsername());

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void update_account_user_not_found() {
        AccountRequestDto dto = new AccountRequestDto(1L, AccountType.CHECKING);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            accountService.updateAccount(dto);
        });

        verify(accountRepository, never()).save(any());
    }

    @Test
    void delete_account_successfully() {
        doNothing().when(accountRepository).deleteById(1L);

        accountService.deleteAccount(1L);

        verify(accountRepository).deleteById(1L);
        verify(userRepository, never()).deleteById(anyLong());
    }
}
