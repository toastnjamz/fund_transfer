package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.BankAccount;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount findBankAccountByAccount(Account account) {
        return bankAccountRepository.findBankAccountByAccount(account);
    }

    //Users can only link one bank account at a time to their PayMyBuddy account for this version of the app (V1)
    @Override
    public BankAccount createBankAccount(User user, String bankAccountNo) {
        Account userAccount = user.getAccount();
        if (userAccount.getBankAccount() == null) {
            BankAccount newBankAccount = new BankAccount(userAccount, bankAccountNo);
            return bankAccountRepository.save(newBankAccount);
        }
        return null;
    }
}
