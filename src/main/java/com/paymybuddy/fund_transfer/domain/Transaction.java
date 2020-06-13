package com.paymybuddy.fund_transfer.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transaction", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_transaction_type_id")
    private TransactionType transactionType;

    //Sending account
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_from_account_id")
    private Account account;

    //Receiving account id
    @Column(name = "to_account_id")
    private int toAccountId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_transaction_currency_id")
    private Currency transactionCurrencyId;

    //Associated bank account if transaction type is AddMoney or TransferToBank
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_bank_account_id")
    private BankAccount bankAccount;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @NotNull
    private BigDecimal amount;

    private String description;

    private BigDecimal transactionFee;

    public Transaction() { };

    public Transaction(Account account, int toAccountId, @NotNull BigDecimal amount) {
        this.account = account;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public Transaction(Account account, BankAccount bankAccount,
                       @NotNull BigDecimal amount) {
        this.account = account;
        this.bankAccount = bankAccount;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Currency getTransactionCurrencyId() {
        return transactionCurrencyId;
    }

    public void setTransactionCurrencyId(Currency transactionCurrencyId) {
        this.transactionCurrencyId = transactionCurrencyId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(BigDecimal transactionFee) {
        this.transactionFee = transactionFee;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @PrePersist
    public void setCreatedOn() {
        this.createdOn = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
