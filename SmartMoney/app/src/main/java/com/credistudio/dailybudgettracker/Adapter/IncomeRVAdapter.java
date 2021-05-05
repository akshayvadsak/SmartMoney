package com.credistudio.dailybudgettracker.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.credistudio.dailybudgettracker.Activity.ReportFilterActivity;
import com.credistudio.dailybudgettracker.Classes.AccountDetails;
import com.credistudio.dailybudgettracker.Classes.Category;
import com.credistudio.dailybudgettracker.Classes.Expense;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.inject.Inject;
import me.drakeet.materialdialog.MaterialDialog;

public class IncomeRVAdapter extends RecyclerView.Adapter<IncomeRVAdapter.MyViewHolder> {
    ArrayList<AccountDetails> accountList;
    ArrayList<AccountDetails> arrayListBank;
    ArrayList<AccountDetails> arrayListCredit;
    ArrayList<AccountDetails> arrayListWallet;
    ArrayList<Category> categoryList;
    Context context;
    @Inject
    DataManager dataManager;
    String incomeAccName = "";
    String incomeAccType = "";
    int incomeAccountItemId = 0;
    int incomeCatId = 0;
    String incomeCatName = "";
    ArrayList<Expense> incomeList;
    int mDay = 0;
    int mMonth = 0;
    int mYear = 0;
    MaterialDialog materialDialog_delete;
    MaterialDialog materialDialog_edit;

    public class MyViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        ImageView imgIncomeList_delete;
        ImageView imgIncomeList_edit;
        LinearLayout linExpenseList_category;
        TextView tvIncomeList_account;
        TextView tvIncomeList_amount;
        TextView tvIncomeList_category;
        TextView tvIncomeList_comment;
        TextView tvIncomeList_date;

        public MyViewHolder(View view) {
            super(view);
            this.tvIncomeList_comment = (TextView) view.findViewById(R.id.tvIncomeList_comment);
            this.tvIncomeList_amount = (TextView) view.findViewById(R.id.tvIncomeList_amount);
            this.tvIncomeList_category = (TextView) view.findViewById(R.id.tvIncomeList_category);
            this.tvIncomeList_date = (TextView) view.findViewById(R.id.tvIncomeList_date);
            this.tvIncomeList_account = (TextView) view.findViewById(R.id.tvIncomeList_account);
            this.imgIncomeList_delete = (ImageView) view.findViewById(R.id.imgIncomeList_delete);
            this.imgIncomeList_edit = (ImageView) view.findViewById(R.id.imgIncomeList_edit);
            this.linExpenseList_category = (LinearLayout) view.findViewById(R.id.linExpenseList_category);
            this.imgIncomeList_delete.setOnClickListener(this);
            this.imgIncomeList_edit.setOnClickListener(this);
        }

        public void onClick(View view) {
            String str;
            for (int i = 0; i < IncomeRVAdapter.this.accountList.size(); i++) {
                if (((AccountDetails) IncomeRVAdapter.this.accountList.get(i)).getAccountName().equals(((Expense) IncomeRVAdapter.this.incomeList.get(getAdapterPosition())).getAccount())) {
                    IncomeRVAdapter.this.incomeAccountItemId = ((AccountDetails) IncomeRVAdapter.this.accountList.get(i)).getAccountId();
                    IncomeRVAdapter.this.incomeAccName = ((AccountDetails) IncomeRVAdapter.this.accountList.get(i)).getAccountName();
                    IncomeRVAdapter.this.incomeAccType = ((AccountDetails) IncomeRVAdapter.this.accountList.get(i)).getType();
                }
            }
            if (view.getId() == R.id.imgIncomeList_edit) {
                View inflate = ((LayoutInflater) IncomeRVAdapter.this.context.getSystemService("layout_inflater")).inflate(R.layout.item_edit_expense, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtExpenses_editAmount);
                final TextView textView = (TextView) inflate.findViewById(R.id.tvExpenses_editDate);
                final EditText editText2 = (EditText) inflate.findViewById(R.id.edtExpenses_editComment);
                final Spinner spinner = (Spinner) inflate.findViewById(R.id.spinExpenses_editCategory);
                TextView textView2 = (TextView) inflate.findViewById(R.id.tvExpenses_editAccount);
                spinner.setAdapter(new CategorySpinnerAdapter(IncomeRVAdapter.this.context, R.layout.item_account, IncomeRVAdapter.this.categoryList));
                textView2.setText(((Expense) IncomeRVAdapter.this.incomeList.get(getAdapterPosition())).getAccount());
                for (int i2 = 0; i2 < IncomeRVAdapter.this.categoryList.size(); i2++) {
                    if (((Category) IncomeRVAdapter.this.categoryList.get(i2)).getCatId() == Integer.parseInt(((Expense) IncomeRVAdapter.this.incomeList.get(getAdapterPosition())).getCategoryId())) {
                        spinner.setSelection(i2);
                    }
                }
                textView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(IncomeRVAdapter.this.context, new OnDateSetListener() {
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
                                TextView textView = null;
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append(str);
                                sb6.append("-");
                                sb6.append(str2);
                                sb6.append("-");
                                sb6.append(i);
                                textView.setText(sb6.toString());
                            }
                        }, IncomeRVAdapter.this.mYear, IncomeRVAdapter.this.mMonth, IncomeRVAdapter.this.mDay);
                        datePickerDialog.show();
                    }
                });
                String str2 = "";
                try {
                    str = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(((Expense) IncomeRVAdapter.this.incomeList.get(getAdapterPosition())).getDate()));
                } catch (Exception e) {
                    e.printStackTrace();
                    str = str2;
                }
                editText.setText(((Expense) IncomeRVAdapter.this.incomeList.get(getAdapterPosition())).getAmount());
                textView.setText(str);
                editText2.setText(((Expense) IncomeRVAdapter.this.incomeList.get(getAdapterPosition())).getCommnet());
                spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        Category category = (Category) adapterView.getItemAtPosition(i);
                        IncomeRVAdapter.this.incomeCatId = category.getCatId();
                        IncomeRVAdapter.this.incomeCatName = category.getCatName();
                        StringBuilder sb = new StringBuilder();
                        sb.append("............");
                        sb.append(IncomeRVAdapter.this.incomeCatId);
                        sb.append("..........");
                        Log.e("item At position", sb.toString());
                    }
                });
                IncomeRVAdapter.this.materialDialog_edit = new MaterialDialog(IncomeRVAdapter.this.context);
                IncomeRVAdapter.this.materialDialog_edit.setTitle((CharSequence) "Edit Income Data");
                IncomeRVAdapter.this.materialDialog_edit.setContentView(inflate);
                IncomeRVAdapter.this.materialDialog_edit.setCanceledOnTouchOutside(false);
                OnClickListener r3 = new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter name amount");
                        } else if (spinner.getSelectedItemPosition() == 0) {
                            Toast.makeText(IncomeRVAdapter.this.context, "Choose one Category ", 0).show();
                        } else {
                            try {
                                if (!editText.getText().toString().equals(((Expense) IncomeRVAdapter.this.incomeList.get(MyViewHolder.this.getAdapterPosition())).getAmount())) {
                                    if (IncomeRVAdapter.this.incomeAccType.equals("Bank Account")) {
                                        for (int i = 0; i < IncomeRVAdapter.this.arrayListBank.size(); i++) {
                                            if (((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getBankId() == IncomeRVAdapter.this.incomeAccountItemId) {
                                                Double valueOf = Double.valueOf(((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getInitialBalance());
                                                Double valueOf2 = Double.valueOf(editText.getText().toString());
                                                Double valueOf3 = Double.valueOf(((Expense) IncomeRVAdapter.this.incomeList.get(MyViewHolder.this.getAdapterPosition())).getAmount());
                                                Double.valueOf(0.0d);
                                                if (valueOf2.doubleValue() < valueOf3.doubleValue()) {
                                                    Double valueOf4 = Double.valueOf(valueOf.doubleValue() - Double.valueOf(valueOf3.doubleValue() - valueOf2.doubleValue()).doubleValue());
                                                    AccountDetails accountDetails = new AccountDetails();
                                                    accountDetails.setBankName(((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getBankName());
                                                    accountDetails.setAccountType(((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getAccountType());
                                                    accountDetails.setBankAccNo(((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getBankAccNo());
                                                    accountDetails.setInitialBalance(String.valueOf(valueOf4));
                                                    IncomeRVAdapter.this.dataManager.updateBank(accountDetails, ((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getBankId());
                                                    ((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).setInitialBalance(String.valueOf(valueOf4));
                                                } else if (valueOf3.doubleValue() < valueOf2.doubleValue()) {
                                                    Double valueOf5 = Double.valueOf(valueOf2.doubleValue() - valueOf3.doubleValue());
                                                    if (valueOf.doubleValue() < valueOf5.doubleValue()) {
                                                        Toast.makeText(IncomeRVAdapter.this.context, "you don't have sufficient cash balance", 0).show();
                                                    } else {
                                                        Double valueOf6 = Double.valueOf(valueOf.doubleValue() + valueOf5.doubleValue());
                                                        AccountDetails accountDetails2 = new AccountDetails();
                                                        accountDetails2.setBankName(((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getBankName());
                                                        accountDetails2.setAccountType(((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getAccountType());
                                                        accountDetails2.setBankAccNo(((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getBankAccNo());
                                                        accountDetails2.setInitialBalance(String.valueOf(valueOf6));
                                                        IncomeRVAdapter.this.dataManager.updateBank(accountDetails2, ((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getBankId());
                                                        ((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).setInitialBalance(String.valueOf(valueOf6));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (IncomeRVAdapter.this.incomeAccType.equals("E-wallet")) {
                                        for (int i2 = 0; i2 < IncomeRVAdapter.this.arrayListWallet.size(); i2++) {
                                            if (((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).geteWalletId() == IncomeRVAdapter.this.incomeAccountItemId) {
                                                Double valueOf7 = Double.valueOf(((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).getEwalletBalance());
                                                Double valueOf8 = Double.valueOf(editText.getText().toString());
                                                Double valueOf9 = Double.valueOf(((Expense) IncomeRVAdapter.this.incomeList.get(MyViewHolder.this.getAdapterPosition())).getAmount());
                                                Double.valueOf(0.0d);
                                                if (valueOf8.doubleValue() < valueOf9.doubleValue()) {
                                                    Double valueOf10 = Double.valueOf(valueOf7.doubleValue() - Double.valueOf(valueOf9.doubleValue() - valueOf8.doubleValue()).doubleValue());
                                                    AccountDetails accountDetails3 = new AccountDetails();
                                                    accountDetails3.seteWalletName(((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).geteWalletName());
                                                    accountDetails3.setEwalletBalance(String.valueOf(valueOf10));
                                                    IncomeRVAdapter.this.dataManager.updateWallet(accountDetails3, ((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).geteWalletId());
                                                    ((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).setEwalletBalance(String.valueOf(valueOf10));
                                                } else if (valueOf9.doubleValue() < valueOf8.doubleValue()) {
                                                    Double valueOf11 = Double.valueOf(valueOf8.doubleValue() - valueOf9.doubleValue());
                                                    if (valueOf7.doubleValue() < valueOf11.doubleValue()) {
                                                        Toast.makeText(IncomeRVAdapter.this.context, "you don't have sufficient cash balance", 0).show();
                                                    } else {
                                                        Double valueOf12 = Double.valueOf(valueOf7.doubleValue() + valueOf11.doubleValue());
                                                        AccountDetails accountDetails4 = new AccountDetails();
                                                        accountDetails4.seteWalletName(((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).geteWalletName());
                                                        accountDetails4.setEwalletBalance(String.valueOf(valueOf12));
                                                        IncomeRVAdapter.this.dataManager.updateWallet(accountDetails4, ((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).geteWalletId());
                                                        ((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).setEwalletBalance(String.valueOf(valueOf12));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (IncomeRVAdapter.this.incomeAccType.equals("Credit Card")) {
                                        for (int i3 = 0; i3 < IncomeRVAdapter.this.arrayListCredit.size(); i3++) {
                                            if (((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditId() == IncomeRVAdapter.this.incomeAccountItemId) {
                                                Double valueOf13 = Double.valueOf(((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditCardBalance());
                                                Double valueOf14 = Double.valueOf(editText.getText().toString());
                                                Double valueOf15 = Double.valueOf(((Expense) IncomeRVAdapter.this.incomeList.get(MyViewHolder.this.getAdapterPosition())).getAmount());
                                                Double.valueOf(0.0d);
                                                if (valueOf14.doubleValue() < valueOf15.doubleValue()) {
                                                    Double valueOf16 = Double.valueOf(valueOf13.doubleValue() - Double.valueOf(valueOf15.doubleValue() - valueOf14.doubleValue()).doubleValue());
                                                    AccountDetails accountDetails5 = new AccountDetails();
                                                    accountDetails5.setCreditCardName(((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditCardName());
                                                    accountDetails5.setCreditCardNo(((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditCardNo());
                                                    accountDetails5.setCreditCardBalance(String.valueOf(valueOf16));
                                                    IncomeRVAdapter.this.dataManager.updateCredit(accountDetails5, ((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditId());
                                                    ((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).setCreditCardBalance(String.valueOf(valueOf16));
                                                } else if (valueOf15.doubleValue() < valueOf14.doubleValue()) {
                                                    Double valueOf17 = Double.valueOf(valueOf14.doubleValue() - valueOf15.doubleValue());
                                                    if (valueOf13.doubleValue() < valueOf17.doubleValue()) {
                                                        Toast.makeText(IncomeRVAdapter.this.context, "you don't have sufficient credit balance", 0).show();
                                                    } else {
                                                        Double valueOf18 = Double.valueOf(valueOf13.doubleValue() + valueOf17.doubleValue());
                                                        AccountDetails accountDetails6 = new AccountDetails();
                                                        accountDetails6.setCreditCardName(((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditCardName());
                                                        accountDetails6.setCreditCardNo(((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditCardNo());
                                                        accountDetails6.setCreditCardBalance(String.valueOf(valueOf18));
                                                        IncomeRVAdapter.this.dataManager.updateCredit(accountDetails6, ((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditId());
                                                        ((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).setCreditCardBalance(String.valueOf(valueOf18));
                                                    }
                                                }
                                            }
                                        }
                                    } else if (IncomeRVAdapter.this.incomeAccType.equals("Cash")) {
                                        String cash = IncomeRVAdapter.this.dataManager.getCash();
                                        if (cash == null) {
                                            Toast.makeText(IncomeRVAdapter.this.context, "you don't have sufficient cash balance", 0).show();
                                        } else {
                                            Double valueOf19 = Double.valueOf(cash);
                                            Double valueOf20 = Double.valueOf(editText.getText().toString());
                                            Double valueOf21 = Double.valueOf(((Expense) IncomeRVAdapter.this.incomeList.get(MyViewHolder.this.getAdapterPosition())).getAmount());
                                            Double.valueOf(0.0d);
                                            if (valueOf20.doubleValue() < valueOf21.doubleValue()) {
                                                Double valueOf22 = Double.valueOf(valueOf19.doubleValue() - Double.valueOf(valueOf21.doubleValue() - valueOf20.doubleValue()).doubleValue());
                                                new AccountDetails();
                                                IncomeRVAdapter.this.dataManager.addCash(String.valueOf(valueOf22));
                                            } else if (valueOf21.doubleValue() < valueOf20.doubleValue()) {
                                                Double valueOf23 = Double.valueOf(valueOf20.doubleValue() - valueOf21.doubleValue());
                                                if (valueOf19.doubleValue() < valueOf23.doubleValue()) {
                                                    Toast.makeText(IncomeRVAdapter.this.context, "you don't have sufficient cash balance", 0).show();
                                                } else {
                                                    Double valueOf24 = Double.valueOf(valueOf19.doubleValue() + valueOf23.doubleValue());
                                                    new AccountDetails();
                                                    IncomeRVAdapter.this.dataManager.addCash(String.valueOf(valueOf24));
                                                }
                                            }
                                        }
                                    }
                                    String format = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(textView.getText().toString()));
                                    Expense expense = new Expense();
                                    expense.setAmount(editText.getText().toString());
                                    expense.setDate(format);
                                    expense.setCategory(IncomeRVAdapter.this.incomeCatName);
                                    expense.setCategoryId(String.valueOf(IncomeRVAdapter.this.incomeCatId));
                                    expense.setAccount(IncomeRVAdapter.this.incomeAccName);
                                    expense.setAccountId(String.valueOf(IncomeRVAdapter.this.incomeAccountItemId));
                                    expense.setCommnet(editText2.getText().toString());
                                    IncomeRVAdapter.this.dataManager.updateIncome(expense, ((Expense) IncomeRVAdapter.this.incomeList.get(MyViewHolder.this.getAdapterPosition())).getId());
                                    IncomeRVAdapter.this.context.startActivity(new Intent(IncomeRVAdapter.this.context, ReportFilterActivity.class));
                                    ((Activity) IncomeRVAdapter.this.context).finish();
                                    IncomeRVAdapter.this.notifyItemChanged(MyViewHolder.this.getAdapterPosition());
                                    IncomeRVAdapter.this.materialDialog_edit.dismiss();
                                    Toast.makeText(IncomeRVAdapter.this.context, "Data has been successfully updated ", 0).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                IncomeRVAdapter.this.materialDialog_edit.setPositiveButton("OK", (OnClickListener) r3);
                IncomeRVAdapter.this.materialDialog_edit.setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        IncomeRVAdapter.this.materialDialog_edit.dismiss();
                    }
                });
                IncomeRVAdapter.this.materialDialog_edit.show();
            }
            if (view.getId() == R.id.imgIncomeList_delete) {
                IncomeRVAdapter.this.materialDialog_delete = new MaterialDialog(IncomeRVAdapter.this.context).setTitle((CharSequence) "Remove Data").setMessage((CharSequence) "Are you sure you want to delete that data?").setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        IncomeRVAdapter.this.materialDialog_delete.dismiss();
                        try {
                            if (IncomeRVAdapter.this.incomeAccType.equals("Bank Account")) {
                                for (int i = 0; i < IncomeRVAdapter.this.arrayListBank.size(); i++) {
                                    if (((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getBankId() == IncomeRVAdapter.this.incomeAccountItemId) {
                                        Double valueOf = Double.valueOf(Double.valueOf(((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getInitialBalance()).doubleValue() - Double.valueOf(((Expense) IncomeRVAdapter.this.incomeList.get(MyViewHolder.this.getAdapterPosition())).getAmount()).doubleValue());
                                        AccountDetails accountDetails = new AccountDetails();
                                        accountDetails.setBankName(((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getBankName());
                                        accountDetails.setAccountType(((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getAccountType());
                                        accountDetails.setBankAccNo(((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getBankAccNo());
                                        accountDetails.setInitialBalance(String.valueOf(valueOf));
                                        IncomeRVAdapter.this.dataManager.updateBank(accountDetails, ((AccountDetails) IncomeRVAdapter.this.arrayListBank.get(i)).getBankId());
                                    }
                                }
                            } else if (IncomeRVAdapter.this.incomeAccType.equals("E-wallet")) {
                                for (int i2 = 0; i2 < IncomeRVAdapter.this.arrayListWallet.size(); i2++) {
                                    if (((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).geteWalletId() == IncomeRVAdapter.this.incomeAccountItemId) {
                                        Double valueOf2 = Double.valueOf(Double.valueOf(((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).getEwalletBalance()).doubleValue() - Double.valueOf(((Expense) IncomeRVAdapter.this.incomeList.get(MyViewHolder.this.getAdapterPosition())).getAmount()).doubleValue());
                                        AccountDetails accountDetails2 = new AccountDetails();
                                        accountDetails2.seteWalletName(((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).geteWalletName());
                                        accountDetails2.setEwalletBalance(String.valueOf(valueOf2));
                                        IncomeRVAdapter.this.dataManager.updateWallet(accountDetails2, ((AccountDetails) IncomeRVAdapter.this.arrayListWallet.get(i2)).geteWalletId());
                                    }
                                }
                            } else if (IncomeRVAdapter.this.incomeAccType.equals("Credit Card")) {
                                for (int i3 = 0; i3 < IncomeRVAdapter.this.arrayListCredit.size(); i3++) {
                                    if (((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditId() == IncomeRVAdapter.this.incomeAccountItemId) {
                                        Double valueOf3 = Double.valueOf(Double.valueOf(((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditCardBalance()).doubleValue() - Double.valueOf(((Expense) IncomeRVAdapter.this.incomeList.get(MyViewHolder.this.getAdapterPosition())).getAmount()).doubleValue());
                                        AccountDetails accountDetails3 = new AccountDetails();
                                        accountDetails3.setCreditCardName(((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditCardName());
                                        accountDetails3.setCreditCardNo(((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditCardNo());
                                        accountDetails3.setCreditCardBalance(String.valueOf(valueOf3));
                                        IncomeRVAdapter.this.dataManager.updateCredit(accountDetails3, ((AccountDetails) IncomeRVAdapter.this.arrayListCredit.get(i3)).getCreditId());
                                    }
                                }
                            } else if (IncomeRVAdapter.this.incomeAccType.equals("Cash")) {
                                Double valueOf4 = Double.valueOf(Double.valueOf(IncomeRVAdapter.this.dataManager.getCash()).doubleValue() - Double.valueOf(((Expense) IncomeRVAdapter.this.incomeList.get(MyViewHolder.this.getAdapterPosition())).getAmount()).doubleValue());
                                new AccountDetails();
                                IncomeRVAdapter.this.dataManager.addCash(String.valueOf(valueOf4));
                            }
                            IncomeRVAdapter.this.dataManager.deleteIncome(((Expense) IncomeRVAdapter.this.incomeList.get(MyViewHolder.this.getAdapterPosition())).getId());
                            IncomeRVAdapter.this.incomeList.remove(MyViewHolder.this.getAdapterPosition());
                            IncomeRVAdapter.this.notifyItemRemoved(MyViewHolder.this.getAdapterPosition());
                            IncomeRVAdapter.this.context.startActivity(new Intent(IncomeRVAdapter.this.context, ReportFilterActivity.class));
                            ((Activity) IncomeRVAdapter.this.context).finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(IncomeRVAdapter.this.context, "Data has been successfully deleted ", 0).show();
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        IncomeRVAdapter.this.materialDialog_delete.dismiss();
                    }
                });
                IncomeRVAdapter.this.materialDialog_delete.show();
            }
        }
    }

    public IncomeRVAdapter(Context context2, ArrayList<Expense> arrayList, ArrayList<AccountDetails> arrayList2, ArrayList<Category> arrayList3) {
        this.context = context2;
        this.incomeList = arrayList;
        this.accountList = arrayList2;
        this.categoryList = arrayList3;
        DaggerApplication.get(context2).getComponent().inject(this);
        getAccountList();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_incomelist, null));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(".........");
        sb.append(((Expense) this.incomeList.get(i)).getAccount());
        sb.append("..............");
        Log.e("getAccount", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(".........");
        sb2.append(((Expense) this.incomeList.get(i)).getCategory());
        sb2.append("..............");
        Log.e("getAccount", sb2.toString());
        if (((Expense) this.incomeList.get(i)).getAccount().equals("")) {
            myViewHolder.tvIncomeList_account.setText("Cash");
        }
        if (((Expense) this.incomeList.get(i)).getCategory().equals("")) {
            myViewHolder.linExpenseList_category.setVisibility(8);
        }
        Calendar.getInstance();
        String str2 = "";
        try {
            str = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(((Expense) this.incomeList.get(i)).getDate()));
        } catch (Exception e) {
            e.printStackTrace();
            str = str2;
        }
        TextView textView = myViewHolder.tvIncomeList_amount;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("₹ ");
        sb3.append(((Expense) this.incomeList.get(i)).getAmount());
        textView.setText(sb3.toString());
        myViewHolder.tvIncomeList_date.setText(str);
        myViewHolder.tvIncomeList_comment.setText(((Expense) this.incomeList.get(i)).getCommnet());
        myViewHolder.tvIncomeList_account.setText(((Expense) this.incomeList.get(i)).getAccount());
        myViewHolder.tvIncomeList_category.setText(((Expense) this.incomeList.get(i)).getCategory());
    }

    public int getItemCount() {
        return this.incomeList.size();
    }

    public void getAccountList() {
        try {
            this.arrayListBank = this.dataManager.getBank();
            this.arrayListWallet = this.dataManager.gettWallet();
            this.arrayListCredit = this.dataManager.getCredit();
            this.dataManager.getCash();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
