package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.*;

import java.util.List;

public interface TransactionService {

    public List<Transaction> findTransactionListByTransactionType(String transactionType);

    public List<Transaction> findTransactionListByAccount(Account account);

    public void createTransactionByTransferToFriend(User sendingUser, String receivingUserEmail, String description, String amount);

    public void createTransactionByTransferToBank(User sendingUser);

    public void createTransactionByAddMoney(User sendingUser, String amount);

    public boolean isInCurrencyFormat(String amount);
}
