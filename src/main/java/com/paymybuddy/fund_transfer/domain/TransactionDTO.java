package com.paymybuddy.fund_transfer.domain;

import java.math.BigDecimal;

public class TransactionDTO {

    private String toUserEmail;
    private String amount;
    private String description;

    public TransactionDTO() {
        this.toUserEmail = "";
        this.amount = "0.0";
        this.description = "";
    }

    public TransactionDTO(String toUserEmail, String amount, String description) {
        this.toUserEmail = toUserEmail;
        this.amount = amount;
        this.description = description;
    }

    public String getToUserEmail() {
        return toUserEmail;
    }

    public void setToUserEmail(String toUserEmail) {
        this.toUserEmail = toUserEmail;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
