package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.AccountType;
import com.paymybuddy.fund_transfer.repository.AccountRepository;
import com.paymybuddy.fund_transfer.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountTypeRepository accountTypeRepository) {
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
    }

    @Override
    public Account findAccountById(int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account roleType = accountOptional.get();
            return roleType;
        }
        return null;
    }

    @Override
    public Account findAccountByUserEmail(String email) {
        return accountRepository.findAccountByUserEmail(email);
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    //Limited functionality exists for updating an account's balance due to transaction activity.
    @Override
    public void updateAccount(Account account) {
        Account updatedAccount = findAccountById(account.getId());
        updatedAccount.setBalance(account.getBalance());
        accountRepository.save(updatedAccount);
    }

    @Override
    public AccountType findAccountTypeByAccountType(String accountType) {
        return accountTypeRepository.findAccountTypeByAccountType(accountType);
    }
}
