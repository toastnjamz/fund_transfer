package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.Transaction;
import com.paymybuddy.fund_transfer.domain.TransactionType;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.repository.TransactionRepository;
import com.paymybuddy.fund_transfer.repository.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionTypeRepository transactionTypeRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @Override
    public List<Transaction> findTransactionListByAccount(Account account) {
        return transactionRepository.findTransactionListByAccount(account);
    }

    //TODO
    @Override
    public void createTransactionByTransferToFriend(User sendingUser, String receivingUserEmail, String description, String amount) {
        Transaction newTransaction = new Transaction();
    }

    //TODO
    public boolean isInCurrencyFormat(String amount) {
        if (amount == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(amount);
            int i = Integer.parseInt(amount);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }



    //TODO: Remove if not using

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findTransactionById(int id) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isPresent()) {
            Transaction transaction = transactionOptional.get();
            return transaction;
        }
        return null;
    }

//    @Override
//    public List<Transaction> findTransactionListBySendingUserEmail(String sendingUserEmail) {
//        return transactionRepository.findTransactionListBySendingUserEmail(sendingUserEmail);
//    }
//
//    @Override
//    public List<Transaction> findTransactionListByReceivingUserEmail(String receivingUserEmail) {
//        return transactionRepository.findTransactionListByReceivingUserEmail(receivingUserEmail);
//    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    //Limited functionality exists for updating a transaction.
    @Override
    public void updateTransaction(Transaction transaction) {
        Transaction updatedTransaction = findTransactionById(transaction.getId());
        updatedTransaction.setAmount(transaction.getAmount());
        updatedTransaction.setTransactionCurrencyId(transaction.getTransactionCurrencyId());
        updatedTransaction.setDescription(transaction.getDescription());
    }

    @Override
    public void deleteTransaction(int id) {
        transactionRepository.deleteById(id);
    }



    @Override
    public List<TransactionType> findAllTransactionTypes() {
        return transactionTypeRepository.findAll();
    }

    @Override
    public TransactionType findTransactionTypeById(int id) {
        return null;
    }

    @Override
    public TransactionType createTransactionType(TransactionType transactionType) {
        return transactionTypeRepository.save(transactionType);
    }

    @Override
    public void updateTransactionType(TransactionType transactionType) {
    }

    @Override
    public void deleteTransactionType(int id) {
        transactionTypeRepository.deleteById(id);
    }
}
