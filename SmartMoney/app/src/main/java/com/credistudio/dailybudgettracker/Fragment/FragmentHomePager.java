package com.credistudio.dailybudgettracker.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.credistudio.dailybudgettracker.Adapter.AccountSpinnerAdapter;
import com.credistudio.dailybudgettracker.Adapter.CategorySpinnerAdapter;
import com.credistudio.dailybudgettracker.Classes.AccountDetails;
import com.credistudio.dailybudgettracker.Classes.Category;
import com.credistudio.dailybudgettracker.Classes.Expense;
import com.credistudio.dailybudgettracker.Classes.Transactions;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.Interface.UpdateValues;
import com.credistudio.dailybudgettracker.R;
import com.credistudio.dailybudgettracker.ServiceUtility.NotificationService;
import com.credistudio.dailybudgettracker.ServiceUtility.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import javax.inject.Inject;

public class FragmentHomePager extends Fragment {
    private static final String ARG_POSITION = "position";
    static UpdateValues updateValues;
    ArrayList<AccountDetails> arrayListBank;
    ArrayList<AccountDetails> arrayListCredit;
    ArrayList<AccountDetails> arrayListDebit;
    ArrayList<AccountDetails> arrayListWallet;
    Button btnExpenses_add;
    Button btnIncome_add;
    Button btnTransaction_done;
    ArrayList<Category> categoryList;
    ArrayList<Category> categoryListIncome;
    @Inject
    DataManager dataManager;
    String day1 = "";
    EditText edtExpenses_amount;
    EditText edtExpenses_comment;
    EditText edtIncome_amount;
    EditText edtIncome_comment;
    EditText edtTransaction_amount;
    EditText edtTransaction_comment;
    Expense expense;
    String expenseAccName = "";
    String expenseAccType = "";
    int expenseAccountId = 0;
    int expenseCatId = 0;
    String expenseCatName = "";
    Expense income;
    String incomeAccName = "";
    String incomeAccType = "";
    int incomeAccountItemId = 0;
    int incomeCatId = 0;
    String incomeCatName = "";
    int mDay = 0;
    int mMonth = 0;
    int mYear = 0;
    String month1 = "";
    private int position;
    Spinner spinExpenses_account;
    Spinner spinExpenses_category;
    Spinner spinIncome_account;
    Spinner spinIncome_category;
    ArrayList<AccountDetails> spinListBank;
    ArrayList<AccountDetails> spinListIncomeAcc;
    Spinner spinTransaction_fromAccount;
    Spinner spinTransaction_toAccount;
    int transacFromAccountId = 0;
    String transacFromAccountName = "";
    String transacFromAccountType = "";
    int transacToAccountId = 0;
    String transacToAccountName = "";
    String transacToAccountType = "";
    Transactions transactions;
    TextView tvExpenses_date;
    TextView tvIncome_date;
    TextView tvTransaction_date;
    Util util;

    public static FragmentHomePager newInstance(int i, UpdateValues updateValues2) {
        updateValues = updateValues2;
        FragmentHomePager fragmentHomePager = new FragmentHomePager();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, i);
        fragmentHomePager.setArguments(bundle);
        return fragmentHomePager;
    }

    public void onCreate(Bundle bundle) {
        setRetainInstance(true);
        super.onCreate(bundle);
        this.position = getArguments().getInt(ARG_POSITION);

    }



    @SuppressLint("WrongConstant")
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View view;
        @SuppressLint("WrongConstant") LayoutInflater layoutInflater2 = (LayoutInflater) getActivity().getSystemService("layout_inflater");
        Calendar instance = Calendar.getInstance();
        this.mYear = instance.get(1);
        this.mMonth = instance.get(2);
        this.mDay = instance.get(5);
        if (this.mDay < 10) {
            StringBuilder sb = new StringBuilder();
            sb.append("0");
            sb.append(this.mDay);
            this.day1 = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(this.mDay);
            this.day1 = sb2.toString();
        }
        if (this.mMonth + 1 < 10) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("0");
            sb3.append(this.mMonth + 1);
            this.month1 = sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(this.mMonth + 1);
            this.month1 = sb4.toString();
        }
        ((DaggerApplication) getActivity().getApplication()).getComponent().inject(this);
        this.util = new Util();
        getAccountList();
        if (this.position == 0) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("onCreate view pos ");
            sb5.append(this.position);
            sb5.append("..............");
            Log.e("Homerpager", sb5.toString());
            view = layoutInflater2.inflate(R.layout.item_expense, null);
            this.expense = new Expense();
            this.spinExpenses_category = (Spinner) view.findViewById(R.id.spinExpenses_category);
            this.spinExpenses_account = (Spinner) view.findViewById(R.id.spinExpenses_account);
            this.btnExpenses_add = (Button) view.findViewById(R.id.btnExpenses_add);
            this.edtExpenses_amount = (EditText) view.findViewById(R.id.edtExpenses_amount);
            this.tvExpenses_date = (TextView) view.findViewById(R.id.tvExpenses_date);
            this.edtExpenses_comment = (EditText) view.findViewById(R.id.edtExpenses_comment);
            TextView textView = this.tvExpenses_date;
            StringBuilder sb6 = new StringBuilder();
            sb6.append(this.day1);
            sb6.append("-");
            sb6.append(this.month1);
            sb6.append("-");
            sb6.append(this.mYear);
            textView.setText(sb6.toString());
            try {
                this.categoryList = this.dataManager.getCategory();
                Collections.sort(this.categoryList, new Comparator<Category>() {
                    public int compare(Category category, Category category2) {
                        try {
                            return category.getCatName().compareTo(category2.getCatName());
                        } catch (Exception e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                this.categoryList.add(0, new Category());
                this.spinExpenses_category.setAdapter(new CategorySpinnerAdapter(getActivity(), R.layout.item_account, this.categoryList));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.spinExpenses_category.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    Category category = (Category) adapterView.getItemAtPosition(i);
                    FragmentHomePager.this.expenseCatId = category.getCatId();
                    FragmentHomePager.this.expenseCatName = category.getCatName();
                    StringBuilder sb = new StringBuilder();
                    sb.append("............");
                    sb.append(FragmentHomePager.this.expenseCatId);
                    sb.append("..........");
                    Log.e("item At position", sb.toString());
                }
            });
            this.spinListBank.add(0, new AccountDetails());
            this.spinExpenses_account.setAdapter(new AccountSpinnerAdapter(getActivity(), this.spinListBank, R.layout.item_account));
            this.spinExpenses_account.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    AccountDetails accountDetails = (AccountDetails) adapterView.getItemAtPosition(i);
                    FragmentHomePager.this.expenseAccountId = accountDetails.getAccountId();
                    FragmentHomePager.this.expenseAccName = accountDetails.getAccountName();
                    FragmentHomePager.this.expenseAccType = accountDetails.getType();
                    StringBuilder sb = new StringBuilder();
                    sb.append(".........................");
                    sb.append(FragmentHomePager.this.expenseAccountId);
                    sb.append("...................");
                    Log.e("expenseAccountId", sb.toString());
                }
            });
            this.tvExpenses_date.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentHomePager.this.getActivity(), new OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            String str;
                            String str2;
                            if (i3 < 10) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("0");
                                sb.append(i3);
                                str = sb.toString();
                            } else {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("");
                                sb2.append(i3);
                                str = sb2.toString();
                            }
                            int i4 = i2 + 1;
                            if (i4 < 10) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i4);
                                str2 = sb3.toString();
                            } else {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("");
                                sb4.append(i4);
                                str2 = sb4.toString();
                            }
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(".........................");
                            sb5.append(i3);
                            sb5.append("-");
                            sb5.append(str2);
                            sb5.append("-");
                            sb5.append(i);
                            sb5.append("...................");
                            Log.e("Date pick", sb5.toString());
                            TextView textView = FragmentHomePager.this.tvExpenses_date;
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str);
                            sb6.append("-");
                            sb6.append(str2);
                            sb6.append("-");
                            sb6.append(i);
                            textView.setText(sb6.toString());
                        }
                    }, FragmentHomePager.this.mYear, FragmentHomePager.this.mMonth, FragmentHomePager.this.mDay);
                    datePickerDialog.show();
                }
            });
            this.btnExpenses_add.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (FragmentHomePager.this.edtExpenses_amount.getText().toString().equals("")) {
                        FragmentHomePager.this.edtExpenses_amount.setError("Enter Amount");
                    } else if (FragmentHomePager.this.tvExpenses_date.getText().toString().equals("")) {
                        FragmentHomePager.this.tvExpenses_date.setError("Enter date");
                    } else if (FragmentHomePager.this.spinExpenses_category.getSelectedItemPosition() == 0) {
                        Toast.makeText(FragmentHomePager.this.getActivity(), "Select a category", 0).show();
                    } else if (FragmentHomePager.this.spinExpenses_account.getSelectedItemPosition() == 0) {
                        Toast.makeText(FragmentHomePager.this.getActivity(), "Select an account", 0).show();
                    } else {
                        try {
                            FragmentHomePager.this.util.showDialog(FragmentHomePager.this.getActivity());
                            if (FragmentHomePager.this.expenseAccType.equals("Bank Account")) {
                                for (int i = 0; i < FragmentHomePager.this.arrayListBank.size(); i++) {
                                    if (((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankId() == FragmentHomePager.this.expenseAccountId) {
                                        Double valueOf = Double.valueOf(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getInitialBalance());
                                        Double valueOf2 = Double.valueOf(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                        if (valueOf.doubleValue() < valueOf2.doubleValue()) {
                                            Toast.makeText(FragmentHomePager.this.getActivity(), "you don't have sufficient cash balance", 0).show();
                                            FragmentHomePager.this.util.dismissDialog();
                                        } else {
                                            Double valueOf3 = Double.valueOf(valueOf.doubleValue() - valueOf2.doubleValue());
                                            AccountDetails accountDetails = new AccountDetails();
                                            accountDetails.setBankName(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankName());
                                            accountDetails.setAccountType(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getAccountType());
                                            accountDetails.setBankAccNo(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankAccNo());
                                            accountDetails.setInitialBalance(String.valueOf(valueOf3));
                                            FragmentHomePager.this.dataManager.updateBank(accountDetails, ((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankId());
                                            ((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).setInitialBalance(String.valueOf(valueOf3));
                                            Double valueOf4 = Double.valueOf(0.0d);
                                            Double valueOf5 = Double.valueOf(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                            Intent intent = new Intent(FragmentHomePager.this.getActivity(), NotificationService.class);
                                            intent.putExtra("Type", "Expense");
                                            FragmentHomePager.this.getActivity().startService(intent);
                                            String format = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(FragmentHomePager.this.tvExpenses_date.getText().toString()));
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(".................");
                                            sb.append(format);
                                            sb.append(".............==============...formattedDate............");
                                            Log.e(" formatted date", sb.toString());
                                            FragmentHomePager.this.expense.setAmount(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                            FragmentHomePager.this.expense.setDate(format);
                                            FragmentHomePager.this.expense.setCategory(FragmentHomePager.this.expenseCatName);
                                            FragmentHomePager.this.expense.setCategoryId(String.valueOf(FragmentHomePager.this.expenseCatId));
                                            FragmentHomePager.this.expense.setAccount(FragmentHomePager.this.expenseAccName);
                                            FragmentHomePager.this.expense.setAccountId(String.valueOf(FragmentHomePager.this.expenseAccountId));
                                            FragmentHomePager.this.expense.setCommnet(FragmentHomePager.this.edtExpenses_comment.getText().toString());
                                            FragmentHomePager.this.dataManager.addExpense(FragmentHomePager.this.expense);
                                            FragmentHomePager.this.dataManager.getExpense();
                                            FragmentHomePager.updateValues.updateIncExp(valueOf5, valueOf4, format);
                                            FragmentHomePager.this.edtExpenses_amount.setText("");
                                            FragmentHomePager.this.edtExpenses_comment.setText("");
                                            TextView textView = FragmentHomePager.this.tvExpenses_date;
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(FragmentHomePager.this.day1);
                                            sb2.append("-");
                                            sb2.append(FragmentHomePager.this.month1);
                                            sb2.append("-");
                                            sb2.append(FragmentHomePager.this.mYear);
                                            textView.setText(sb2.toString());
                                            FragmentHomePager.this.spinExpenses_category.setSelection(0);
                                            FragmentHomePager.this.spinExpenses_account.setSelection(0);
                                            FragmentHomePager.this.util.dismissDialog();
                                            Toast.makeText(FragmentHomePager.this.getActivity(), "Your Expense has been added", 1).show();
                                        }
                                    }
                                }
                            } else if (FragmentHomePager.this.expenseAccType.equals("E-wallet")) {
                                for (int i2 = 0; i2 < FragmentHomePager.this.arrayListWallet.size(); i2++) {
                                    if (((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i2)).geteWalletId() == FragmentHomePager.this.expenseAccountId) {
                                        Double valueOf6 = Double.valueOf(((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i2)).getEwalletBalance());
                                        Double valueOf7 = Double.valueOf(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                        if (valueOf6.doubleValue() < valueOf7.doubleValue()) {
                                            Toast.makeText(FragmentHomePager.this.getActivity(), "you don't have sufficient cash balance", 0).show();
                                            FragmentHomePager.this.util.dismissDialog();
                                        } else {
                                            Double valueOf8 = Double.valueOf(valueOf6.doubleValue() - valueOf7.doubleValue());
                                            AccountDetails accountDetails2 = new AccountDetails();
                                            accountDetails2.seteWalletName(((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i2)).geteWalletName());
                                            accountDetails2.setEwalletBalance(String.valueOf(valueOf8));
                                            FragmentHomePager.this.dataManager.updateWallet(accountDetails2, ((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i2)).geteWalletId());
                                            ((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i2)).setEwalletBalance(String.valueOf(valueOf8));
                                            Double valueOf9 = Double.valueOf(0.0d);
                                            Double valueOf10 = Double.valueOf(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                            Intent intent2 = new Intent(FragmentHomePager.this.getActivity(), NotificationService.class);
                                            intent2.putExtra("Type", "Expense");
                                            FragmentHomePager.this.getActivity().startService(intent2);
                                            String format2 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(FragmentHomePager.this.tvExpenses_date.getText().toString()));
                                            StringBuilder sb3 = new StringBuilder();
                                            sb3.append(".................");
                                            sb3.append(format2);
                                            sb3.append(".............==============...formattedDate............");
                                            Log.e(" formatted date", sb3.toString());
                                            FragmentHomePager.this.expense.setAmount(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                            FragmentHomePager.this.expense.setDate(format2);
                                            FragmentHomePager.this.expense.setCategory(FragmentHomePager.this.expenseCatName);
                                            FragmentHomePager.this.expense.setCategoryId(String.valueOf(FragmentHomePager.this.expenseCatId));
                                            FragmentHomePager.this.expense.setAccount(FragmentHomePager.this.expenseAccName);
                                            FragmentHomePager.this.expense.setAccountId(String.valueOf(FragmentHomePager.this.expenseAccountId));
                                            FragmentHomePager.this.expense.setCommnet(FragmentHomePager.this.edtExpenses_comment.getText().toString());
                                            FragmentHomePager.this.dataManager.addExpense(FragmentHomePager.this.expense);
                                            FragmentHomePager.this.dataManager.getExpense();
                                            FragmentHomePager.updateValues.updateIncExp(valueOf10, valueOf9, format2);
                                            FragmentHomePager.this.edtExpenses_amount.setText("");
                                            FragmentHomePager.this.edtExpenses_comment.setText("");
                                            TextView textView2 = FragmentHomePager.this.tvExpenses_date;
                                            StringBuilder sb4 = new StringBuilder();
                                            sb4.append(FragmentHomePager.this.day1);
                                            sb4.append("-");
                                            sb4.append(FragmentHomePager.this.month1);
                                            sb4.append("-");
                                            sb4.append(FragmentHomePager.this.mYear);
                                            textView2.setText(sb4.toString());
                                            FragmentHomePager.this.spinExpenses_category.setSelection(0);
                                            FragmentHomePager.this.spinExpenses_account.setSelection(0);
                                            FragmentHomePager.this.util.dismissDialog();
                                            Toast.makeText(FragmentHomePager.this.getActivity(), "Your Expense has been added", 1).show();
                                        }
                                    }
                                }
                            } else if (FragmentHomePager.this.expenseAccType.equals("Credit Card")) {
                                for (int i3 = 0; i3 < FragmentHomePager.this.arrayListCredit.size(); i3++) {
                                    if (((AccountDetails) FragmentHomePager.this.arrayListCredit.get(i3)).getCreditId() == FragmentHomePager.this.expenseAccountId) {
                                        Double valueOf11 = Double.valueOf(((AccountDetails) FragmentHomePager.this.arrayListCredit.get(i3)).getCreditCardBalance());
                                        Double valueOf12 = Double.valueOf(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                        if (valueOf11.doubleValue() < valueOf12.doubleValue()) {
                                            Toast.makeText(FragmentHomePager.this.getActivity(), "you don't have sufficient Credit card balance", 0).show();
                                            FragmentHomePager.this.util.dismissDialog();
                                        } else {
                                            Double valueOf13 = Double.valueOf(valueOf11.doubleValue() - valueOf12.doubleValue());
                                            AccountDetails accountDetails3 = new AccountDetails();
                                            accountDetails3.setCreditCardName(((AccountDetails) FragmentHomePager.this.arrayListCredit.get(i3)).getCreditCardName());
                                            accountDetails3.setCreditCardNo(((AccountDetails) FragmentHomePager.this.arrayListCredit.get(i3)).getCreditCardNo());
                                            accountDetails3.setCreditCardBalance(String.valueOf(valueOf13));
                                            FragmentHomePager.this.dataManager.updateCredit(accountDetails3, ((AccountDetails) FragmentHomePager.this.arrayListCredit.get(i3)).getCreditId());
                                            ((AccountDetails) FragmentHomePager.this.arrayListCredit.get(i3)).setCreditCardBalance(String.valueOf(valueOf13));
                                            Double valueOf14 = Double.valueOf(0.0d);
                                            Double valueOf15 = Double.valueOf(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                            Intent intent3 = new Intent(FragmentHomePager.this.getActivity(), NotificationService.class);
                                            intent3.putExtra("Type", "Expense");
                                            FragmentHomePager.this.getActivity().startService(intent3);
                                            String format3 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(FragmentHomePager.this.tvExpenses_date.getText().toString()));
                                            StringBuilder sb5 = new StringBuilder();
                                            sb5.append(".................");
                                            sb5.append(format3);
                                            sb5.append(".............==============...formattedDate............");
                                            Log.e(" formatted date", sb5.toString());
                                            FragmentHomePager.this.expense.setAmount(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                            FragmentHomePager.this.expense.setDate(format3);
                                            FragmentHomePager.this.expense.setCategory(FragmentHomePager.this.expenseCatName);
                                            FragmentHomePager.this.expense.setCategoryId(String.valueOf(FragmentHomePager.this.expenseCatId));
                                            FragmentHomePager.this.expense.setAccount(FragmentHomePager.this.expenseAccName);
                                            FragmentHomePager.this.expense.setAccountId(String.valueOf(FragmentHomePager.this.expenseAccountId));
                                            FragmentHomePager.this.expense.setCommnet(FragmentHomePager.this.edtExpenses_comment.getText().toString());
                                            FragmentHomePager.this.dataManager.addExpense(FragmentHomePager.this.expense);
                                            FragmentHomePager.this.dataManager.getExpense();
                                            FragmentHomePager.updateValues.updateIncExp(valueOf15, valueOf14, format3);
                                            FragmentHomePager.this.edtExpenses_amount.setText("");
                                            FragmentHomePager.this.edtExpenses_comment.setText("");
                                            TextView textView3 = FragmentHomePager.this.tvExpenses_date;
                                            StringBuilder sb6 = new StringBuilder();
                                            sb6.append(FragmentHomePager.this.day1);
                                            sb6.append("-");
                                            sb6.append(FragmentHomePager.this.month1);
                                            sb6.append("-");
                                            sb6.append(FragmentHomePager.this.mYear);
                                            textView3.setText(sb6.toString());
                                            FragmentHomePager.this.spinExpenses_category.setSelection(0);
                                            FragmentHomePager.this.spinExpenses_account.setSelection(0);
                                            FragmentHomePager.this.util.dismissDialog();
                                            Toast.makeText(FragmentHomePager.this.getActivity(), "Your Expense has been added", 1).show();
                                        }
                                    }
                                }
                            } else if (FragmentHomePager.this.expenseAccType.equals("Cash")) {
                                String cash = FragmentHomePager.this.dataManager.getCash();
                                if (cash == null) {
                                    Toast.makeText(FragmentHomePager.this.getActivity(), "you don't have sufficient cash balance", 0).show();
                                }
                                Double valueOf16 = Double.valueOf(cash);
                                Double valueOf17 = Double.valueOf(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                if (valueOf16.doubleValue() < valueOf17.doubleValue()) {
                                    FragmentHomePager.this.util.dismissDialog();
                                    Toast.makeText(FragmentHomePager.this.getActivity(), "you don't have sufficient cash balance", 0).show();
                                    return;
                                }
                                Double valueOf18 = Double.valueOf(valueOf16.doubleValue() - valueOf17.doubleValue());
                                new AccountDetails();
                                FragmentHomePager.this.dataManager.addCash(String.valueOf(valueOf18));
                                Double valueOf19 = Double.valueOf(0.0d);
                                Double valueOf20 = Double.valueOf(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                Intent intent4 = new Intent(FragmentHomePager.this.getActivity(), NotificationService.class);
                                intent4.putExtra("Type", "Expense");
                                FragmentHomePager.this.getActivity().startService(intent4);
                                String format4 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(FragmentHomePager.this.tvExpenses_date.getText().toString()));
                                StringBuilder sb7 = new StringBuilder();
                                sb7.append(".................");
                                sb7.append(format4);
                                sb7.append(".............==============...formattedDate............");
                                Log.e(" formatted date", sb7.toString());
                                FragmentHomePager.this.expense.setAmount(FragmentHomePager.this.edtExpenses_amount.getText().toString());
                                FragmentHomePager.this.expense.setDate(format4);
                                FragmentHomePager.this.expense.setCategory(FragmentHomePager.this.expenseCatName);
                                FragmentHomePager.this.expense.setCategoryId(String.valueOf(FragmentHomePager.this.expenseCatId));
                                FragmentHomePager.this.expense.setAccount(FragmentHomePager.this.expenseAccName);
                                FragmentHomePager.this.expense.setAccountId(String.valueOf(FragmentHomePager.this.expenseAccountId));
                                FragmentHomePager.this.expense.setCommnet(FragmentHomePager.this.edtExpenses_comment.getText().toString());
                                FragmentHomePager.this.dataManager.addExpense(FragmentHomePager.this.expense);
                                FragmentHomePager.this.dataManager.getExpense();
                                FragmentHomePager.updateValues.updateIncExp(valueOf20, valueOf19, format4);
                                FragmentHomePager.this.edtExpenses_amount.setText("");
                                FragmentHomePager.this.edtExpenses_comment.setText("");
                                TextView textView4 = FragmentHomePager.this.tvExpenses_date;
                                StringBuilder sb8 = new StringBuilder();
                                sb8.append(FragmentHomePager.this.day1);
                                sb8.append("-");
                                sb8.append(FragmentHomePager.this.month1);
                                sb8.append("-");
                                sb8.append(FragmentHomePager.this.mYear);
                                textView4.setText(sb8.toString());
                                FragmentHomePager.this.spinExpenses_category.setSelection(0);
                                FragmentHomePager.this.spinExpenses_account.setSelection(0);
                                FragmentHomePager.this.util.dismissDialog();
                                Toast.makeText(FragmentHomePager.this.getActivity(), "Your Expense has been added", 1).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            FragmentHomePager.this.util.dismissDialog();
                        }
                    }
                }
            });
        } else {
            view = null;
        }
        if (this.position == 1) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("onCreate view pos ");
            sb7.append(this.position);
            sb7.append("..............");
            Log.e("Home Page", sb7.toString());
            view = layoutInflater2.inflate(R.layout.item_income, null);
            this.btnIncome_add = (Button) view.findViewById(R.id.btnIncome_add);
            this.edtIncome_amount = (EditText) view.findViewById(R.id.edtIncome_amount);
            this.tvIncome_date = (TextView) view.findViewById(R.id.tvIncome_date);
            this.spinIncome_account = (Spinner) view.findViewById(R.id.spinIncome_account);
            this.spinIncome_category = (Spinner) view.findViewById(R.id.spinIncome_category);
            this.edtIncome_comment = (EditText) view.findViewById(R.id.edtIncome_comment);
            this.income = new Expense();
            TextView textView2 = this.tvIncome_date;
            StringBuilder sb8 = new StringBuilder();
            sb8.append(this.day1);
            sb8.append("-");
            sb8.append(this.month1);
            sb8.append("-");
            sb8.append(this.mYear);
            textView2.setText(sb8.toString());
            this.spinListIncomeAcc.add(0, new AccountDetails());
            this.spinIncome_account.setAdapter(new AccountSpinnerAdapter(getActivity(), this.spinListIncomeAcc, R.layout.item_account));
            this.spinIncome_account.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    AccountDetails accountDetails = (AccountDetails) adapterView.getItemAtPosition(i);
                    FragmentHomePager.this.incomeAccountItemId = accountDetails.getAccountId();
                    FragmentHomePager.this.incomeAccName = accountDetails.getAccountName();
                    FragmentHomePager.this.incomeAccType = accountDetails.getType();
                    StringBuilder sb = new StringBuilder();
                    sb.append(".........................");
                    sb.append(FragmentHomePager.this.incomeAccountItemId);
                    sb.append("...................");
                    Log.e("incomeCatItemId", sb.toString());
                }
            });
            try {
                this.categoryListIncome = this.dataManager.getCategoryIncome();
                Collections.sort(this.categoryListIncome, new Comparator<Category>() {
                    public int compare(Category category, Category category2) {
                        try {
                            return category.getCatName().compareTo(category2.getCatName());
                        } catch (Exception e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                this.categoryListIncome.add(0, new Category());
                this.spinIncome_category.setAdapter(new CategorySpinnerAdapter(getActivity(), R.layout.item_account, this.categoryListIncome));
                this.spinIncome_category.setOnItemSelectedListener(new OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        Category category = (Category) adapterView.getItemAtPosition(i);
                        FragmentHomePager.this.incomeCatId = category.getCatId();
                        FragmentHomePager.this.incomeCatName = category.getCatName();
                        StringBuilder sb = new StringBuilder();
                        sb.append("............");
                        sb.append(FragmentHomePager.this.incomeCatId);
                        sb.append(".....<.Category.>........");
                        sb.append(FragmentHomePager.this.incomeCatName);
                        sb.append(".......");
                        Log.e("item At position", sb.toString());
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.tvIncome_date.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentHomePager.this.getActivity(), new OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            String str;
                            String str2;
                            if (i3 < 10) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("0");
                                sb.append(i3);
                                str = sb.toString();
                            } else {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("");
                                sb2.append(i3);
                                str = sb2.toString();
                            }
                            int i4 = i2 + 1;
                            if (i4 < 10) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i4);
                                str2 = sb3.toString();
                            } else {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("");
                                sb4.append(i4);
                                str2 = sb4.toString();
                            }
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(".........................");
                            sb5.append(str);
                            sb5.append("-");
                            sb5.append(str2);
                            sb5.append("-");
                            sb5.append(i);
                            sb5.append("...................");
                            Log.e("Date pick", sb5.toString());
                            TextView textView = FragmentHomePager.this.tvIncome_date;
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str);
                            sb6.append("-");
                            sb6.append(str2);
                            sb6.append("-");
                            sb6.append(i);
                            textView.setText(sb6.toString());
                        }
                    }, FragmentHomePager.this.mYear, FragmentHomePager.this.mMonth, FragmentHomePager.this.mDay);
                    datePickerDialog.show();
                }
            });
            this.btnIncome_add.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (FragmentHomePager.this.edtIncome_amount.getText().toString().equals("")) {
                        FragmentHomePager.this.edtIncome_amount.setError("Enter Amount");
                    } else if (FragmentHomePager.this.tvIncome_date.getText().toString().equals("")) {
                        FragmentHomePager.this.tvIncome_date.setError("Enter Date");
                    } else if (FragmentHomePager.this.spinIncome_category.getSelectedItemPosition() == 0) {
                        Toast.makeText(FragmentHomePager.this.getActivity(), "Select a category", 1).show();
                    } else if (FragmentHomePager.this.spinIncome_account.getSelectedItemPosition() == 0) {
                        Toast.makeText(FragmentHomePager.this.getActivity(), "Select a account", 1).show();
                    } else {
                        try {
                            FragmentHomePager.this.util.showDialog(FragmentHomePager.this.getActivity());
                            if (FragmentHomePager.this.incomeAccType.equals("Bank Account")) {
                                for (int i = 0; i < FragmentHomePager.this.arrayListBank.size(); i++) {
                                    if (((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankId() == FragmentHomePager.this.incomeAccountItemId) {
                                        Double valueOf = Double.valueOf(Double.valueOf(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getInitialBalance()).doubleValue() + Double.valueOf(FragmentHomePager.this.edtIncome_amount.getText().toString()).doubleValue());
                                        AccountDetails accountDetails = new AccountDetails();
                                        accountDetails.setBankName(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankName());
                                        accountDetails.setAccountType(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getAccountType());
                                        accountDetails.setBankAccNo(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankAccNo());
                                        accountDetails.setInitialBalance(String.valueOf(valueOf));
                                        FragmentHomePager.this.dataManager.updateBank(accountDetails, ((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankId());
                                        ((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).setInitialBalance(String.valueOf(valueOf));
                                    }
                                }

                            } else if (FragmentHomePager.this.incomeAccType.equals("E-wallet")) {
                                for (int i2 = 0; i2 < FragmentHomePager.this.arrayListWallet.size(); i2++) {
                                    if (((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i2)).geteWalletId() == FragmentHomePager.this.incomeAccountItemId) {
                                        Double valueOf2 = Double.valueOf(Double.valueOf(((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i2)).getEwalletBalance()).doubleValue() + Double.valueOf(FragmentHomePager.this.edtIncome_amount.getText().toString()).doubleValue());
                                        AccountDetails accountDetails2 = new AccountDetails();
                                        accountDetails2.seteWalletName(((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i2)).geteWalletName());
                                        accountDetails2.setEwalletBalance(String.valueOf(valueOf2));
                                        FragmentHomePager.this.dataManager.updateWallet(accountDetails2, ((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i2)).geteWalletId());
                                        ((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i2)).setEwalletBalance(String.valueOf(valueOf2));
                                    }
                                }

                            } else if (FragmentHomePager.this.incomeAccType.equals("Cash")) {
                                String cash = FragmentHomePager.this.dataManager.getCash();
                                if (cash == null) {
                                    cash = "00";
                                }
                                Double valueOf3 = Double.valueOf(cash);
                                Double valueOf4 = Double.valueOf(FragmentHomePager.this.edtIncome_amount.getText().toString());
                                Double valueOf5 = Double.valueOf(valueOf3.doubleValue() + valueOf4.doubleValue());
                                StringBuilder sb = new StringBuilder();
                                sb.append("............");
                                sb.append(valueOf3);
                                sb.append(".......==========================================amount1========================================================");
                                Log.e("amount1", sb.toString());
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("............");
                                sb2.append(valueOf4);
                                sb2.append(".......==========================================amount2========================================================");
                                Log.e("amount2", sb2.toString());
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("............");
                                sb3.append(valueOf5);
                                sb3.append(".......==========================================finalBalance=========================================");
                                Log.e("finalBalance", sb3.toString());
                                FragmentHomePager.this.dataManager.addCash(String.valueOf(valueOf5));
                            }
                            Double valueOf6 = Double.valueOf(FragmentHomePager.this.edtIncome_amount.getText().toString());
                            Double valueOf7 = Double.valueOf(0.0d);
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("..............edt Date.........");
                            sb4.append(FragmentHomePager.this.tvIncome_date);
                            sb4.append("..............");
                            Log.e("date", sb4.toString());
                            String format = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(FragmentHomePager.this.tvIncome_date.getText().toString()));
                            FragmentHomePager.this.income.setAmount(FragmentHomePager.this.edtIncome_amount.getText().toString());
                            FragmentHomePager.this.income.setDate(format);
                            FragmentHomePager.this.income.setCommnet(FragmentHomePager.this.edtIncome_comment.getText().toString());
                            FragmentHomePager.this.income.setCategory(FragmentHomePager.this.incomeCatName);
                            FragmentHomePager.this.income.setCategoryId(String.valueOf(FragmentHomePager.this.incomeCatId));
                            FragmentHomePager.this.income.setAccount(FragmentHomePager.this.incomeAccName);
                            FragmentHomePager.this.income.setAccountId(String.valueOf(FragmentHomePager.this.incomeAccountItemId));
                            FragmentHomePager.this.dataManager.addIncome(FragmentHomePager.this.income);
                            FragmentHomePager.this.dataManager.getIncome();
                            FragmentHomePager.updateValues.updateIncExp(valueOf7, valueOf6, format);
                            Intent intent = new Intent(FragmentHomePager.this.getActivity(), NotificationService.class);
                            intent.putExtra("Type", "Income");
                            FragmentHomePager.this.getActivity().startService(intent);
                            FragmentHomePager.this.edtIncome_amount.setText("");
                            FragmentHomePager.this.edtIncome_comment.setText("");
                            TextView textView = FragmentHomePager.this.tvIncome_date;
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(FragmentHomePager.this.day1);
                            sb5.append("-");
                            sb5.append(FragmentHomePager.this.month1);
                            sb5.append("-");
                            sb5.append(FragmentHomePager.this.mYear);
                            textView.setText(sb5.toString());
                            FragmentHomePager.this.spinIncome_account.setSelection(0);
                            FragmentHomePager.this.spinIncome_category.setSelection(0);
                            FragmentHomePager.this.util.dismissDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                            FragmentHomePager.this.util.dismissDialog();
                        }
                        Toast.makeText(FragmentHomePager.this.getActivity(), "Your Income has been added", 1).show();
                    }
                }
            });
        }
        if (this.position != 2) {
            return view;
        }
        View inflate = layoutInflater2.inflate(R.layout.item_transfer, null);
        this.spinTransaction_fromAccount = (Spinner) inflate.findViewById(R.id.spinTransaction_fromAccount);
        this.spinTransaction_toAccount = (Spinner) inflate.findViewById(R.id.spinTransaction_toAccount);
        this.btnTransaction_done = (Button) inflate.findViewById(R.id.btnTransaction_done);
        this.edtTransaction_amount = (EditText) inflate.findViewById(R.id.edtTransaction_amount);
        this.edtTransaction_comment = (EditText) inflate.findViewById(R.id.edtTransaction_comment);
        this.tvTransaction_date = (TextView) inflate.findViewById(R.id.tvTransaction_date);
        TextView textView3 = this.tvTransaction_date;
        StringBuilder sb9 = new StringBuilder();
        sb9.append(this.day1);
        sb9.append("-");
        sb9.append(this.month1);
        sb9.append("-");
        sb9.append(this.mYear);
        textView3.setText(sb9.toString());
        this.spinListIncomeAcc.add(0, new AccountDetails());
        AccountSpinnerAdapter accountSpinnerAdapter = new AccountSpinnerAdapter(getActivity(), this.spinListIncomeAcc, R.layout.item_account);
        this.spinTransaction_fromAccount.setAdapter(accountSpinnerAdapter);
        this.spinTransaction_toAccount.setAdapter(accountSpinnerAdapter);
        this.spinTransaction_fromAccount.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                AccountDetails accountDetails = (AccountDetails) adapterView.getItemAtPosition(i);
                FragmentHomePager.this.transacFromAccountId = accountDetails.getAccountId();
                FragmentHomePager.this.transacFromAccountName = accountDetails.getAccountName();
                FragmentHomePager.this.transacFromAccountType = accountDetails.getType();
                StringBuilder sb = new StringBuilder();
                sb.append(".........................");
                sb.append(FragmentHomePager.this.transacFromAccountId);
                sb.append("...................");
                Log.e("transacFromAccountId", sb.toString());
            }
        });
        this.spinTransaction_toAccount.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                AccountDetails accountDetails = (AccountDetails) adapterView.getItemAtPosition(i);
                FragmentHomePager.this.transacToAccountId = accountDetails.getAccountId();
                FragmentHomePager.this.transacToAccountName = accountDetails.getAccountName();
                FragmentHomePager.this.transacToAccountType = accountDetails.getType();
                StringBuilder sb = new StringBuilder();
                sb.append(".........................");
                sb.append(FragmentHomePager.this.transacToAccountId);
                sb.append("...................");
                Log.e("transacToAccountId", sb.toString());
            }
        });
        this.tvTransaction_date.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentHomePager.this.getActivity(), new OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        String str;
                        String str2;
                        if (i3 < 10) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("0");
                            sb.append(i3);
                            str = sb.toString();
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("");
                            sb2.append(i3);
                            str = sb2.toString();
                        }
                        int i4 = i2 + 1;
                        if (i4 < 10) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("0");
                            sb3.append(i4);
                            str2 = sb3.toString();
                        } else {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("");
                            sb4.append(i4);
                            str2 = sb4.toString();
                        }
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(".........................");
                        sb5.append(str);
                        sb5.append("-");
                        sb5.append(str2);
                        sb5.append("-");
                        sb5.append(i);
                        sb5.append("...................");
                        Log.e("Date pick", sb5.toString());
                        TextView textView = FragmentHomePager.this.tvTransaction_date;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(str);
                        sb6.append("-");
                        sb6.append(str2);
                        sb6.append("-");
                        sb6.append(i);
                        textView.setText(sb6.toString());
                    }
                }, FragmentHomePager.this.mYear, FragmentHomePager.this.mMonth, FragmentHomePager.this.mDay);
                datePickerDialog.show();
            }
        });
        this.btnTransaction_done.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (FragmentHomePager.this.edtTransaction_amount.equals("")) {
                    FragmentHomePager.this.edtTransaction_amount.setError("Enter some Amount");
                    return;
                }
                int i = 0;
                if (FragmentHomePager.this.spinTransaction_fromAccount.getSelectedItemPosition() == 0) {
                    Toast.makeText(FragmentHomePager.this.getActivity(), "Select an valid account", 0).show();
                } else if (FragmentHomePager.this.spinTransaction_toAccount.getSelectedItemPosition() == 0) {
                    Toast.makeText(FragmentHomePager.this.getActivity(), "Select an valid account", 0).show();
                } else if (!FragmentHomePager.this.transacFromAccountType.equals(FragmentHomePager.this.transacToAccountType) || FragmentHomePager.this.transacFromAccountId != FragmentHomePager.this.transacToAccountId) {
                    try {
                        FragmentHomePager.this.util.showDialog(FragmentHomePager.this.getActivity());
                        if (FragmentHomePager.this.transacFromAccountType.equals("Bank Account")) {
                            for (int i2 = 0; i2 < FragmentHomePager.this.arrayListBank.size(); i2++) {
                                if (((AccountDetails) FragmentHomePager.this.arrayListBank.get(i2)).getBankId() == FragmentHomePager.this.transacFromAccountId) {
                                    Double valueOf = Double.valueOf(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i2)).getInitialBalance());
                                    Double valueOf2 = Double.valueOf(FragmentHomePager.this.edtTransaction_amount.getText().toString());
                                    if (valueOf.doubleValue() > valueOf2.doubleValue()) {
                                        Double valueOf3 = Double.valueOf(valueOf.doubleValue() - valueOf2.doubleValue());
                                        AccountDetails accountDetails = new AccountDetails();
                                        accountDetails.setBankName(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i2)).getBankName());
                                        accountDetails.setAccountType(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i2)).getAccountType());
                                        accountDetails.setBankAccNo(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i2)).getBankAccNo());
                                        accountDetails.setInitialBalance(String.valueOf(valueOf3));
                                        FragmentHomePager.this.dataManager.updateBank(accountDetails, ((AccountDetails) FragmentHomePager.this.arrayListBank.get(i2)).getBankId());
                                        ((AccountDetails) FragmentHomePager.this.arrayListBank.get(i2)).setInitialBalance(String.valueOf(valueOf3));
                                        String format = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(FragmentHomePager.this.tvTransaction_date.getText().toString()));
                                        FragmentHomePager.this.transactions = new Transactions();
                                        FragmentHomePager.this.transactions.setAmount(FragmentHomePager.this.edtTransaction_amount.getText().toString());
                                        FragmentHomePager.this.transactions.setFromAccount(FragmentHomePager.this.transacFromAccountName);
                                        FragmentHomePager.this.transactions.setFromAccountId(String.valueOf(FragmentHomePager.this.transacFromAccountId));
                                        FragmentHomePager.this.transactions.setToAccountId(String.valueOf(FragmentHomePager.this.transacToAccountId));
                                        FragmentHomePager.this.transactions.setToAccount(FragmentHomePager.this.transacToAccountName);
                                        FragmentHomePager.this.transactions.setDate(format);
                                        FragmentHomePager.this.transactions.setComment(FragmentHomePager.this.edtTransaction_comment.getText().toString());
                                        FragmentHomePager.this.dataManager.addTransaction(FragmentHomePager.this.transactions);
                                        FragmentHomePager.this.dataManager.getTransaction();
                                        FragmentHomePager.this.spinTransaction_fromAccount.setSelection(0);
                                        FragmentHomePager.this.spinTransaction_toAccount.setSelection(0);
                                        FragmentHomePager.this.edtTransaction_amount.setText("");
                                        FragmentHomePager.this.edtTransaction_comment.setText("");
                                        TextView textView = FragmentHomePager.this.tvTransaction_date;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(FragmentHomePager.this.day1);
                                        sb.append("-");
                                        sb.append(FragmentHomePager.this.month1);
                                        sb.append("-");
                                        sb.append(FragmentHomePager.this.mYear);
                                        textView.setText(sb.toString());
                                    } else {
                                        Toast.makeText(FragmentHomePager.this.getActivity(), "you don't have sufficient cash balance", 0).show();
                                    }
                                }
                            }
                        } else if (FragmentHomePager.this.transacFromAccountType.equals("E-wallet")) {
                            for (int i3 = 0; i3 < FragmentHomePager.this.arrayListWallet.size(); i3++) {
                                if (((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i3)).geteWalletId() == FragmentHomePager.this.transacFromAccountId) {
                                    Double valueOf4 = Double.valueOf(((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i3)).getEwalletBalance());
                                    Double valueOf5 = Double.valueOf(FragmentHomePager.this.edtTransaction_amount.getText().toString());
                                    if (valueOf4.doubleValue() > valueOf5.doubleValue()) {
                                        Double valueOf6 = Double.valueOf(valueOf4.doubleValue() - valueOf5.doubleValue());
                                        AccountDetails accountDetails2 = new AccountDetails();
                                        accountDetails2.seteWalletName(((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i3)).geteWalletName());
                                        accountDetails2.setEwalletBalance(String.valueOf(valueOf6));
                                        FragmentHomePager.this.dataManager.updateWallet(accountDetails2, ((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i3)).geteWalletId());
                                        ((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i3)).setEwalletBalance(String.valueOf(valueOf6));
                                        String format2 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(FragmentHomePager.this.tvTransaction_date.getText().toString()));
                                        FragmentHomePager.this.transactions = new Transactions();
                                        FragmentHomePager.this.transactions.setAmount(FragmentHomePager.this.edtTransaction_amount.getText().toString());
                                        FragmentHomePager.this.transactions.setFromAccount(FragmentHomePager.this.transacFromAccountName);
                                        FragmentHomePager.this.transactions.setFromAccountId(String.valueOf(FragmentHomePager.this.transacFromAccountId));
                                        FragmentHomePager.this.transactions.setToAccountId(String.valueOf(FragmentHomePager.this.transacToAccountId));
                                        FragmentHomePager.this.transactions.setToAccount(FragmentHomePager.this.transacToAccountName);
                                        FragmentHomePager.this.transactions.setDate(format2);
                                        FragmentHomePager.this.transactions.setComment(FragmentHomePager.this.edtTransaction_comment.getText().toString());
                                        FragmentHomePager.this.dataManager.addTransaction(FragmentHomePager.this.transactions);
                                        FragmentHomePager.this.dataManager.getTransaction();
                                        FragmentHomePager.this.spinTransaction_fromAccount.setSelection(0);
                                        FragmentHomePager.this.spinTransaction_toAccount.setSelection(0);
                                        FragmentHomePager.this.edtTransaction_amount.setText("");
                                        FragmentHomePager.this.edtTransaction_comment.setText("");
                                        TextView textView2 = FragmentHomePager.this.tvTransaction_date;
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(FragmentHomePager.this.day1);
                                        sb2.append("-");
                                        sb2.append(FragmentHomePager.this.month1);
                                        sb2.append("-");
                                        sb2.append(FragmentHomePager.this.mYear);
                                        textView2.setText(sb2.toString());
                                    } else {
                                        Toast.makeText(FragmentHomePager.this.getActivity(), "you don't have sufficient cash balance", 0).show();
                                    }
                                }
                            }
                        } else if (FragmentHomePager.this.transacFromAccountType.equals("Cash")) {
                            String cash = FragmentHomePager.this.dataManager.getCash();
                            if (cash == null) {
                                cash = "00";
                            }
                            Double valueOf7 = Double.valueOf(cash);
                            Double valueOf8 = Double.valueOf(FragmentHomePager.this.edtTransaction_amount.getText().toString());
                            if (valueOf7.doubleValue() > valueOf8.doubleValue()) {
                                FragmentHomePager.this.dataManager.addCash(String.valueOf(Double.valueOf(valueOf7.doubleValue() - valueOf8.doubleValue())));
                                String format3 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(FragmentHomePager.this.tvTransaction_date.getText().toString()));
                                FragmentHomePager.this.transactions = new Transactions();
                                FragmentHomePager.this.transactions.setAmount(FragmentHomePager.this.edtTransaction_amount.getText().toString());
                                FragmentHomePager.this.transactions.setFromAccount(FragmentHomePager.this.transacFromAccountName);
                                FragmentHomePager.this.transactions.setFromAccountId(String.valueOf(FragmentHomePager.this.transacFromAccountId));
                                FragmentHomePager.this.transactions.setToAccountId(String.valueOf(FragmentHomePager.this.transacToAccountId));
                                FragmentHomePager.this.transactions.setToAccount(FragmentHomePager.this.transacToAccountName);
                                FragmentHomePager.this.transactions.setDate(format3);
                                FragmentHomePager.this.transactions.setComment(FragmentHomePager.this.edtTransaction_comment.getText().toString());
                                FragmentHomePager.this.dataManager.addTransaction(FragmentHomePager.this.transactions);
                                FragmentHomePager.this.dataManager.getTransaction();
                                FragmentHomePager.this.spinTransaction_fromAccount.setSelection(0);
                                FragmentHomePager.this.spinTransaction_toAccount.setSelection(0);
                                FragmentHomePager.this.edtTransaction_amount.setText("");
                                FragmentHomePager.this.edtTransaction_comment.setText("");
                                TextView textView3 = FragmentHomePager.this.tvTransaction_date;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(FragmentHomePager.this.day1);
                                sb3.append("-");
                                sb3.append(FragmentHomePager.this.month1);
                                sb3.append("-");
                                sb3.append(FragmentHomePager.this.mYear);
                                textView3.setText(sb3.toString());
                            } else {
                                Toast.makeText(FragmentHomePager.this.getActivity(), "you don't have sufficient cash balance", 0).show();
                            }
                        }
                        if (FragmentHomePager.this.transacToAccountType.equals("Bank Account")) {
                            while (i < FragmentHomePager.this.arrayListBank.size()) {
                                if (((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankId() == FragmentHomePager.this.transacToAccountId) {
                                    Double valueOf9 = Double.valueOf(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getInitialBalance());
                                    Double valueOf10 = Double.valueOf(FragmentHomePager.this.edtTransaction_amount.getText().toString());
                                    if (valueOf9.doubleValue() > valueOf10.doubleValue()) {
                                        Double valueOf11 = Double.valueOf(valueOf9.doubleValue() + valueOf10.doubleValue());
                                        AccountDetails accountDetails3 = new AccountDetails();
                                        accountDetails3.setBankName(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankName());
                                        accountDetails3.setAccountType(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getAccountType());
                                        accountDetails3.setBankAccNo(((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankAccNo());
                                        accountDetails3.setInitialBalance(String.valueOf(valueOf11));
                                        FragmentHomePager.this.dataManager.updateBank(accountDetails3, ((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).getBankId());
                                        ((AccountDetails) FragmentHomePager.this.arrayListBank.get(i)).setInitialBalance(String.valueOf(valueOf11));
                                    }
                                }
                                i++;
                            }
                        } else if (FragmentHomePager.this.transacToAccountType.equals("E-wallet")) {
                            while (i < FragmentHomePager.this.arrayListWallet.size()) {
                                if (((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i)).geteWalletId() == FragmentHomePager.this.transacToAccountId) {
                                    Double valueOf12 = Double.valueOf(((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i)).getEwalletBalance());
                                    Double valueOf13 = Double.valueOf(FragmentHomePager.this.edtTransaction_amount.getText().toString());
                                    if (valueOf12.doubleValue() > valueOf13.doubleValue()) {
                                        Double valueOf14 = Double.valueOf(valueOf12.doubleValue() + valueOf13.doubleValue());
                                        AccountDetails accountDetails4 = new AccountDetails();
                                        accountDetails4.seteWalletName(((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i)).geteWalletName());
                                        accountDetails4.setEwalletBalance(String.valueOf(valueOf14));
                                        FragmentHomePager.this.dataManager.updateWallet(accountDetails4, ((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i)).geteWalletId());
                                        ((AccountDetails) FragmentHomePager.this.arrayListWallet.get(i)).setEwalletBalance(String.valueOf(valueOf14));
                                    }
                                }
                                i++;
                            }
                        } else if (FragmentHomePager.this.transacToAccountType.equals("Cash")) {
                            String cash2 = FragmentHomePager.this.dataManager.getCash();
                            if (cash2 == null) {
                                cash2 = "00";
                            }
                            Double valueOf15 = Double.valueOf(cash2);
                            Double valueOf16 = Double.valueOf(FragmentHomePager.this.edtTransaction_amount.getText().toString());
                            if (valueOf15.doubleValue() > valueOf16.doubleValue()) {
                                FragmentHomePager.this.dataManager.addCash(String.valueOf(Double.valueOf(valueOf15.doubleValue() + valueOf16.doubleValue())));
                            }
                        }
                        FragmentHomePager.this.util.dismissDialog();
                    } catch (Exception e) {
                        FragmentHomePager.this.util.dismissDialog();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(FragmentHomePager.this.getActivity(), "Amount will transfer in two different accounts not in same account, Select different account for one", 1).show();
                }
            }
        });
        return inflate;
    }

    public void getAccountList() {
        StringBuilder sb = new StringBuilder();
        sb.append("................");
        sb.append(this.position);
        sb.append("........................");
        Log.e("postion", sb.toString());
        try {
            this.arrayListBank = this.dataManager.getBank();
            this.arrayListWallet = this.dataManager.gettWallet();
            this.arrayListCredit = this.dataManager.getCredit();
            String cash = this.dataManager.getCash();
            this.spinListBank = new ArrayList<>();
            this.spinListIncomeAcc = new ArrayList<>();
            this.spinListBank.clear();
            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setAccountId(0);
            accountDetails.setAccountName("Cash");
            accountDetails.setTotalAmount(cash);
            accountDetails.setType("Cash");
            this.spinListBank.add(0, accountDetails);
            this.spinListIncomeAcc.add(0, accountDetails);
            if (this.arrayListBank != null) {
                for (int i = 0; i < this.arrayListBank.size(); i++) {
                    AccountDetails accountDetails2 = new AccountDetails();
                    accountDetails2.setAccountId(((AccountDetails) this.arrayListBank.get(i)).getBankId());
                    accountDetails2.setAccountName(((AccountDetails) this.arrayListBank.get(i)).getBankName());
                    accountDetails2.setTotalAmount(((AccountDetails) this.arrayListBank.get(i)).getInitialBalance());
                    accountDetails2.setAccountType(((AccountDetails) this.arrayListBank.get(i)).getAccountType());
                    accountDetails2.setType("Bank Account");
                    this.spinListBank.add(this.spinListBank.size(), accountDetails2);
                    this.spinListIncomeAcc.add(this.spinListIncomeAcc.size(), accountDetails2);
                }
            }
            if (this.arrayListWallet != null) {
                for (int i2 = 0; i2 < this.arrayListWallet.size(); i2++) {
                    AccountDetails accountDetails3 = new AccountDetails();
                    accountDetails3.setAccountId(((AccountDetails) this.arrayListWallet.get(i2)).geteWalletId());
                    accountDetails3.setAccountName(((AccountDetails) this.arrayListWallet.get(i2)).geteWalletName());
                    accountDetails3.setTotalAmount(((AccountDetails) this.arrayListWallet.get(i2)).getEwalletBalance());
                    accountDetails3.setType("E-wallet");
                    this.spinListBank.add(this.spinListBank.size(), accountDetails3);
                    this.spinListIncomeAcc.add(this.spinListIncomeAcc.size(), accountDetails3);
                }
            }
            if (this.arrayListCredit != null) {
                for (int i3 = 0; i3 < this.arrayListCredit.size(); i3++) {
                    AccountDetails accountDetails4 = new AccountDetails();
                    accountDetails4.setAccountId(((AccountDetails) this.arrayListCredit.get(i3)).getCreditId());
                    accountDetails4.setAccountName(((AccountDetails) this.arrayListCredit.get(i3)).getCreditCardName());
                    accountDetails4.setTotalAmount(((AccountDetails) this.arrayListCredit.get(i3)).getCreditCardBalance());
                    accountDetails4.setType("Credit Card");
                    this.spinListBank.add(this.spinListBank.size(), accountDetails4);
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(".......");
            sb2.append(this.spinListBank.size());
            sb2.append("..............");
            Log.e(".spinBank size...", sb2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
