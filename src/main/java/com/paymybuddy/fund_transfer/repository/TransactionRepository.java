package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findTransactionListByAccount(Account account);
}
