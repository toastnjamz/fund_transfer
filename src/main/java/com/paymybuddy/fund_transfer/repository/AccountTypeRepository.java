package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {

    AccountType findAccountTypeByAccountType(String accountType);
}
