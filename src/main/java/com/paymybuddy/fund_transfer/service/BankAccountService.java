package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.BankAccount;
import com.paymybuddy.fund_transfer.domain.User;

public interface BankAccountService {

    public BankAccount findBankAccountByAccount(Account account);

    public BankAccount createBankAccount(User user, String bankAccountNo);
}
