package com.paymybuddy.fund_transfer.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

//    //Receiving account
//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_to_account_id")
//    private Account toAccount;

    //Receiving account id
    @Column(name = "to_account_id")
    private int toAccountId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_transaction_currency_id")
    private Currency transactionCurrencyId;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @NotEmpty(message="Enter a valid value.")
    private BigDecimal amount;

    @NotEmpty(message="Enter a description.")
    private String description;

    //TODO: should transaction fee be a field?

//    //TODO: How to remove this, since @Transient doesn't work and TransactionRepository seems to need it?
//    //Email field for TransactionRepository method findTransactionListBySendingUserEmail(String fromAccountUserEmail);
//    private String sendingUserEmail;
//
//    //TODO: How to remove this, since @Transient doesn't work and TransactionRepository seems to need it?
//    //Email field for TransactionRepository method findTransactionListByReceivingUserEmail(String toAccountUserEmail);
//    private String receivingUserEmail;


    public Transaction() { };

    public Transaction(TransactionType transactionType, Account account, int toAccountId, Currency transactionCurrencyId,
                       @NotEmpty(message = "Enter a valid value.") BigDecimal amount, @NotEmpty(message = "Enter a description.") String description) {
        this.transactionType = transactionType;
        this.account = account;
        this.toAccountId = toAccountId;
        this.transactionCurrencyId = transactionCurrencyId;
        this.amount = amount;
        this.description = description;
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

//    public Account getToAccount() {
//        return toAccount;
//    }
//
//    public void setToAccount(Account toAccount) {
//        this.toAccount = toAccount;
//    }

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
