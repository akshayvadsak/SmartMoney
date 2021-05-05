package com.credistudio.dailybudgettracker.Dagger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.credistudio.dailybudgettracker.Classes.AccountDetails;
import com.credistudio.dailybudgettracker.Classes.Category;
import com.credistudio.dailybudgettracker.Classes.Expense;
import com.credistudio.dailybudgettracker.Classes.Notifications;
import com.credistudio.dailybudgettracker.Classes.Transactions;
import com.credistudio.dailybudgettracker.Database.DBHelper;

import java.util.ArrayList;
import javax.inject.Inject;

public class DataManager {
    private static DBHelper instance;
    SQLiteDatabase db;
    private Context mContext;
    private DBHelper mDbHelper;

    @Inject
    public DataManager(Context context, DBHelper dBHelper) {
        this.mContext = context;
        this.mDbHelper = dBHelper;
    }

    public boolean addBank(AccountDetails accountDetails) throws Exception {
        return this.mDbHelper.add_bankDetails(this.db, accountDetails);
    }

    public boolean addCredit(AccountDetails accountDetails) throws Exception {
        return this.mDbHelper.add_creditCard(this.db, accountDetails);
    }

    public boolean addWallet(AccountDetails accountDetails) throws Exception {
        return this.mDbHelper.add_eWallet(this.db, accountDetails);
    }

    public boolean addIncome(Expense expense) throws Exception {
        return this.mDbHelper.add_income(this.db, expense);
    }

    public boolean addExpense(Expense expense) throws Exception {
        return this.mDbHelper.add_Expenses(this.db, expense);
    }

    public boolean addTransaction(Transactions transactions) throws Exception {
        return this.mDbHelper.add_transaction(this.db, transactions);
    }

    public boolean addCategory(Category category) throws Exception {
        return this.mDbHelper.add_category(this.db, category);
    }

    public boolean addCategoryIncome(Category category) throws Exception {
        return this.mDbHelper.addIncomeCategory(this.db, category);
    }

    public boolean addCategoryList(ArrayList<Category> arrayList) throws Exception {
        return this.mDbHelper.add_categoryList(this.db, arrayList);
    }

    public boolean addCategoryListIncome(ArrayList<Category> arrayList) throws Exception {
        return this.mDbHelper.add_categoryListIncome(this.db, arrayList);
    }

    public boolean addCash(String str) throws Exception {
        return this.mDbHelper.add_cash(this.db, str);
    }

    public String getCash() throws Exception {
        return this.mDbHelper.get_cash(this.db);
    }

    public ArrayList<Transactions> getTransaction() throws Exception {
        return this.mDbHelper.getTransaction(this.db);
    }

    public ArrayList<Category> getCategory() throws Exception {
        return this.mDbHelper.get_category(this.db);
    }

    public ArrayList<Category> getCategoryIncome() throws Exception {
        return this.mDbHelper.get_categoryIncome(this.db);
    }

    public ArrayList<AccountDetails> getBank() throws Exception {
        return this.mDbHelper.get_bank(this.db);
    }

    public ArrayList<AccountDetails> gettWallet() throws Exception {
        return this.mDbHelper.get_eWaller(this.db);
    }

    public ArrayList<AccountDetails> getCredit() throws Exception {
        return this.mDbHelper.get_credit(this.db);
    }

    public ArrayList<Expense> getExpense() throws Exception {
        return this.mDbHelper.getExpenses(this.db);
    }

    public ArrayList<Expense> getIncome() throws Exception {
        return this.mDbHelper.getIncome(this.db);
    }

    public AccountDetails updateBank(AccountDetails accountDetails, int i) {
        return this.mDbHelper.updateBank(this.db, accountDetails, i);
    }

    public AccountDetails updateWallet(AccountDetails accountDetails, int i) {
        return this.mDbHelper.updateWallet(this.db, accountDetails, i);
    }

    public AccountDetails updateCredit(AccountDetails accountDetails, int i) {
        return this.mDbHelper.updateCreditCard(this.db, accountDetails, i);
    }

    public ArrayList<Expense> getfilterListIncome(String str, String str2, String str3, String str4) throws Exception {
        return this.mDbHelper.getIncomeFilter(this.db, str, str2, str3, str4);
    }

    public ArrayList<Transactions> getTransactionFilters(String str, String str2, String str3, String str4) throws Exception {
        return this.mDbHelper.getTransferFilters(this.db, str, str2, str3, str4);
    }

    public ArrayList<Expense> getDefaultExpense(String str, String str2) throws Exception {
        return this.mDbHelper.getDefaultExpenseData(this.db, str, str2);
    }

    public ArrayList<Expense> getfilterListExpense(String str, String str2, String str3, String str4) throws Exception {
        return this.mDbHelper.getExpenseFilter(this.db, str, str2, str3, str4);
    }

    public boolean deleteBank(int i) throws Exception {
        return this.mDbHelper.deleteBank(this.db, i);
    }

    public boolean deleteWallet(int i) throws Exception {
        return this.mDbHelper.deleteWallet(this.db, i);
    }

    public boolean deleteCreditCard(int i) throws Exception {
        return this.mDbHelper.deleteCreditCard(this.db, i);
    }

    public boolean deleteDebitCard(int i) throws Exception {
        return this.mDbHelper.deleteDebitCard(this.db, i);
    }

    public ArrayList<Notifications> getNotification() throws Exception {
        return this.mDbHelper.getNotification(this.db);
    }

    public boolean addNotification(Notifications notifications) throws Exception {
        return this.mDbHelper.add_notification(this.db, notifications);
    }

    public boolean deleteNotification(int i) throws Exception {
        return this.mDbHelper.deleteNotifications(this.db, i);
    }

    public boolean deleteExpenseCategory(int i) throws Exception {
        return this.mDbHelper.deleteCategoryExpense(this.db, i);
    }

    public boolean deleteIncomeCategory(int i) throws Exception {
        return this.mDbHelper.deleteCategoryIncome(this.db, i);
    }

    public boolean deleteIncome(int i) throws Exception {
        return this.mDbHelper.deleteIncome(this.db, i);
    }

    public boolean deleteExpense(int i) throws Exception {
        return this.mDbHelper.deleteExpense(this.db, i);
    }

    public boolean deleteTransfer(int i) throws Exception {
        return this.mDbHelper.deleteTransfer(this.db, i);
    }

    public Expense updateExpense(Expense expense, int i) {
        return this.mDbHelper.updateExpense(this.db, expense, i);
    }

    public Expense updateIncome(Expense expense, int i) {
        return this.mDbHelper.updateIncome(this.db, expense, i);
    }

    public Transactions updateTransaction(Transactions transactions, int i) {
        return this.mDbHelper.updateTransfers(this.db, transactions, i);
    }
}
