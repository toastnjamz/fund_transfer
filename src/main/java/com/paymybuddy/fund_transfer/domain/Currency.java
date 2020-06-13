package com.paymybuddy.fund_transfer.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "currency", catalog = "fund_transfer")
@EntityListeners(AuditingEntityListener.class)
public class Currency {

    @Id
    private int id;

    //Currency types - 1: USD for US Dollars
    private String currencyLabel;

    private String currencyDescription;

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
