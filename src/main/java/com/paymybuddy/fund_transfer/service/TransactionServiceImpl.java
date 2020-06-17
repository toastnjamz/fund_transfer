package com.paymybuddy.fund_transfer.service;

import com.paymybuddy.fund_transfer.domain.*;
import com.paymybuddy.fund_transfer.repository.TransactionRepository;
import com.paymybuddy.fund_transfer.repository.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionTypeRepository transactionTypeRepository;
    private AccountService accountService;
    private CurrencyService currencyService;
    private BankAccountService bankAccountService;

    //0.5% transaction fee to be collected from sending user for every regular transaction (from friend to friend)
    private BigDecimal transactionFee = new BigDecimal(0.005);

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionTypeRepository transactionTypeRepository,
                                  AccountService accountService, CurrencyService currencyService, BankAccountService bankAccountService) {
        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.accountService = accountService;
        this.currencyService = currencyService;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public List<Transaction> findTransactionListByAccount(Account account) {
        return transactionRepository.findTransactionListByAccount(account);
    }

    @Override
    public List<Transaction> findTransactionListByTransactionType(String transactionType) {
        List<Transaction> transactionList = transactionRepository.findAll().stream()
            .filter(t -> t.getTransactionType().getTransactionType().equals(transactionType)).collect(Collectors.toList());
        return transactionList;
    }

    @Override
    public void createTransactionByTransferToFriend(User sendingUser, String receivingUserEmail, String amount, String description) {
        BigDecimal transactionAmount = new BigDecimal(amount);
        if (transactionValidator("Regular", sendingUser.getEmail(), transactionAmount)) {
            Account sendingAccount = accountService.findAccountByUserEmail(sendingUser.getEmail());
            BigDecimal sendingAccountBalanceBefore = sendingAccount.getBalance();
            Account receivingAccount = accountService.findAccountByUserEmail(receivingUserEmail);
            BigDecimal receivingAccountBalanceBefore = receivingAccount.getBalance();

            Transaction newTransaction = new Transaction(sendingAccount, receivingAccount.getId(), transactionAmount);
            newTransaction.setTransactionType(transactionTypeRepository.findTransactionTypeByTransactionType("Regular"));
            newTransaction.setTransactionCurrencyId(currencyService.findCurrencyByCurrencyLabel("USD"));
            newTransaction.setDescription(description);
            newTransaction.setTransactionFee(transactionAmount.multiply(transactionFee));
            transactionRepository.save(newTransaction);

            BigDecimal amountToSubtractFromSender = calculateTransactionAmountForSender(transactionAmount);
            sendingAccount.setBalance(sendingAccountBalanceBefore.subtract(amountToSubtractFromSender));
            accountService.updateAccount(sendingAccount);
            receivingAccount.setBalance(receivingAccountBalanceBefore.add(transactionAmount));
            accountService.updateAccount(receivingAccount);
        }
    }

    @Override
    public void createTransactionByAddMoney(User sendingUser, String amount) {
        Account sendingAccount = accountService.findAccountByUserEmail(sendingUser.getEmail());
        if (bankAccountService.findBankAccountByAccount(sendingAccount) != null) {
            BigDecimal transactionAmount = new BigDecimal(amount);
            if (transactionValidator("AddMoney", sendingUser.getEmail(), transactionAmount)) {
                BigDecimal sendingAccountBalanceBefore = sendingAccount.getBalance();
                BankAccount sendingAccountBankAccount = sendingAccount.getBankAccount();

                Transaction newTransaction = new Transaction(sendingAccount, sendingAccountBankAccount, transactionAmount);
                newTransaction.setTransactionType(transactionTypeRepository.findTransactionTypeByTransactionType("AddMoney"));
                newTransaction.setTransactionCurrencyId(currencyService.findCurrencyByCurrencyLabel("USD"));
                newTransaction.setDescription("Adding funds from bank.");
                newTransaction.setTransactionFee(new BigDecimal(0.0));
                transactionRepository.save(newTransaction);

                sendingAccount.setBalance(sendingAccountBalanceBefore.add(transactionAmount));
                accountService.updateAccount(sendingAccount);
            }
        }
    }

    @Override
    public void createTransactionByTransferToBank(User sendingUser) {
        Account sendingAccount = accountService.findAccountByUserEmail(sendingUser.getEmail());
        if (bankAccountService.findBankAccountByAccount(sendingAccount) != null) {
            BigDecimal sendingAccountBalanceBefore = sendingAccount.getBalance();
            if (transactionValidator("TransferToBank", sendingUser.getEmail(), sendingAccountBalanceBefore)) {
                BankAccount sendingAccountBankAccount = sendingAccount.getBankAccount();

                Transaction newTransaction = new Transaction(sendingAccount, sendingAccountBankAccount, sendingAccountBalanceBefore);
                newTransaction.setTransactionType(transactionTypeRepository.findTransactionTypeByTransactionType("TransferToBank"));
                newTransaction.setTransactionCurrencyId(currencyService.findCurrencyByCurrencyLabel("USD"));
                newTransaction.setDescription("Transferring funds back to bank.");
                newTransaction.setTransactionFee(new BigDecimal(0.0));
                transactionRepository.save(newTransaction);

                sendingAccount.setBalance(new BigDecimal(0.0));
                accountService.updateAccount(sendingAccount);
            }
        }
    }

    @Override
    public boolean isInCurrencyFormat(String amount) {
        if (amount == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(amount);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean transactionValidator(String transactionType, String sendingUserEmail, BigDecimal transactionAmount) {
        boolean validation = false;
        BigDecimal sendersBalance = accountService.findAccountByUserEmail(sendingUserEmail).getBalance();
        BigDecimal sendersBalanceMinusTransactionAmount = sendersBalance.subtract(calculateTransactionAmountForSender(transactionAmount));

        if (transactionType == "AddMoney" && transactionAmount.intValue() > 0) {
            validation = true;
        }
        else if (transactionType == "TransferToBank" && sendersBalance.intValue() > 0) {
            validation = true;
        }
        else if (transactionType == "Regular" && sendersBalanceMinusTransactionAmount.intValue() >= 0) {
            validation = true;
        }
        return validation;
    }

    //Calculates transaction total for sender: amount * (1 + transaction fee)
    public BigDecimal calculateTransactionAmountForSender(BigDecimal transactionAmount) {
        BigDecimal feeFactor = (new BigDecimal("1").add(transactionFee));
        return transactionAmount.multiply(feeFactor).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}
