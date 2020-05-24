package com.paymybuddy.fund_transfer.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "currency", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //There is currently one currency type - USD for US Dollars
    private String currencyLabel;

    private String currencyDescription;

    @OneToMany(mappedBy = "currency")
    @JsonManagedReference
    private List<Account> accountList;

    public Currency() { }

    public Currency(String currencyLabel, String currencyDescription) {
        this.currencyLabel = currencyLabel;
        this.currencyDescription = currencyDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrencyLabel() {
        return currencyLabel;
    }

    public void setCurrencyLabel(String currencyLabel) {
        this.currencyLabel = currencyLabel;
    }

    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return id == currency.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
