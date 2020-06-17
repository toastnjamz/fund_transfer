package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.BankAccount;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.BankAccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepositoryMock;

    @InjectMocks
    private BankAccountServiceImpl bankAccountServiceImpl;

    @Test
    public void findBankAccountByAccount_bankAccountExists_bankAccountReturned() {
        //arrange
        User user = new User();
        Account account = new Account();
        account.setUser(user);
        BankAccount bankAccount = new BankAccount(account, "testbankaccountno");

        when(bankAccountRepositoryMock.findBankAccountByAccount(account)).thenReturn(bankAccount);

        //act
        BankAccount result = bankAccountServiceImpl.findBankAccountByAccount(account);

        //assert
        assertEquals(bankAccount.getBankAccountNo(), result.getBankAccountNo());
    }

    @Test
    public void findBankAccountByAccount_bankAccountDoesNotExist_nullReturned() {
        //arrange
        User user = new User();
        Account account = new Account();
        account.setUser(user);

        //act
        BankAccount result = bankAccountServiceImpl.findBankAccountByAccount(account);

        //assert
        assertNull(result);
    }

    @Test
    public void createBankAccount_bankAccountDoesNotExist_bankAccountReturned() {
        //arrange
        User user = new User();
        Account account = new Account();
        account.setUser(user);
        user.setAccount(account);

        when(bankAccountRepositoryMock.save(any(BankAccount.class))).thenReturn(new BankAccount());

        //act
        BankAccount result = bankAccountServiceImpl.createBankAccount(user, "testbankaccountno");

        //assert
        assertEquals("testbankaccountno", result.getBankAccountNo());
        verify(bankAccountRepositoryMock, times(1)).save(any(BankAccount.class));
    }

    @Test
    public void createBankAccount_bankAccountExists_nullReturned() {
        //arrange
        User user = new User();
        Account account = new Account();
        account.setUser(user);
        user.setAccount(account);
        BankAccount bankAccount = new BankAccount(account, "testbankaccountno");
        account.setBankAccount(bankAccount);

        //act
        BankAccount result = bankAccountServiceImpl.createBankAccount(user, "testbankaccountno");

        //assert
        assertNull(result);
    }
}