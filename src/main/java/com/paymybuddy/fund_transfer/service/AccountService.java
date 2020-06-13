package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.AccountType;

public interface AccountService {

    public Account findAccountById(int id);

    public Account findAccountByUserEmail(String email);

    public Account createAccount(Account account);

    public void updateAccount(Account account);

    public AccountType findAccountTypeByAccountType(String accountType);
}
