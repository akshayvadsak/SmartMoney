package com.credistudio.dailybudgettracker.Classes;

public class Expense {
    private String account = "";
    private String accountId = "";
    private String amount = "";
    private String category = "";
    private String categoryId = "";
    private String commnet = "";
    private String date = "";
    private int id = 0;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
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

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public String getCommnet() {
        return this.commnet;
    }

    public void setCommnet(String str) {
        this.commnet = str;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String str) {
        this.account = str;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String str) {
        this.accountId = str;
    }
}
