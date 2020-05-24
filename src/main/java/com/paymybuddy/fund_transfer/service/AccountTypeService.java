package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.AccountType;

import java.util.List;

public interface AccountTypeService {

    public List<AccountType> findAllAccountTypes();

    public AccountType findAccountTypeById(int id);

    public AccountType findAccountTypeByAccountType(String accountType);

    public AccountType createAccountType(AccountType accountType);

    public void updateAccountType(AccountType accountType);

    public void deleteAccountType(int id);
}
