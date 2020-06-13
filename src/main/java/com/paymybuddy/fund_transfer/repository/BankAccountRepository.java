package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    BankAccount findBankAccountByAccount(Account account);
}
