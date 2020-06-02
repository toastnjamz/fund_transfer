package com.paymybuddy.fund_transfer.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "account_type", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class AccountType {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Account types: 1 - Regular, 2 - Bank
    private String accountType;

//    @OneToMany(mappedBy = "accountType")
//    @JsonManagedReference
//    private List<Account> accountList;

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

//    public List<Account> getAccountList() {
//        return accountList;
//    }
//
//    public void setAccountList(List<Account> accountList) {
//        this.accountList = accountList;
//    }

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
