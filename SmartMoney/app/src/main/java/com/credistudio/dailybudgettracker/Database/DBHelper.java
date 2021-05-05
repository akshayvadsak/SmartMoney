package com.credistudio.dailybudgettracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.credistudio.dailybudgettracker.Classes.AccountDetails;
import com.credistudio.dailybudgettracker.Classes.Category;
import com.credistudio.dailybudgettracker.Classes.Expense;
import com.credistudio.dailybudgettracker.Classes.Notifications;
import com.credistudio.dailybudgettracker.Classes.Transactions;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DBHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase db;
    private static DBHelper instance;
    private String table_bankDetails = "BANK_DETAILS";
    private String table_cash = "CASH";
    private String table_category = "CATEGORY";
    private String table_categoryIncome = "CATEGORY_INCOME";
    private String table_creditCard = "CREDIT_DETAILS";
    private String table_debitCard = "DEBIT_DETAILS";
    private String table_eWallet = "EWALLET_DETAILS";
    private String table_expenses = "EXPENSES";
    private String table_income = "INCOME";
    private String table_notification = "NOTIFICATION";
    private String table_transfer = "TRANSFER";

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public DBHelper(@ApplicationContext Context context, @DatabaseInfo String str, CursorFactory cursorFactory, @DatabaseInfo int i) {
        super(context, "MyExpenseDB", null, 1);
    }

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    System.out.println("getInstance(): First time getInstance was invoked!");
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }

    @Inject
    public DBHelper(Context context) {
        super(context, "MyExpenseDB", null, 1);
    }

    public static SQLiteDatabase getInstanceDB() {
        if (db == null) {
            synchronized (DBHelper.class) {
                if (db == null) {
                    System.out.println("getInstance(): First time getInstance was invoked!");
                    db = instance.getWritableDatabase();
                }
            }
        }
        return db;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        db = sQLiteDatabase;
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(this.table_notification);
        sb.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE text, TIME text, TITLE text, MESSAGE text )");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("CREATE TABLE ");
        sb3.append(this.table_category);
        sb3.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY text )");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append("CREATE TABLE ");
        sb5.append(this.table_categoryIncome);
        sb5.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY_INCOME text )");
        String sb6 = sb5.toString();
        StringBuilder sb7 = new StringBuilder();
        sb7.append("CREATE TABLE ");
        sb7.append(this.table_cash);
        sb7.append("(AMOUNT text )");
        String sb8 = sb7.toString();
        StringBuilder sb9 = new StringBuilder();
        sb9.append("CREATE TABLE ");
        sb9.append(this.table_income);
        sb9.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT text, DATE text, CATEGORY text, CATEGORY_ID text, ACCOUNT text, ACCOUNT_ID text, COMMENT text )");
        String sb10 = sb9.toString();
        StringBuilder sb11 = new StringBuilder();
        sb11.append("CREATE TABLE ");
        sb11.append(this.table_expenses);
        sb11.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT text, DATE text, CATEGORY text, CATEGORY_ID text, ACCOUNT text, ACCOUNT_ID text, COMMENT text )");
        String sb12 = sb11.toString();
        StringBuilder sb13 = new StringBuilder();
        sb13.append("CREATE TABLE ");
        sb13.append(this.table_bankDetails);
        sb13.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, BANK_NAME text, ACCOUNT_NUMBER text, ACCOUNT_TYPE text, INITIAL_BALANCE text )");
        String sb14 = sb13.toString();
        StringBuilder sb15 = new StringBuilder();
        sb15.append("CREATE TABLE ");
        sb15.append(this.table_eWallet);
        sb15.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, WALLET_NAME text, INITIAL_BALANCE text )");
        String sb16 = sb15.toString();
        StringBuilder sb17 = new StringBuilder();
        sb17.append("CREATE TABLE ");
        sb17.append(this.table_creditCard);
        sb17.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, CARD_NAME text, CARD_NUMBER text, INITIAL_BALANCE text )");
        String sb18 = sb17.toString();
        StringBuilder sb19 = new StringBuilder();
        sb19.append("CREATE TABLE ");
        sb19.append(this.table_debitCard);
        sb19.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, CARD_NAME text, CARD_NUMBER text, INITIAL_BALANCE text )");
        String sb20 = sb19.toString();
        StringBuilder sb21 = new StringBuilder();
        sb21.append("CREATE TABLE ");
        sb21.append(this.table_transfer);
        sb21.append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, FROM_ACCOUNT text, FROM_ACCOUNT_ID text, TO_ACCOUNT text, TO_ACCOUNT_ID text, AMOUNT text, DATE text, COMMENT text )");
        String sb22 = sb21.toString();
        db.execSQL(sb2);
        db.execSQL(sb10);
        db.execSQL(sb12);
        db.execSQL(sb14);
        db.execSQL(sb16);
        db.execSQL(sb18);
        db.execSQL(sb20);
        db.execSQL(sb4);
        db.execSQL(sb6);
        db.execSQL(sb8);
        db.execSQL(sb22);
    }

    public boolean add_bankDetails(SQLiteDatabase sQLiteDatabase, AccountDetails accountDetails) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_bankDetails);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO ");
        sb2.append(this.table_bankDetails);
        sb2.append("(BANK_NAME , ACCOUNT_NUMBER , ACCOUNT_TYPE , INITIAL_BALANCE  ) VALUES ('");
        sb2.append(accountDetails.getBankName());
        sb2.append("','");
        sb2.append(accountDetails.getBankAccNo());
        sb2.append("','");
        sb2.append(accountDetails.getAccountType());
        sb2.append("','");
        sb2.append(accountDetails.getInitialBalance());
        sb2.append("')");
        writableDatabase.execSQL(sb2.toString());
        rawQuery.close();
        return true;
    }

    public boolean add_cash(SQLiteDatabase sQLiteDatabase, String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_cash);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() > 0) {
            writableDatabase.delete(this.table_cash, null, null);
            rawQuery.close();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO  ");
        sb2.append(this.table_cash);
        sb2.append("(AMOUNT  ) VALUES ('");
        sb2.append(str);
        sb2.append("')");
        writableDatabase.execSQL(sb2.toString());
        rawQuery.close();
        return true;
    }

    public boolean add_category(SQLiteDatabase sQLiteDatabase, Category category) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_category);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO ");
        sb2.append(this.table_category);
        sb2.append("(CATEGORY  ) VALUES ('");
        sb2.append(category.getCatName());
        sb2.append("')");
        writableDatabase.execSQL(sb2.toString());
        rawQuery.close();
        return true;
    }

    public boolean addIncomeCategory(SQLiteDatabase sQLiteDatabase, Category category) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_categoryIncome);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO ");
        sb2.append(this.table_categoryIncome);
        sb2.append("(CATEGORY_INCOME  ) VALUES ('");
        sb2.append(category.getCatName());
        sb2.append("')");
        writableDatabase.execSQL(sb2.toString());
        rawQuery.close();
        return true;
    }

    public boolean add_categoryList(SQLiteDatabase sQLiteDatabase, ArrayList<Category> arrayList) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_category);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() > 0) {
            writableDatabase.delete(this.table_category, null, null);
        }
        for (int i = 0; i < arrayList.size(); i++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("INSERT INTO ");
            sb2.append(this.table_category);
            sb2.append("(CATEGORY  ) VALUES ('");
            sb2.append(((Category) arrayList.get(i)).getCatName());
            sb2.append("')");
            writableDatabase.execSQL(sb2.toString());
        }
        rawQuery.close();
        return true;
    }

    public boolean add_categoryListIncome(SQLiteDatabase sQLiteDatabase, ArrayList<Category> arrayList) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_categoryIncome);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() > 0) {
            writableDatabase.delete(this.table_categoryIncome, null, null);
        }
        for (int i = 0; i < arrayList.size(); i++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("INSERT INTO ");
            sb2.append(this.table_categoryIncome);
            sb2.append("(CATEGORY_INCOME  ) VALUES ('");
            sb2.append(((Category) arrayList.get(i)).getCatName());
            sb2.append("')");
            writableDatabase.execSQL(sb2.toString());
        }
        rawQuery.close();
        return true;
    }

    public boolean add_eWallet(SQLiteDatabase sQLiteDatabase, AccountDetails accountDetails) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_eWallet);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO ");
        sb2.append(this.table_eWallet);
        sb2.append("(WALLET_NAME , INITIAL_BALANCE  ) VALUES ('");
        sb2.append(accountDetails.geteWalletName());
        sb2.append("','");
        sb2.append(accountDetails.getEwalletBalance());
        sb2.append("')");
        writableDatabase.execSQL(sb2.toString());
        rawQuery.close();
        return true;
    }

    public boolean add_creditCard(SQLiteDatabase sQLiteDatabase, AccountDetails accountDetails) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_creditCard);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO ");
        sb2.append(this.table_creditCard);
        sb2.append("(CARD_NAME , CARD_NUMBER , INITIAL_BALANCE  ) VALUES ('");
        sb2.append(accountDetails.getCreditCardName());
        sb2.append("','");
        sb2.append(accountDetails.getCreditCardNo());
        sb2.append("','");
        sb2.append(accountDetails.getCreditCardBalance());
        sb2.append("')");
        writableDatabase.execSQL(sb2.toString());
        rawQuery.close();
        return true;
    }

    public boolean add_Expenses(SQLiteDatabase sQLiteDatabase, Expense expense) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_expenses);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO ");
        sb2.append(this.table_expenses);
        sb2.append("(AMOUNT , DATE , CATEGORY , CATEGORY_ID , ACCOUNT , ACCOUNT_ID , COMMENT  ) VALUES ('");
        sb2.append(expense.getAmount());
        sb2.append("','");
        sb2.append(expense.getDate());
        sb2.append("','");
        sb2.append(expense.getCategory());
        sb2.append("','");
        sb2.append(expense.getCategoryId());
        sb2.append("','");
        sb2.append(expense.getAccount());
        sb2.append("','");
        sb2.append(expense.getAccountId());
        sb2.append("','");
        sb2.append(expense.getCommnet());
        sb2.append("')");
        writableDatabase.execSQL(sb2.toString());
        rawQuery.close();
        return true;
    }

    public boolean add_income(SQLiteDatabase sQLiteDatabase, Expense expense) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_income);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO ");
        sb2.append(this.table_income);
        sb2.append("(AMOUNT , DATE , CATEGORY , CATEGORY_ID , ACCOUNT , ACCOUNT_ID , COMMENT  ) VALUES ('");
        sb2.append(expense.getAmount());
        sb2.append("','");
        sb2.append(expense.getDate());
        sb2.append("','");
        sb2.append(expense.getCategory());
        sb2.append("','");
        sb2.append(expense.getCategoryId());
        sb2.append("','");
        sb2.append(expense.getAccount());
        sb2.append("','");
        sb2.append(expense.getAccountId());
        sb2.append("','");
        sb2.append(expense.getCommnet());
        sb2.append("')");
        writableDatabase.execSQL(sb2.toString());
        rawQuery.close();
        return true;
    }

    public boolean add_notification(SQLiteDatabase sQLiteDatabase, Notifications notifications) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_notification);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO ");
        sb2.append(this.table_notification);
        sb2.append("(DATE , TIME , TITLE , MESSAGE  ) VALUES ('");
        sb2.append(notifications.getDate());
        sb2.append("','");
        sb2.append(notifications.getTime());
        sb2.append("','");
        sb2.append(notifications.getTitle());
        sb2.append("','");
        sb2.append(notifications.getMessage());
        sb2.append("')");
        writableDatabase.execSQL(sb2.toString());
        rawQuery.close();
        return true;
    }

    public boolean add_transaction(SQLiteDatabase sQLiteDatabase, Transactions transactions) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_transfer);
        Cursor rawQuery = writableDatabase.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO ");
        sb2.append(this.table_transfer);
        sb2.append("(FROM_ACCOUNT , FROM_ACCOUNT_ID , TO_ACCOUNT , TO_ACCOUNT_ID , AMOUNT , DATE , COMMENT  ) VALUES ('");
        sb2.append(transactions.getFromAccount());
        sb2.append("','");
        sb2.append(transactions.getFromAccountId());
        sb2.append("','");
        sb2.append(transactions.getToAccount());
        sb2.append("','");
        sb2.append(transactions.getToAccountId());
        sb2.append("','");
        sb2.append(transactions.getAmount());
        sb2.append("','");
        sb2.append(transactions.getDate());
        sb2.append("','");
        sb2.append(transactions.getComment());
        sb2.append("')");
        writableDatabase.execSQL(sb2.toString());
        rawQuery.close();
        return true;
    }

    public String get_cash(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2 = db;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_cash);
        sb.append(";");
        Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() <= 0) {
            return null;
        }
        String string = rawQuery.getString(0);
        rawQuery.close();
        return string;
    }

    public ArrayList<AccountDetails> get_bank(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2 = db;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_bankDetails);
        sb.append(";");
        Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() <= 0) {
            return null;
        }
        ArrayList<AccountDetails> arrayList = new ArrayList<>();
        for (int i = 0; i < rawQuery.getCount(); i++) {
            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setBankId(rawQuery.getInt(0));
            StringBuilder sb2 = new StringBuilder();
            sb2.append("....");
            sb2.append(rawQuery.getInt(0));
            sb2.append("..db....BankDetails................");
            Log.e("setBankId", sb2.toString());
            accountDetails.setBankName(rawQuery.getString(1));
            StringBuilder sb3 = new StringBuilder();
            sb3.append("....");
            sb3.append(rawQuery.getString(1));
            sb3.append("..db....BankDetails................");
            Log.e("setBankName", sb3.toString());
            accountDetails.setBankAccNo(rawQuery.getString(2));
            StringBuilder sb4 = new StringBuilder();
            sb4.append("....");
            sb4.append(rawQuery.getString(2));
            sb4.append("..db....BankDetails................");
            Log.e("setBankAccNo", sb4.toString());
            accountDetails.setAccountType(rawQuery.getString(3));
            StringBuilder sb5 = new StringBuilder();
            sb5.append("....");
            sb5.append(rawQuery.getString(3));
            sb5.append("..db....BankDetails................");
            Log.e("setAccountType", sb5.toString());
            accountDetails.setInitialBalance(rawQuery.getString(4));
            StringBuilder sb6 = new StringBuilder();
            sb6.append("....");
            sb6.append(rawQuery.getString(4));
            sb6.append("..db....BankDetails................=================================================BANK=======");
            Log.e("setInitialBalance", sb6.toString());
            rawQuery.moveToNext();
            arrayList.add(accountDetails);
        }
        rawQuery.close();
        return arrayList;
    }

    public ArrayList<Category> get_category(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2 = db;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_category);
        sb.append(";");
        Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() <= 0) {
            return null;
        }
        ArrayList<Category> arrayList = new ArrayList<>();
        for (int i = 0; i < rawQuery.getCount(); i++) {
            Category category = new Category();
            category.setCatId(rawQuery.getInt(0));
            category.setCatName(rawQuery.getString(1));
            rawQuery.moveToNext();
            arrayList.add(category);
        }
        rawQuery.close();
        return arrayList;
    }

    public ArrayList<Category> get_categoryIncome(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2 = db;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_categoryIncome);
        sb.append(";");
        Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() <= 0) {
            return null;
        }
        ArrayList<Category> arrayList = new ArrayList<>();
        for (int i = 0; i < rawQuery.getCount(); i++) {
            Category category = new Category();
            category.setCatId(rawQuery.getInt(0));
            category.setCatName(rawQuery.getString(1));
            rawQuery.moveToNext();
            arrayList.add(category);
        }
        rawQuery.close();
        return arrayList;
    }

    public ArrayList<AccountDetails> get_eWaller(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2 = db;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_eWallet);
        sb.append(";");
        Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() <= 0) {
            return null;
        }
        ArrayList<AccountDetails> arrayList = new ArrayList<>();
        for (int i = 0; i < rawQuery.getCount(); i++) {
            AccountDetails accountDetails = new AccountDetails();
            accountDetails.seteWalletId(rawQuery.getInt(0));
            accountDetails.seteWalletName(rawQuery.getString(1));
            accountDetails.setEwalletBalance(rawQuery.getString(2));
            rawQuery.moveToNext();
            arrayList.add(accountDetails);
        }
        rawQuery.close();
        return arrayList;
    }

    public ArrayList<AccountDetails> get_credit(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2 = db;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_creditCard);
        sb.append(";");
        Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() <= 0) {
            return null;
        }
        ArrayList<AccountDetails> arrayList = new ArrayList<>();
        for (int i = 0; i < rawQuery.getCount(); i++) {
            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setCreditId(rawQuery.getInt(0));
            accountDetails.setCreditCardName(rawQuery.getString(1));
            accountDetails.setCreditCardNo(rawQuery.getString(2));
            accountDetails.setCreditCardBalance(rawQuery.getString(3));
            rawQuery.moveToNext();
            arrayList.add(accountDetails);
        }
        rawQuery.close();
        return arrayList;
    }

    public ArrayList<Expense> getExpenses(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2 = db;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_expenses);
        sb.append(";");
        Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() <= 0) {
            return null;
        }
        ArrayList<Expense> arrayList = new ArrayList<>();
        for (int i = 0; i < rawQuery.getCount(); i++) {
            Expense expense = new Expense();
            expense.setId(rawQuery.getInt(0));
            expense.setAmount(rawQuery.getString(1));
            expense.setDate(rawQuery.getString(2));
            expense.setCategory(rawQuery.getString(3));
            expense.setCategoryId(rawQuery.getString(4));
            expense.setAccount(rawQuery.getString(5));
            expense.setAccountId(rawQuery.getString(6));
            expense.setCommnet(rawQuery.getString(7));
            rawQuery.moveToNext();
            arrayList.add(expense);
        }
        rawQuery.close();
        return arrayList;
    }

    public ArrayList<Expense> getIncome(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2 = db;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_income);
        sb.append(";");
        Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() <= 0) {
            return null;
        }
        ArrayList<Expense> arrayList = new ArrayList<>();
        for (int i = 0; i < rawQuery.getCount(); i++) {
            Expense expense = new Expense();
            expense.setId(rawQuery.getInt(0));
            StringBuilder sb2 = new StringBuilder();
            sb2.append("....");
            sb2.append(rawQuery.getInt(0));
            sb2.append("..db....Income...............");
            Log.e("setId", sb2.toString());
            expense.setAmount(rawQuery.getString(1));
            StringBuilder sb3 = new StringBuilder();
            sb3.append("....");
            sb3.append(rawQuery.getString(1));
            sb3.append("..db....Income...............");
            Log.e("setAmount", sb3.toString());
            expense.setDate(rawQuery.getString(2));
            StringBuilder sb4 = new StringBuilder();
            sb4.append("....");
            sb4.append(rawQuery.getString(2));
            sb4.append("..db....Income...............");
            Log.e("setDate", sb4.toString());
            expense.setCategory(rawQuery.getString(3));
            StringBuilder sb5 = new StringBuilder();
            sb5.append("....");
            sb5.append(rawQuery.getString(3));
            sb5.append("..db....Expense...............");
            Log.e("setCategory", sb5.toString());
            expense.setCategoryId(rawQuery.getString(4));
            StringBuilder sb6 = new StringBuilder();
            sb6.append("....");
            sb6.append(rawQuery.getString(4));
            sb6.append("..db....Expense...............");
            Log.e("setCategoryId", sb6.toString());
            expense.setAccount(rawQuery.getString(5));
            StringBuilder sb7 = new StringBuilder();
            sb7.append("....");
            sb7.append(rawQuery.getString(5));
            sb7.append("..db....Expense...............");
            Log.e("setAccount", sb7.toString());
            expense.setAccountId(rawQuery.getString(6));
            StringBuilder sb8 = new StringBuilder();
            sb8.append("....");
            sb8.append(rawQuery.getString(6));
            sb8.append("..db....Expense...............");
            Log.e("setAccountId", sb8.toString());
            expense.setCommnet(rawQuery.getString(7));
            StringBuilder sb9 = new StringBuilder();
            sb9.append("....");
            sb9.append(rawQuery.getString(7));
            sb9.append("..db....Expense.............===========INCOME========================================================..");
            Log.e("setCommnet", sb9.toString());
            rawQuery.moveToNext();
            arrayList.add(expense);
        }
        rawQuery.close();
        return arrayList;
    }

    public ArrayList<Notifications> getNotification(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2 = db;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_notification);
        sb.append(";");
        Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() <= 0) {
            return null;
        }
        ArrayList<Notifications> arrayList = new ArrayList<>();
        for (int i = 0; i < rawQuery.getCount(); i++) {
            Notifications notifications = new Notifications();
            notifications.setId(rawQuery.getInt(0));
            notifications.setDate(rawQuery.getString(1));
            notifications.setTime(rawQuery.getString(2));
            notifications.setTitle(rawQuery.getString(3));
            notifications.setMessage(rawQuery.getString(4));
            rawQuery.moveToNext();
            arrayList.add(notifications);
        }
        rawQuery.close();
        return arrayList;
    }

    public ArrayList<Transactions> getTransaction(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase sQLiteDatabase2 = db;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_transfer);
        sb.append(";");
        Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() <= 0) {
            return null;
        }
        ArrayList<Transactions> arrayList = new ArrayList<>();
        for (int i = 0; i < rawQuery.getCount(); i++) {
            Transactions transactions = new Transactions();
            transactions.setTransactionId(rawQuery.getInt(0));
            transactions.setFromAccount(rawQuery.getString(1));
            transactions.setFromAccountId(rawQuery.getString(2));
            transactions.setToAccount(rawQuery.getString(3));
            transactions.setToAccountId(rawQuery.getString(4));
            transactions.setAmount(rawQuery.getString(5));
            transactions.setDate(rawQuery.getString(6));
            transactions.setComment(rawQuery.getString(7));
            rawQuery.moveToNext();
            arrayList.add(transactions);
        }
        rawQuery.close();
        return arrayList;
    }

    public AccountDetails updateBank(SQLiteDatabase sQLiteDatabase, AccountDetails accountDetails, int i) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BANK_NAME", accountDetails.getBankName());
        contentValues.put("ACCOUNT_NUMBER", accountDetails.getBankAccNo());
        contentValues.put("ACCOUNT_TYPE", accountDetails.getAccountType());
        contentValues.put("INITIAL_BALANCE", accountDetails.getInitialBalance());
        StringBuilder sb = new StringBuilder();
        sb.append("ID =");
        sb.append(i);
        db.update(this.table_bankDetails, contentValues, sb.toString(), new String[0]);
        return accountDetails;
    }

    public AccountDetails updateWallet(SQLiteDatabase sQLiteDatabase, AccountDetails accountDetails, int i) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("WALLET_NAME", accountDetails.geteWalletName());
        contentValues.put("INITIAL_BALANCE", accountDetails.getEwalletBalance());
        StringBuilder sb = new StringBuilder();
        sb.append("ID =");
        sb.append(i);
        db.update(this.table_eWallet, contentValues, sb.toString(), new String[0]);
        return accountDetails;
    }

    public AccountDetails updateCreditCard(SQLiteDatabase sQLiteDatabase, AccountDetails accountDetails, int i) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CARD_NAME", accountDetails.getCreditCardName());
        contentValues.put("CARD_NUMBER", accountDetails.getCreditCardNo());
        contentValues.put("INITIAL_BALANCE", accountDetails.getCreditCardBalance());
        StringBuilder sb = new StringBuilder();
        sb.append("ID =");
        sb.append(i);
        db.update(this.table_creditCard, contentValues, sb.toString(), new String[0]);
        return accountDetails;
    }

    public ArrayList<Expense> getIncomeFilter(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String str4) {
        String str5 = str;
        String str6 = str2;
        String str7 = str3;
        String str8 = str4;
        if (str7.equals("") && str8.equals("")) {
            SQLiteDatabase sQLiteDatabase2 = db;
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(this.table_income);
            sb.append(" WHERE DATE BETWEEN '");
            sb.append(str6);
            sb.append("' AND '");
            sb.append(str5);
            sb.append("'");
            Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
            rawQuery.moveToFirst();
            if (rawQuery.getCount() <= 0) {
                return null;
            }
            ArrayList<Expense> arrayList = new ArrayList<>();
            for (int i = 0; i < rawQuery.getCount(); i++) {
                Expense expense = new Expense();
                expense.setId(rawQuery.getInt(0));
                StringBuilder sb2 = new StringBuilder();
                sb2.append("....");
                sb2.append(rawQuery.getInt(0));
                sb2.append("..db....Income...............");
                Log.e("setId", sb2.toString());
                expense.setAmount(rawQuery.getString(1));
                StringBuilder sb3 = new StringBuilder();
                sb3.append("....");
                sb3.append(rawQuery.getString(1));
                sb3.append("..db....Income...............");
                Log.e("setAmount", sb3.toString());
                expense.setDate(rawQuery.getString(2));
                StringBuilder sb4 = new StringBuilder();
                sb4.append("....");
                sb4.append(rawQuery.getString(2));
                sb4.append("..db....Income...............");
                Log.e("setDate", sb4.toString());
                expense.setCategory(rawQuery.getString(3));
                StringBuilder sb5 = new StringBuilder();
                sb5.append("....");
                sb5.append(rawQuery.getString(3));
                sb5.append("..db....Expense...............");
                Log.e("setCategory", sb5.toString());
                expense.setCategoryId(rawQuery.getString(4));
                StringBuilder sb6 = new StringBuilder();
                sb6.append("....");
                sb6.append(rawQuery.getString(4));
                sb6.append("..db....Expense...............");
                Log.e("setCategoryId", sb6.toString());
                expense.setAccount(rawQuery.getString(5));
                StringBuilder sb7 = new StringBuilder();
                sb7.append("....");
                sb7.append(rawQuery.getString(5));
                sb7.append("..db....Expense...............");
                Log.e("setAccount", sb7.toString());
                expense.setAccountId(rawQuery.getString(6));
                StringBuilder sb8 = new StringBuilder();
                sb8.append("....");
                sb8.append(rawQuery.getString(6));
                sb8.append("..db....Expense...............");
                Log.e("setAccountId", sb8.toString());
                expense.setCommnet(rawQuery.getString(7));
                StringBuilder sb9 = new StringBuilder();
                sb9.append("....");
                sb9.append(rawQuery.getString(7));
                sb9.append("..db....Expense.............===========INCOME========================================================..");
                Log.e("setCommnet", sb9.toString());
                rawQuery.moveToNext();
                arrayList.add(expense);
            }
            rawQuery.close();
            return arrayList;
        } else if (str7.equals("")) {
            SQLiteDatabase sQLiteDatabase3 = db;
            StringBuilder sb10 = new StringBuilder();
            sb10.append("SELECT * FROM ");
            sb10.append(this.table_income);
            sb10.append(" WHERE CATEGORY_ID='");
            sb10.append(str8);
            sb10.append("' AND DATE BETWEEN '");
            sb10.append(str6);
            sb10.append("' AND '");
            sb10.append(str5);
            sb10.append("';");
            Cursor rawQuery2 = sQLiteDatabase3.rawQuery(sb10.toString(), null);
            rawQuery2.moveToFirst();
            if (rawQuery2.getCount() <= 0) {
                return null;
            }
            ArrayList<Expense> arrayList2 = new ArrayList<>();
            for (int i2 = 0; i2 < rawQuery2.getCount(); i2++) {
                Expense expense2 = new Expense();
                expense2.setId(rawQuery2.getInt(0));
                StringBuilder sb11 = new StringBuilder();
                sb11.append("....");
                sb11.append(rawQuery2.getInt(0));
                sb11.append("..db....Income...............");
                Log.e("setId", sb11.toString());
                expense2.setAmount(rawQuery2.getString(1));
                StringBuilder sb12 = new StringBuilder();
                sb12.append("....");
                sb12.append(rawQuery2.getString(1));
                sb12.append("..db....Income...............");
                Log.e("setAmount", sb12.toString());
                expense2.setDate(rawQuery2.getString(2));
                StringBuilder sb13 = new StringBuilder();
                sb13.append("....");
                sb13.append(rawQuery2.getString(2));
                sb13.append("..db....Income...............");
                Log.e("setDate", sb13.toString());
                expense2.setCategory(rawQuery2.getString(3));
                StringBuilder sb14 = new StringBuilder();
                sb14.append("....");
                sb14.append(rawQuery2.getString(3));
                sb14.append("..db....Expense...............");
                Log.e("setCategory", sb14.toString());
                expense2.setCategoryId(rawQuery2.getString(4));
                StringBuilder sb15 = new StringBuilder();
                sb15.append("....");
                sb15.append(rawQuery2.getString(4));
                sb15.append("..db....Expense...............");
                Log.e("setCategoryId", sb15.toString());
                expense2.setAccount(rawQuery2.getString(5));
                StringBuilder sb16 = new StringBuilder();
                sb16.append("....");
                sb16.append(rawQuery2.getString(5));
                sb16.append("..db....Expense...............");
                Log.e("setAccount", sb16.toString());
                expense2.setAccountId(rawQuery2.getString(6));
                StringBuilder sb17 = new StringBuilder();
                sb17.append("....");
                sb17.append(rawQuery2.getString(6));
                sb17.append("..db....Expense...............");
                Log.e("setAccountId", sb17.toString());
                expense2.setCommnet(rawQuery2.getString(7));
                StringBuilder sb18 = new StringBuilder();
                sb18.append("....");
                sb18.append(rawQuery2.getString(7));
                sb18.append("..db....Expense.............===========INCOME========================================================..");
                Log.e("setCommnet", sb18.toString());
                rawQuery2.moveToNext();
                arrayList2.add(expense2);
            }
            rawQuery2.close();
            return arrayList2;
        } else if (str8.equals("")) {
            SQLiteDatabase sQLiteDatabase4 = db;
            StringBuilder sb19 = new StringBuilder();
            sb19.append("SELECT * FROM ");
            sb19.append(this.table_income);
            sb19.append(" WHERE ACCOUNT='");
            sb19.append(str7);
            sb19.append("' AND DATE BETWEEN '");
            sb19.append(str6);
            sb19.append("' AND '");
            sb19.append(str5);
            sb19.append("';");
            Cursor rawQuery3 = sQLiteDatabase4.rawQuery(sb19.toString(), null);
            rawQuery3.moveToFirst();
            if (rawQuery3.getCount() <= 0) {
                return null;
            }
            ArrayList<Expense> arrayList3 = new ArrayList<>();
            for (int i3 = 0; i3 < rawQuery3.getCount(); i3++) {
                Expense expense3 = new Expense();
                expense3.setId(rawQuery3.getInt(0));
                StringBuilder sb20 = new StringBuilder();
                sb20.append("....");
                sb20.append(rawQuery3.getInt(0));
                sb20.append("..db....Income...............");
                Log.e("setId", sb20.toString());
                expense3.setAmount(rawQuery3.getString(1));
                StringBuilder sb21 = new StringBuilder();
                sb21.append("....");
                sb21.append(rawQuery3.getString(1));
                sb21.append("..db....Income...............");
                Log.e("setAmount", sb21.toString());
                expense3.setDate(rawQuery3.getString(2));
                StringBuilder sb22 = new StringBuilder();
                sb22.append("....");
                sb22.append(rawQuery3.getString(2));
                sb22.append("..db....Income...............");
                Log.e("setDate", sb22.toString());
                expense3.setCategory(rawQuery3.getString(3));
                StringBuilder sb23 = new StringBuilder();
                sb23.append("....");
                sb23.append(rawQuery3.getString(3));
                sb23.append("..db....Expense...............");
                Log.e("setCategory", sb23.toString());
                expense3.setCategoryId(rawQuery3.getString(4));
                StringBuilder sb24 = new StringBuilder();
                sb24.append("....");
                sb24.append(rawQuery3.getString(4));
                sb24.append("..db....Expense...............");
                Log.e("setCategoryId", sb24.toString());
                expense3.setAccount(rawQuery3.getString(5));
                StringBuilder sb25 = new StringBuilder();
                sb25.append("....");
                sb25.append(rawQuery3.getString(5));
                sb25.append("..db....Expense...............");
                Log.e("setAccount", sb25.toString());
                expense3.setAccountId(rawQuery3.getString(6));
                StringBuilder sb26 = new StringBuilder();
                sb26.append("....");
                sb26.append(rawQuery3.getString(6));
                sb26.append("..db....Expense...............");
                Log.e("setAccountId", sb26.toString());
                expense3.setCommnet(rawQuery3.getString(7));
                StringBuilder sb27 = new StringBuilder();
                sb27.append("....");
                sb27.append(rawQuery3.getString(7));
                sb27.append("..db....Expense.............===========INCOME========================================================..");
                Log.e("setCommnet", sb27.toString());
                rawQuery3.moveToNext();
                arrayList3.add(expense3);
            }
            rawQuery3.close();
            return arrayList3;
        } else {
            SQLiteDatabase sQLiteDatabase5 = db;
            StringBuilder sb28 = new StringBuilder();
            sb28.append("SELECT * FROM ");
            sb28.append(this.table_income);
            sb28.append(" WHERE ACCOUNT='");
            sb28.append(str7);
            sb28.append("' AND CATEGORY_ID='");
            sb28.append(str8);
            sb28.append("' AND DATE BETWEEN '");
            sb28.append(str6);
            sb28.append("' AND '");
            sb28.append(str5);
            sb28.append("';");
            Cursor rawQuery4 = sQLiteDatabase5.rawQuery(sb28.toString(), null);
            rawQuery4.moveToFirst();
            if (rawQuery4.getCount() <= 0) {
                return null;
            }
            ArrayList<Expense> arrayList4 = new ArrayList<>();
            for (int i4 = 0; i4 < rawQuery4.getCount(); i4++) {
                Expense expense4 = new Expense();
                expense4.setId(rawQuery4.getInt(0));
                StringBuilder sb29 = new StringBuilder();
                sb29.append("....");
                sb29.append(rawQuery4.getInt(0));
                sb29.append("..db....Income...............");
                Log.e("setId", sb29.toString());
                expense4.setAmount(rawQuery4.getString(1));
                StringBuilder sb30 = new StringBuilder();
                sb30.append("....");
                sb30.append(rawQuery4.getString(1));
                sb30.append("..db....Income...............");
                Log.e("setAmount", sb30.toString());
                expense4.setDate(rawQuery4.getString(2));
                StringBuilder sb31 = new StringBuilder();
                sb31.append("....");
                sb31.append(rawQuery4.getString(2));
                sb31.append("..db....Income...............");
                Log.e("setDate", sb31.toString());
                expense4.setCategory(rawQuery4.getString(3));
                StringBuilder sb32 = new StringBuilder();
                sb32.append("....");
                sb32.append(rawQuery4.getString(3));
                sb32.append("..db....Expense...............");
                Log.e("setCategory", sb32.toString());
                expense4.setCategoryId(rawQuery4.getString(4));
                StringBuilder sb33 = new StringBuilder();
                sb33.append("....");
                sb33.append(rawQuery4.getString(4));
                sb33.append("..db....Expense...............");
                Log.e("setCategoryId", sb33.toString());
                expense4.setAccount(rawQuery4.getString(5));
                StringBuilder sb34 = new StringBuilder();
                sb34.append("....");
                sb34.append(rawQuery4.getString(5));
                sb34.append("..db....Expense...............");
                Log.e("setAccount", sb34.toString());
                expense4.setAccountId(rawQuery4.getString(6));
                StringBuilder sb35 = new StringBuilder();
                sb35.append("....");
                sb35.append(rawQuery4.getString(6));
                sb35.append("..db....Expense...............");
                Log.e("setAccountId", sb35.toString());
                expense4.setCommnet(rawQuery4.getString(7));
                StringBuilder sb36 = new StringBuilder();
                sb36.append("....");
                sb36.append(rawQuery4.getString(7));
                sb36.append("..db....Expense.............===========INCOME========================================================..");
                Log.e("setCommnet", sb36.toString());
                rawQuery4.moveToNext();
                arrayList4.add(expense4);
            }
            rawQuery4.close();
            return arrayList4;
        }
    }

    public ArrayList<Expense> getDefaultExpenseData(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        SQLiteDatabase sQLiteDatabase2 = db;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(this.table_expenses);
        sb.append(" WHERE DATE BETWEEN '");
        sb.append(str2);
        sb.append("' AND '");
        sb.append(str);
        sb.append("'");
        Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        if (rawQuery.getCount() <= 0) {
            return null;
        }
        ArrayList<Expense> arrayList = new ArrayList<>();
        for (int i = 0; i < rawQuery.getCount(); i++) {
            Expense expense = new Expense();
            expense.setId(rawQuery.getInt(0));
            expense.setAmount(rawQuery.getString(1));
            expense.setDate(rawQuery.getString(2));
            expense.setCategory(rawQuery.getString(3));
            expense.setCategoryId(rawQuery.getString(4));
            expense.setAccount(rawQuery.getString(5));
            expense.setAccountId(rawQuery.getString(6));
            expense.setCommnet(rawQuery.getString(7));
            rawQuery.moveToNext();
            arrayList.add(expense);
        }
        rawQuery.close();
        return arrayList;
    }

    public ArrayList<Expense> getExpenseFilter(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String str4) {
        String str5 = str;
        String str6 = str2;
        String str7 = str3;
        String str8 = str4;
        if (str7.equals("") && str8.equals("")) {
            SQLiteDatabase sQLiteDatabase2 = db;
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(this.table_expenses);
            sb.append(" WHERE DATE BETWEEN '");
            sb.append(str6);
            sb.append("' AND '");
            sb.append(str5);
            sb.append("'");
            Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
            rawQuery.moveToFirst();
            if (rawQuery.getCount() <= 0) {
                return null;
            }
            ArrayList<Expense> arrayList = new ArrayList<>();
            for (int i = 0; i < rawQuery.getCount(); i++) {
                Expense expense = new Expense();
                expense.setId(rawQuery.getInt(0));
                expense.setAmount(rawQuery.getString(1));
                expense.setDate(rawQuery.getString(2));
                expense.setCategory(rawQuery.getString(3));
                expense.setCategoryId(rawQuery.getString(4));
                expense.setAccount(rawQuery.getString(5));
                expense.setAccountId(rawQuery.getString(6));
                expense.setCommnet(rawQuery.getString(7));
                rawQuery.moveToNext();
                arrayList.add(expense);
            }
            rawQuery.close();
            return arrayList;
        } else if (str8.equals("")) {
            SQLiteDatabase sQLiteDatabase3 = db;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("SELECT * FROM ");
            sb2.append(this.table_expenses);
            sb2.append(" WHERE ACCOUNT='");
            sb2.append(str7);
            sb2.append("' AND DATE BETWEEN '");
            sb2.append(str6);
            sb2.append("' AND '");
            sb2.append(str5);
            sb2.append("';");
            Cursor rawQuery2 = sQLiteDatabase3.rawQuery(sb2.toString(), null);
            rawQuery2.moveToFirst();
            if (rawQuery2.getCount() <= 0) {
                return null;
            }
            ArrayList<Expense> arrayList2 = new ArrayList<>();
            for (int i2 = 0; i2 < rawQuery2.getCount(); i2++) {
                Expense expense2 = new Expense();
                expense2.setId(rawQuery2.getInt(0));
                expense2.setAmount(rawQuery2.getString(1));
                expense2.setDate(rawQuery2.getString(2));
                expense2.setCategory(rawQuery2.getString(3));
                expense2.setCategoryId(rawQuery2.getString(4));
                expense2.setAccount(rawQuery2.getString(5));
                expense2.setAccountId(rawQuery2.getString(6));
                expense2.setCommnet(rawQuery2.getString(7));
                rawQuery2.moveToNext();
                arrayList2.add(expense2);
            }
            rawQuery2.close();
            return arrayList2;
        } else if (str7.equals("")) {
            SQLiteDatabase sQLiteDatabase4 = db;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("SELECT * FROM ");
            sb3.append(this.table_expenses);
            sb3.append(" WHERE CATEGORY_ID='");
            sb3.append(str8);
            sb3.append("' AND DATE BETWEEN '");
            sb3.append(str6);
            sb3.append("' AND '");
            sb3.append(str5);
            sb3.append("';");
            Cursor rawQuery3 = sQLiteDatabase4.rawQuery(sb3.toString(), null);
            rawQuery3.moveToFirst();
            if (rawQuery3.getCount() <= 0) {
                return null;
            }
            ArrayList<Expense> arrayList3 = new ArrayList<>();
            for (int i3 = 0; i3 < rawQuery3.getCount(); i3++) {
                Expense expense3 = new Expense();
                expense3.setId(rawQuery3.getInt(0));
                expense3.setAmount(rawQuery3.getString(1));
                expense3.setDate(rawQuery3.getString(2));
                expense3.setCategory(rawQuery3.getString(3));
                expense3.setCategoryId(rawQuery3.getString(4));
                expense3.setAccount(rawQuery3.getString(5));
                expense3.setAccountId(rawQuery3.getString(6));
                expense3.setCommnet(rawQuery3.getString(7));
                rawQuery3.moveToNext();
                arrayList3.add(expense3);
            }
            rawQuery3.close();
            return arrayList3;
        } else {
            SQLiteDatabase sQLiteDatabase5 = db;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("SELECT * FROM ");
            sb4.append(this.table_expenses);
            sb4.append(" WHERE CATEGORY_ID='");
            sb4.append(str8);
            sb4.append("' AND ACCOUNT='");
            sb4.append(str7);
            sb4.append("' AND DATE BETWEEN '");
            sb4.append(str6);
            sb4.append("' AND '");
            sb4.append(str5);
            sb4.append("';");
            Cursor rawQuery4 = sQLiteDatabase5.rawQuery(sb4.toString(), null);
            rawQuery4.moveToFirst();
            if (rawQuery4.getCount() <= 0) {
                return null;
            }
            ArrayList<Expense> arrayList4 = new ArrayList<>();
            for (int i4 = 0; i4 < rawQuery4.getCount(); i4++) {
                Expense expense4 = new Expense();
                expense4.setId(rawQuery4.getInt(0));
                expense4.setAmount(rawQuery4.getString(1));
                expense4.setDate(rawQuery4.getString(2));
                expense4.setCategory(rawQuery4.getString(3));
                expense4.setCategoryId(rawQuery4.getString(4));
                expense4.setAccount(rawQuery4.getString(5));
                expense4.setAccountId(rawQuery4.getString(6));
                expense4.setCommnet(rawQuery4.getString(7));
                rawQuery4.moveToNext();
                arrayList4.add(expense4);
            }
            rawQuery4.close();
            return arrayList4;
        }
    }

    public ArrayList<Transactions> getTransferFilters(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String str4) {
        String str5 = str;
        String str6 = str2;
        String str7 = str3;
        String str8 = str4;
        if (str7.equals("") && str8.equals("")) {
            SQLiteDatabase sQLiteDatabase2 = db;
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(this.table_transfer);
            sb.append(" WHERE DATE BETWEEN '");
            sb.append(str6);
            sb.append("' AND '");
            sb.append(str5);
            sb.append("'");
            Cursor rawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
            rawQuery.moveToFirst();
            if (rawQuery.getCount() <= 0) {
                return null;
            }
            ArrayList<Transactions> arrayList = new ArrayList<>();
            for (int i = 0; i < rawQuery.getCount(); i++) {
                Transactions transactions = new Transactions();
                transactions.setTransactionId(rawQuery.getInt(0));
                transactions.setFromAccount(rawQuery.getString(1));
                transactions.setFromAccountId(rawQuery.getString(2));
                transactions.setToAccount(rawQuery.getString(3));
                transactions.setToAccountId(rawQuery.getString(4));
                transactions.setAmount(rawQuery.getString(5));
                transactions.setDate(rawQuery.getString(6));
                transactions.setComment(rawQuery.getString(7));
                rawQuery.moveToNext();
                arrayList.add(transactions);
            }
            rawQuery.close();
            return arrayList;
        } else if (str7.equals("")) {
            SQLiteDatabase sQLiteDatabase3 = db;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("SELECT * FROM ");
            sb2.append(this.table_transfer);
            sb2.append(" WHERE TO_ACCOUNT='");
            sb2.append(str8);
            sb2.append("' AND DATE BETWEEN '");
            sb2.append(str6);
            sb2.append("' AND '");
            sb2.append(str5);
            sb2.append("';");
            Cursor rawQuery2 = sQLiteDatabase3.rawQuery(sb2.toString(), null);
            rawQuery2.moveToFirst();
            if (rawQuery2.getCount() <= 0) {
                return null;
            }
            ArrayList<Transactions> arrayList2 = new ArrayList<>();
            for (int i2 = 0; i2 < rawQuery2.getCount(); i2++) {
                Transactions transactions2 = new Transactions();
                transactions2.setTransactionId(rawQuery2.getInt(0));
                transactions2.setFromAccount(rawQuery2.getString(1));
                transactions2.setFromAccountId(rawQuery2.getString(2));
                transactions2.setToAccount(rawQuery2.getString(3));
                transactions2.setToAccountId(rawQuery2.getString(4));
                transactions2.setAmount(rawQuery2.getString(5));
                transactions2.setDate(rawQuery2.getString(6));
                transactions2.setComment(rawQuery2.getString(7));
                rawQuery2.moveToNext();
                arrayList2.add(transactions2);
            }
            rawQuery2.close();
            return arrayList2;
        } else if (str8.equals("")) {
            SQLiteDatabase sQLiteDatabase4 = db;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("SELECT * FROM ");
            sb3.append(this.table_transfer);
            sb3.append(" WHERE FROM_ACCOUNT='");
            sb3.append(str7);
            sb3.append("' AND DATE BETWEEN '");
            sb3.append(str6);
            sb3.append("' AND '");
            sb3.append(str5);
            sb3.append("';");
            Cursor rawQuery3 = sQLiteDatabase4.rawQuery(sb3.toString(), null);
            rawQuery3.moveToFirst();
            if (rawQuery3.getCount() <= 0) {
                return null;
            }
            ArrayList<Transactions> arrayList3 = new ArrayList<>();
            for (int i3 = 0; i3 < rawQuery3.getCount(); i3++) {
                Transactions transactions3 = new Transactions();
                transactions3.setTransactionId(rawQuery3.getInt(0));
                transactions3.setFromAccount(rawQuery3.getString(1));
                transactions3.setFromAccountId(rawQuery3.getString(2));
                transactions3.setToAccount(rawQuery3.getString(3));
                transactions3.setToAccountId(rawQuery3.getString(4));
                transactions3.setAmount(rawQuery3.getString(5));
                transactions3.setDate(rawQuery3.getString(6));
                transactions3.setComment(rawQuery3.getString(7));
                rawQuery3.moveToNext();
                arrayList3.add(transactions3);
            }
            rawQuery3.close();
            return arrayList3;
        } else {
            SQLiteDatabase sQLiteDatabase5 = db;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("SELECT * FROM ");
            sb4.append(this.table_transfer);
            sb4.append(" WHERE FROM_ACCOUNT='");
            sb4.append(str7);
            sb4.append("' AND TO_ACCOUNT='");
            sb4.append(str8);
            sb4.append("' AND DATE BETWEEN '");
            sb4.append(str6);
            sb4.append("' AND '");
            sb4.append(str5);
            sb4.append("';");
            Cursor rawQuery4 = sQLiteDatabase5.rawQuery(sb4.toString(), null);
            rawQuery4.moveToFirst();
            if (rawQuery4.getCount() <= 0) {
                return null;
            }
            ArrayList<Transactions> arrayList4 = new ArrayList<>();
            for (int i4 = 0; i4 < rawQuery4.getCount(); i4++) {
                Transactions transactions4 = new Transactions();
                transactions4.setTransactionId(rawQuery4.getInt(0));
                transactions4.setFromAccount(rawQuery4.getString(1));
                transactions4.setFromAccountId(rawQuery4.getString(2));
                transactions4.setToAccount(rawQuery4.getString(3));
                transactions4.setToAccountId(rawQuery4.getString(4));
                transactions4.setAmount(rawQuery4.getString(5));
                transactions4.setDate(rawQuery4.getString(6));
                transactions4.setComment(rawQuery4.getString(7));
                rawQuery4.moveToNext();
                arrayList4.add(transactions4);
            }
            rawQuery4.close();
            return arrayList4;
        }
    }

    public Expense updateExpense(SQLiteDatabase sQLiteDatabase, Expense expense, int i) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("AMOUNT", expense.getAmount());
        contentValues.put("DATE", expense.getDate());
        contentValues.put("CATEGORY", expense.getCategory());
        contentValues.put("CATEGORY_ID", expense.getCategoryId());
        contentValues.put("ACCOUNT", expense.getAccount());
        contentValues.put("ACCOUNT_ID", expense.getAccountId());
        contentValues.put("COMMENT", expense.getCommnet());
        StringBuilder sb = new StringBuilder();
        sb.append("ID =");
        sb.append(i);
        db.update(this.table_expenses, contentValues, sb.toString(), new String[0]);
        return expense;
    }

    public Expense updateIncome(SQLiteDatabase sQLiteDatabase, Expense expense, int i) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("AMOUNT", expense.getAmount());
        contentValues.put("DATE", expense.getDate());
        contentValues.put("CATEGORY", expense.getCategory());
        contentValues.put("CATEGORY_ID", expense.getCategoryId());
        contentValues.put("ACCOUNT", expense.getAccount());
        contentValues.put("ACCOUNT_ID", expense.getAccountId());
        contentValues.put("COMMENT", expense.getCommnet());
        StringBuilder sb = new StringBuilder();
        sb.append("ID =");
        sb.append(i);
        db.update(this.table_income, contentValues, sb.toString(), new String[0]);
        return expense;
    }

    public Transactions updateTransfers(SQLiteDatabase sQLiteDatabase, Transactions transactions, int i) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FROM_ACCOUNT", transactions.getFromAccount());
        contentValues.put("FROM_ACCOUNT_ID", transactions.getFromAccountId());
        contentValues.put("TO_ACCOUNT", transactions.getToAccount());
        contentValues.put("TO_ACCOUNT_ID", transactions.getToAccountId());
        contentValues.put("AMOUNT", transactions.getAmount());
        contentValues.put("DATE", transactions.getDate());
        contentValues.put("COMMENT", transactions.getComment());
        StringBuilder sb = new StringBuilder();
        sb.append("ID =");
        sb.append(i);
        db.update(this.table_transfer, contentValues, sb.toString(), new String[0]);
        return transactions;
    }

    public boolean deleteBank(SQLiteDatabase sQLiteDatabase, int i) {
        SQLiteDatabase sQLiteDatabase2 = db;
        String str = this.table_bankDetails;
        StringBuilder sb = new StringBuilder();
        sb.append("ID=");
        sb.append(i);
        return sQLiteDatabase2.delete(str, sb.toString(), null) > 0;
    }

    public boolean deleteCreditCard(SQLiteDatabase sQLiteDatabase, int i) {
        SQLiteDatabase sQLiteDatabase2 = db;
        String str = this.table_creditCard;
        StringBuilder sb = new StringBuilder();
        sb.append("ID=");
        sb.append(i);
        return sQLiteDatabase2.delete(str, sb.toString(), null) > 0;
    }

    public boolean deleteWallet(SQLiteDatabase sQLiteDatabase, int i) {
        SQLiteDatabase sQLiteDatabase2 = db;
        String str = this.table_eWallet;
        StringBuilder sb = new StringBuilder();
        sb.append("ID=");
        sb.append(i);
        return sQLiteDatabase2.delete(str, sb.toString(), null) > 0;
    }

    public boolean deleteDebitCard(SQLiteDatabase sQLiteDatabase, int i) {
        SQLiteDatabase sQLiteDatabase2 = db;
        String str = this.table_debitCard;
        StringBuilder sb = new StringBuilder();
        sb.append("ID=");
        sb.append(i);
        return sQLiteDatabase2.delete(str, sb.toString(), null) > 0;
    }

    public boolean deleteNotifications(SQLiteDatabase sQLiteDatabase, int i) {
        SQLiteDatabase sQLiteDatabase2 = db;
        String str = this.table_notification;
        StringBuilder sb = new StringBuilder();
        sb.append("ID=");
        sb.append(i);
        return sQLiteDatabase2.delete(str, sb.toString(), null) > 0;
    }

    public boolean deleteCategoryExpense(SQLiteDatabase sQLiteDatabase, int i) {
        SQLiteDatabase sQLiteDatabase2 = db;
        String str = this.table_category;
        StringBuilder sb = new StringBuilder();
        sb.append("ID=");
        sb.append(i);
        return sQLiteDatabase2.delete(str, sb.toString(), null) > 0;
    }

    public boolean deleteCategoryIncome(SQLiteDatabase sQLiteDatabase, int i) {
        SQLiteDatabase sQLiteDatabase2 = db;
        String str = this.table_categoryIncome;
        StringBuilder sb = new StringBuilder();
        sb.append("ID=");
        sb.append(i);
        return sQLiteDatabase2.delete(str, sb.toString(), null) > 0;
    }

    public boolean deleteExpense(SQLiteDatabase sQLiteDatabase, int i) {
        SQLiteDatabase sQLiteDatabase2 = db;
        String str = this.table_expenses;
        StringBuilder sb = new StringBuilder();
        sb.append("ID=");
        sb.append(i);
        return sQLiteDatabase2.delete(str, sb.toString(), null) > 0;
    }

    public boolean deleteIncome(SQLiteDatabase sQLiteDatabase, int i) {
        SQLiteDatabase sQLiteDatabase2 = db;
        String str = this.table_income;
        StringBuilder sb = new StringBuilder();
        sb.append("ID=");
        sb.append(i);
        return sQLiteDatabase2.delete(str, sb.toString(), null) > 0;
    }

    public boolean deleteTransfer(SQLiteDatabase sQLiteDatabase, int i) {
        SQLiteDatabase sQLiteDatabase2 = db;
        String str = this.table_transfer;
        StringBuilder sb = new StringBuilder();
        sb.append("ID=");
        sb.append(i);
        return sQLiteDatabase2.delete(str, sb.toString(), null) > 0;
    }
}
