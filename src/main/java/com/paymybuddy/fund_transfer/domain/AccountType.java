package com.paymybuddy.fund_transfer.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account_type", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class AccountType {

    @Id
    private int id;

    //Account types: 1 - Regular
    private String accountType;

    public AccountType() { }

    public AccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountType)) return false;
        AccountType that = (AccountType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
