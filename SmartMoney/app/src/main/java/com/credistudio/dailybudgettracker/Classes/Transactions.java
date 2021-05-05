package com.credistudio.dailybudgettracker.Classes;

public class Transactions {
    private String amount = "";
    private String comment = "";
    private String date = "";
    private String fromAccount = "";
    private String fromAccountId = "";
    private String toAccount = "";
    private String toAccountId = "";
    private int transactionId = 0;

    public int getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(int i) {
        this.transactionId = i;
    }

    public String getFromAccount() {
        return this.fromAccount;
    }

    public void setFromAccount(String str) {
        this.fromAccount = str;
    }

    public String getFromAccountId() {
        return this.fromAccountId;
    }

    public void setFromAccountId(String str) {
        this.fromAccountId = str;
    }

    public String getToAccount() {
        return this.toAccount;
    }

    public void setToAccount(String str) {
        this.toAccount = str;
    }

    public String getToAccountId() {
        return this.toAccountId;
    }

    public void setToAccountId(String str) {
        this.toAccountId = str;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String str) {
        this.amount = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String str) {
        this.comment = str;
    }
}
