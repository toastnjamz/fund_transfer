package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
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

    //Limited functionality exists for updating an account. For instance, you cannot switch owning users.
    @Override
    public void updateAccount(Account account) {
        Account updatedAccount = findAccountById(account.getId());
        updatedAccount.setCurrency(account.getCurrency());
        updatedAccount.setBalance(account.getBalance());
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }
}
