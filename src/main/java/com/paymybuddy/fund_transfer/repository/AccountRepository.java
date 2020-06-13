package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountByUserEmail(String email);
}
