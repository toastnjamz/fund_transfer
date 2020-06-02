package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.AccountType;

import java.util.List;

public interface AccountService {

    public List<Account> findAllAccounts();

    public Account findAccountById(int id);

    public Account findAccountByUserEmail(String email);

    public Account createAccount(Account account);

    public void updateAccount(Account account);

    public void deleteAccount(int id);


    public List<AccountType> findAllAccountTypes();

    public AccountType findAccountTypeById(int id);

    public AccountType findAccountTypeByAccountType(String accountType);

    public AccountType createAccountType(AccountType accountType);

    public void updateAccountType(AccountType accountType);

    public void deleteAccountType(int id);
}
