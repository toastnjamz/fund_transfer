package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.AccountType;
import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.repository.AccountRepository;
import com.paymybuddy.fund_transfer.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    //Limited functionality exists for updating an account's balance due to transaction activity.
    @Override
    public void updateAccount(Account account) {
        Account updatedAccount = findAccountById(account.getId());
        updatedAccount.setBalance(account.getBalance());
        accountRepository.save(updatedAccount);
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }



    @Override
    public List<AccountType> findAllAccountTypes() {
        return accountTypeRepository.findAll();
    }

    @Override
    public AccountType findAccountTypeById(int id) {
        Optional<AccountType> accountTypeOptional = accountTypeRepository.findById(id);
        if (accountTypeOptional.isPresent()) {
            AccountType accountType = accountTypeOptional.get();
            return accountType;
        }
        return null;
    }

    @Override
    public AccountType findAccountTypeByAccountType(String accountType) {
        return accountTypeRepository.findAccountTypeByAccountType(accountType);
    }

    @Override
    public AccountType createAccountType(AccountType accountType) {
        return accountTypeRepository.save(accountType);
    }

    @Override
    public void updateAccountType(AccountType accountType) {
        AccountType updatedAccountType = findAccountTypeById(accountType.getId());
        updatedAccountType.setAccountType(accountType.getAccountType());
    }

    @Override
    public void deleteAccountType(int id) {
        accountTypeRepository.deleteById(id);
    }
}
