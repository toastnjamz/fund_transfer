package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {

    AccountType findAccountTypeByAccountType(String accountType);
}
