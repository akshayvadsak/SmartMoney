package com.credistudio.dailybudgettracker.Classes;

public class AccountDetails {
    private int accountId;
    private String accountName;
    private String accountType;
    private String bankAccNo;
    private int bankId;
    private String bankName;
    private String creditCardBalance;
    private String creditCardName;
    private String creditCardNo;
    private int creditId;
    private String debitCardBalance;
    private String debitCardName;
    private String debitCardNo;
    private int debitId;
    private int eWalletId;
    private String eWalletName;
    private String ewalletBalance;
    private String initialBalance;
    private String number;
    private String totalAmount;
    private String type;

    public AccountDetails() {
        this.bankName = "";
        this.accountType = "";
        this.bankAccNo = "";
        this.initialBalance = "";
        this.ewalletBalance = "";
        this.eWalletId = 0;
        this.ewalletBalance = "";
        this.creditCardName = "";
        this.creditId = 0;
        this.creditCardNo = "";
        this.creditCardBalance = "";
        this.debitCardName = "";
        this.debitId = 0;
        this.debitCardNo = "";
        this.debitCardBalance = "";
        this.accountId = 0;
        this.number = "";
        this.accountName = "";
        this.totalAmount = "";
        this.type = "";
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String str) {
        this.bankName = str;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String str) {
        this.accountType = str;
    }

    public String getInitialBalance() {
        return this.initialBalance;
    }

    public void setInitialBalance(String str) {
        this.initialBalance = str;
    }

    public String geteWalletName() {
        return this.eWalletName;
    }

    public void seteWalletName(String str) {
        this.eWalletName = str;
    }

    public String getEwalletBalance() {
        return this.ewalletBalance;
    }

    public void setEwalletBalance(String str) {
        this.ewalletBalance = str;
    }

    public String getCreditCardName() {
        return this.creditCardName;
    }

    public void setCreditCardName(String str) {
        this.creditCardName = str;
    }

    public String getCreditCardBalance() {
        return this.creditCardBalance;
    }

    public void setCreditCardBalance(String str) {
        this.creditCardBalance = str;
    }

    public String getDebitCardName() {
        return this.debitCardName;
    }

    public void setDebitCardName(String str) {
        this.debitCardName = str;
    }

    public String getDebitCardBalance() {
        return this.debitCardBalance;
    }

    public void setDebitCardBalance(String str) {
        this.debitCardBalance = str;
    }

    public int getBankId() {
        return this.bankId;
    }

    public void setBankId(int i) {
        this.bankId = i;
    }

    public int geteWalletId() {
        return this.eWalletId;
    }

    public void seteWalletId(int i) {
        this.eWalletId = i;
    }

    public int getCreditId() {
        return this.creditId;
    }

    public void setCreditId(int i) {
        this.creditId = i;
    }

    public int getDebitId() {
        return this.debitId;
    }

    public void setDebitId(int i) {
        this.debitId = i;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String str) {
        this.accountName = str;
    }

    public int getAccountId() {
        return this.accountId;
    }

    public void setAccountId(int i) {
        this.accountId = i;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getBankAccNo() {
        return this.bankAccNo;
    }

    public void setBankAccNo(String str) {
        this.bankAccNo = str;
    }

    public String getCreditCardNo() {
        return this.creditCardNo;
    }

    public void setCreditCardNo(String str) {
        this.creditCardNo = str;
    }

    public String getDebitCardNo() {
        return this.debitCardNo;
    }

    public void setDebitCardNo(String str) {
        this.debitCardNo = str;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String str) {
        this.number = str;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(String str) {
        this.totalAmount = str;
    }
}
