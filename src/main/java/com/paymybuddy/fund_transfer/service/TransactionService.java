package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.Account;
import com.paymybuddy.fund_transfer.domain.Transaction;
import com.paymybuddy.fund_transfer.domain.TransactionType;
import com.paymybuddy.fund_transfer.domain.User;

import java.util.List;

public interface TransactionService {

    public List<Transaction> findAllTransactions();

    public Transaction findTransactionById(int id);

    //TODO
//    List<Transaction> findTransactionListBySendingUserEmail(String sendingUserEmail);
//    List<Transaction> findTransactionListByReceivingUserEmail(String receivingUserEmail);

    public List<Transaction> findTransactionListByAccount(Account account);

    public Transaction createTransaction(Transaction transaction);

    public void createTransactionByTransferToFriend(User sendingUser, String receivingUserEmail, String description, String amount);

    public void updateTransaction(Transaction transaction);

    public void deleteTransaction(int id);


    public List<TransactionType> findAllTransactionTypes();

    public TransactionType findTransactionTypeById(int id);

    public TransactionType createTransactionType(TransactionType transactionType);

    public void updateTransactionType(TransactionType transactionType);

    public void deleteTransactionType(int id);
}
