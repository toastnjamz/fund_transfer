package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;

import java.util.List;

public interface AccountService {

    public List<Account> findAllAccounts();

    public Account findAccountById(int id);

    public Account findAccountByUserEmail(String email);

    public Account createAccount(Account account);

    public void updateAccount(Account account);

    public void deleteAccount(int id);
}
