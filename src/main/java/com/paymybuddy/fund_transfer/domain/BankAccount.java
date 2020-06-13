package com.paymybuddy.fund_transfer.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bank_account", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //User's linked PayMyBuddy account
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_account_id")
    private Account account;

    //Bank account number entered by the user and used for identifying their linked bank account
    @NotNull
    private String bankAccountNo;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<Transaction> transactionList;

    public BankAccount() { };

    public BankAccount(Account account, @NotNull String bankAccountNo) {
        this.account = account;
        this.bankAccountNo = bankAccountNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String iban) {
        this.bankAccountNo = iban;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        BankAccount that = (BankAccount) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
