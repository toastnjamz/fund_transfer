package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.AccountType;
import com.paymybuddy.fund_transfer.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountTypeServiceImpl implements AccountTypeService {

    private AccountTypeRepository accountTypeRepository;

    @Autowired
    public AccountTypeServiceImpl(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
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
