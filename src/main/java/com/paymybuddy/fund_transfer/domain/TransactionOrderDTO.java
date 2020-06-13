package com.paymybuddy.fund_transfer.domain;

public class TransactionOrderDTO {

    private String fromUserEmail;
    private String toUserEmail;
    private String description;
    private String amount;
    private String fee;
    private String date;

    public TransactionOrderDTO() {
        this.fromUserEmail = "";
        this.toUserEmail = "";
        this.description = "";
        this.amount = "0.0";
        this.fee = "0.0";
        this.date = "";
    }

    public TransactionOrderDTO(String fromUserEmail, String toUserEmail, String description, String amount, String fee, String date) {
        this.fromUserEmail = fromUserEmail;
        this.toUserEmail = toUserEmail;
        this.description = description;
        this.amount = amount;
        this.fee = fee;
        this.date = date;
    }

    public String getFromUserEmail() {
        return fromUserEmail;
    }

    public void setFromUserEmail(String fromUserEmail) {
        this.fromUserEmail = fromUserEmail;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
