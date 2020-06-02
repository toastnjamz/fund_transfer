package com.paymybuddy.fund_transfer.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "transaction_type", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class TransactionType {

    @Id
    private int id;

    //Transaction types: 1 - Regular, 2 - AddMoney, 3 - TransferToBank
    private String transactionType;

    public TransactionType() { };

    public TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionType)) return false;
        TransactionType that = (TransactionType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
