package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.AccountType;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.AccountRepository;
import com.paymybuddy.fund_transfer.repository.AccountTypeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;

import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepositoryMock;

    @Mock
    private AccountTypeRepository accountTypeRepositoryMock;

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Test
    public void findAccountById_accountExists_accountReturned() {
        //arrange
        User user = new User();
        Account account = new Account();
        account.setId(1);
        account.setUser(user);

        when(accountRepositoryMock.findById(account.getId())).thenReturn(Optional.of(account));

        //act
        Account result = accountServiceImpl.findAccountById(1);

        //assert
        assertEquals(account.getId(), result.getId(), 0);
    }

    @Test
    public void findAccountById_accountDoesNotExist_nullReturned() {
        //arrange

        //act
        Account result = accountServiceImpl.findAccountById(1);

        //assert
        assertNull(result);
    }

    @Test
    public void findAccountByUserEmail_accountExists_accountReturned() {
        //arrange
        User user = new User();
        user.setEmail("testEmail@test.com");
        Account account = new Account();
        account.setUser(user);

        when(accountRepositoryMock.findAccountByUserEmail(account.getUser().getEmail())).thenReturn(account);

        //act
        Account result = accountServiceImpl.findAccountByUserEmail("testEmail@test.com");

        //assert
        assertEquals(account, result);
    }

    @Test
    public void findAccountByUserEmail_accountDoesNotExist_nullReturned() {
        //arrange

        //act
        Account result = accountServiceImpl.findAccountByUserEmail("testEmail@test.com");

        //assert
        assertNull(result);
    }

    @Test
    public void createAccount_accountValid_accountSaved() {
        //arrange
        User user = new User();
        Account account = new Account();
        account.setId(1);
        account.setUser(user);

        when(accountServiceImpl.createAccount(account)).thenReturn(account);

        //act
        Account result = accountServiceImpl.createAccount(account);

        //assert
        assertEquals(1, result.getId());
        verify(accountRepositoryMock, times(1)).save(any(Account.class));
    }

    @Test
    public void updateAccount_accountValid_accountSaved() {
        //arrange
        User user = new User();
        Account account = new Account();
        account.setId(1);
        account.setUser(user);
        account.setBalance(new BigDecimal(0.0));
        user.setAccount(account);
        assertThat(new BigDecimal(0.0), Matchers.comparesEqualTo(account.getBalance()));

        account.setBalance(new BigDecimal(10.0));
        Optional<Account> optionalOfAccount = Optional.of(account);
        when(accountRepositoryMock.findById(1)).thenReturn(optionalOfAccount);

        //act
        accountServiceImpl.updateAccount(account);

        //assert
        assertThat(new BigDecimal(10.0), Matchers.comparesEqualTo(account.getBalance()));
        verify(accountRepositoryMock, times(1)).save(any(Account.class));
    }

    @Test
    public void findAccountTypeByAccountType_accountTypeExists_accountReturned() {
        //arrange
        AccountType accountType = new AccountType("Test");

        when(accountTypeRepositoryMock.findAccountTypeByAccountType(accountType.getAccountType())).thenReturn(accountType);

        //act
        AccountType result = accountServiceImpl.findAccountTypeByAccountType("Test");

        //assert
        assertEquals(accountType, result);
    }

    @Test
    public void findAccountTypeByAccountType_accountTypeDoesNotExist_nullReturned() {
        //arrange

        //act
        AccountType result = accountServiceImpl.findAccountTypeByAccountType("Test");

        //assert
        assertNull(result);
    }
}
