package com.paymybuddy.fund_transfer.domain;

import java.math.BigDecimal;

public class TransactionDTO {

    private String toUserEmail;
    private BigDecimal amount;
    private String description;

    public TransactionDTO() {
        this.toUserEmail = "";
        this.description = "";
        this.amount = new BigDecimal(0.0);
    }

    public TransactionDTO(String toUserEmail, String description, BigDecimal amount) {
        this.toUserEmail = toUserEmail;
        this.description = description;
        this.amount = amount;
    }

    public String getToUserEmail() {
        return toUserEmail;
    }

    public void setToUserEmail(String toUserEmail) {
        this.toUserEmail = toUserEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
