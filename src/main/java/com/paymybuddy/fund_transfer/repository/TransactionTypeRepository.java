package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer> {

    TransactionType findTransactionTypeByTransactionType(String transactionType);
}
