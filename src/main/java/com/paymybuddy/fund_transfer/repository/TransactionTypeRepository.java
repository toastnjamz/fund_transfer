package com.paymybuddy.fund_transfer.repository;

import com.paymybuddy.fund_transfer.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer> {

    TransactionType findTransactionTypeByTransactionType(String transactionType);
}