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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.credistudio.dailybudgettracker.Activity.ReportFilterActivity;
import com.credistudio.dailybudgettracker.Classes.AccountDetails;
import com.credistudio.dailybudgettracker.Classes.Transactions;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.inject.Inject;
import me.drakeet.materialdialog.MaterialDialog;

public class TransferRVAdapter extends RecyclerView.Adapter<TransferRVAdapter.MyViewHolders> {
    ArrayList<AccountDetails> accountList;
    ArrayList<AccountDetails> arrayListBank;
    ArrayList<AccountDetails> arrayListCredit;
    ArrayList<AccountDetails> arrayListWallet;
    Context context;
    @Inject
    DataManager dataManager;
    int mDay = 0;
    int mMonth = 0;
    int mYear = 0;
    MaterialDialog materialDialog_delete;
    MaterialDialog materialDialog_edit;
    ArrayList<Transactions> transactionList;
    int transferFromAccId = 0;
    String transferFromAccType = "";
    String transferFromAccount = "";
    int transferToAccId = 0;
    String transferToAccType = "";
    String transferToAccount = "";

    public class MyViewHolders extends RecyclerView.ViewHolder implements OnClickListener {
        ImageView imgTransferList_delete;
        ImageView imgTransferList_edit;
        TextView tvTransferList_amount;
        TextView tvTransferList_comment;
        TextView tvTransferList_date;
        TextView tvTransferList_fromAccount;
        TextView tvTransferList_toAccount;

        public MyViewHolders(View view) {
            super(view);
            this.tvTransferList_date = (TextView) view.findViewById(R.id.tvTransferList_date);
            this.tvTransferList_amount = (TextView) view.findViewById(R.id.tvTransferList_amount);
            this.tvTransferList_fromAccount = (TextView) view.findViewById(R.id.tvTransferList_fromAccount);
            this.tvTransferList_toAccount = (TextView) view.findViewById(R.id.tvTransferList_toAccount);
            this.tvTransferList_comment = (TextView) view.findViewById(R.id.tvTransferList_comment);
            this.imgTransferList_delete = (ImageView) view.findViewById(R.id.imgTransferList_delete);
            this.imgTransferList_edit = (ImageView) view.findViewById(R.id.imgTransferList_edit);
            this.imgTransferList_edit.setOnClickListener(this);
            this.imgTransferList_delete.setOnClickListener(this);
        }

        public void onClick(View view) {
            String str;
            for (int i = 0; i < TransferRVAdapter.this.accountList.size(); i++) {
                if (((AccountDetails) TransferRVAdapter.this.accountList.get(i)).getAccountName().equals(((Transactions) TransferRVAdapter.this.transactionList.get(getAdapterPosition())).getFromAccount())) {
                    TransferRVAdapter.this.transferFromAccId = ((AccountDetails) TransferRVAdapter.this.accountList.get(i)).getAccountId();
                    TransferRVAdapter.this.transferFromAccType = ((AccountDetails) TransferRVAdapter.this.accountList.get(i)).getType();
                    TransferRVAdapter.this.transferFromAccount = ((AccountDetails) TransferRVAdapter.this.accountList.get(i)).getAccountName();
                }
            }
            for (int i2 = 0; i2 < TransferRVAdapter.this.accountList.size(); i2++) {
                if (((AccountDetails) TransferRVAdapter.this.accountList.get(i2)).getAccountName().equals(((Transactions) TransferRVAdapter.this.transactionList.get(getAdapterPosition())).getToAccount())) {
                    TransferRVAdapter.this.transferToAccId = ((AccountDetails) TransferRVAdapter.this.accountList.get(i2)).getAccountId();
                    TransferRVAdapter.this.transferToAccType = ((AccountDetails) TransferRVAdapter.this.accountList.get(i2)).getType();
                    TransferRVAdapter.this.transferToAccount = ((AccountDetails) TransferRVAdapter.this.accountList.get(i2)).getAccountName();
                }
            }
            if (view.getId() == R.id.imgTransferList_edit) {
                Calendar instance = Calendar.getInstance();
                TransferRVAdapter.this.mYear = instance.get(1);
                TransferRVAdapter.this.mMonth = instance.get(2);
                TransferRVAdapter.this.mDay = instance.get(5);
                View inflate = ((LayoutInflater) TransferRVAdapter.this.context.getSystemService("layout_inflater")).inflate(R.layout.item_edit_transfer, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtTransaction_editAmount);
                final EditText editText2 = (EditText) inflate.findViewById(R.id.edtTransaction_editComment);
                final TextView textView = (TextView) inflate.findViewById(R.id.tvTransaction_editDate);
                TextView textView2 = (TextView) inflate.findViewById(R.id.tvTransaction_editFromAccount);
                TextView textView3 = (TextView) inflate.findViewById(R.id.tvTransaction_editToAccount);
                textView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(TransferRVAdapter.this.context, new OnDateSetListener() {
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
                        }, TransferRVAdapter.this.mYear, TransferRVAdapter.this.mMonth, TransferRVAdapter.this.mDay);
                        datePickerDialog.show();
                    }
                });
                String str2 = "";
                try {
                    str = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(((Transactions) TransferRVAdapter.this.transactionList.get(getAdapterPosition())).getDate()));
                } catch (Exception e) {
                    e.printStackTrace();
                    str = str2;
                }
                editText.setText(((Transactions) TransferRVAdapter.this.transactionList.get(getAdapterPosition())).getAmount());
                textView.setText(str);
                editText2.setText(((Transactions) TransferRVAdapter.this.transactionList.get(getAdapterPosition())).getComment());
                textView2.setText(((Transactions) TransferRVAdapter.this.transactionList.get(getAdapterPosition())).getFromAccount());
                textView3.setText(((Transactions) TransferRVAdapter.this.transactionList.get(getAdapterPosition())).getToAccount());
                TransferRVAdapter.this.materialDialog_edit = new MaterialDialog(TransferRVAdapter.this.context);
                TransferRVAdapter.this.materialDialog_edit.setTitle((CharSequence) "Edit Transfer Data");
                TransferRVAdapter.this.materialDialog_edit.setContentView(inflate);
                TransferRVAdapter.this.materialDialog_edit.setCanceledOnTouchOutside(false);
                TransferRVAdapter.this.materialDialog_edit.setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(".......FROM...........");
                        sb.append(TransferRVAdapter.this.transferFromAccType);
                        sb.append("...");
                        sb.append(TransferRVAdapter.this.transferToAccType);
                        sb.append(".....To ............=============================================..");
                        Log.e("ADAPTER EXPENSE", sb.toString());
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter  amount");
                            return;
                        }
                        try {
                            if (!editText.getText().toString().equals(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount())) {
                                if (TransferRVAdapter.this.transferFromAccType.equals("Bank Account")) {
                                    for (int i = 0; i < TransferRVAdapter.this.arrayListBank.size(); i++) {
                                        if (((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getBankId() == TransferRVAdapter.this.transferFromAccId) {
                                            Double valueOf = Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getInitialBalance());
                                            Double valueOf2 = Double.valueOf(editText.getText().toString());
                                            Double valueOf3 = Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount());
                                            Double.valueOf(0.0d);
                                            if (valueOf2.doubleValue() < valueOf3.doubleValue()) {
                                                Double valueOf4 = Double.valueOf(valueOf.doubleValue() + Double.valueOf(valueOf3.doubleValue() - valueOf2.doubleValue()).doubleValue());
                                                AccountDetails accountDetails = new AccountDetails();
                                                accountDetails.setBankName(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getBankName());
                                                accountDetails.setAccountType(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getAccountType());
                                                accountDetails.setBankAccNo(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getBankAccNo());
                                                accountDetails.setInitialBalance(String.valueOf(valueOf4));
                                                TransferRVAdapter.this.dataManager.updateBank(accountDetails, ((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getBankId());
                                                ((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).setInitialBalance(String.valueOf(valueOf4));
                                            } else if (valueOf3.doubleValue() < valueOf2.doubleValue()) {
                                                Double valueOf5 = Double.valueOf(valueOf2.doubleValue() - valueOf3.doubleValue());
                                                if (valueOf.doubleValue() < valueOf5.doubleValue()) {
                                                    Toast.makeText(TransferRVAdapter.this.context, "you don't have sufficient bank account balance", 0).show();
                                                } else {
                                                    Double valueOf6 = Double.valueOf(valueOf.doubleValue() - valueOf5.doubleValue());
                                                    AccountDetails accountDetails2 = new AccountDetails();
                                                    accountDetails2.setBankName(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getBankName());
                                                    accountDetails2.setAccountType(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getAccountType());
                                                    accountDetails2.setBankAccNo(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getBankAccNo());
                                                    accountDetails2.setInitialBalance(String.valueOf(valueOf6));
                                                    TransferRVAdapter.this.dataManager.updateBank(accountDetails2, ((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getBankId());
                                                    ((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).setInitialBalance(String.valueOf(valueOf6));
                                                }
                                            }
                                        }
                                    }
                                } else if (TransferRVAdapter.this.transferFromAccType.equals("E-wallet")) {
                                    for (int i2 = 0; i2 < TransferRVAdapter.this.arrayListWallet.size(); i2++) {
                                        if (((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).geteWalletId() == TransferRVAdapter.this.transferFromAccId) {
                                            Double valueOf7 = Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).getEwalletBalance());
                                            Double valueOf8 = Double.valueOf(editText.getText().toString());
                                            Double valueOf9 = Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount());
                                            Double.valueOf(0.0d);
                                            if (valueOf8.doubleValue() < valueOf9.doubleValue()) {
                                                Double valueOf10 = Double.valueOf(valueOf7.doubleValue() + Double.valueOf(valueOf9.doubleValue() - valueOf8.doubleValue()).doubleValue());
                                                AccountDetails accountDetails3 = new AccountDetails();
                                                accountDetails3.seteWalletName(((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).geteWalletName());
                                                accountDetails3.setEwalletBalance(String.valueOf(valueOf10));
                                                TransferRVAdapter.this.dataManager.updateWallet(accountDetails3, ((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).geteWalletId());
                                                ((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).setEwalletBalance(String.valueOf(valueOf10));
                                            } else if (valueOf9.doubleValue() < valueOf8.doubleValue()) {
                                                Double valueOf11 = Double.valueOf(valueOf8.doubleValue() - valueOf9.doubleValue());
                                                if (valueOf7.doubleValue() < valueOf11.doubleValue()) {
                                                    Toast.makeText(TransferRVAdapter.this.context, "you don't have sufficient wallet balance", 0).show();
                                                } else {
                                                    Double valueOf12 = Double.valueOf(valueOf7.doubleValue() - valueOf11.doubleValue());
                                                    AccountDetails accountDetails4 = new AccountDetails();
                                                    accountDetails4.seteWalletName(((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).geteWalletName());
                                                    accountDetails4.setEwalletBalance(String.valueOf(valueOf12));
                                                    TransferRVAdapter.this.dataManager.updateWallet(accountDetails4, ((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).geteWalletId());
                                                    ((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).setEwalletBalance(String.valueOf(valueOf12));
                                                }
                                            }
                                        }
                                    }
                                } else if (TransferRVAdapter.this.transferFromAccType.equals("Credit Card")) {
                                    for (int i3 = 0; i3 < TransferRVAdapter.this.arrayListCredit.size(); i3++) {
                                        if (((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditId() == TransferRVAdapter.this.transferFromAccId) {
                                            Double valueOf13 = Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditCardBalance());
                                            Double valueOf14 = Double.valueOf(editText.getText().toString());
                                            Double valueOf15 = Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount());
                                            Double.valueOf(0.0d);
                                            if (valueOf14.doubleValue() < valueOf15.doubleValue()) {
                                                Double valueOf16 = Double.valueOf(valueOf13.doubleValue() + Double.valueOf(valueOf15.doubleValue() - valueOf14.doubleValue()).doubleValue());
                                                AccountDetails accountDetails5 = new AccountDetails();
                                                accountDetails5.setCreditCardName(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditCardName());
                                                accountDetails5.setCreditCardNo(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditCardNo());
                                                accountDetails5.setCreditCardBalance(String.valueOf(valueOf16));
                                                TransferRVAdapter.this.dataManager.updateCredit(accountDetails5, ((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditId());
                                                ((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).setCreditCardBalance(String.valueOf(valueOf16));
                                            } else if (valueOf15.doubleValue() < valueOf14.doubleValue()) {
                                                Double valueOf17 = Double.valueOf(valueOf14.doubleValue() - valueOf15.doubleValue());
                                                if (valueOf13.doubleValue() < valueOf17.doubleValue()) {
                                                    Toast.makeText(TransferRVAdapter.this.context, "you don't have sufficient credit balance", 0).show();
                                                } else {
                                                    Double valueOf18 = Double.valueOf(valueOf13.doubleValue() - valueOf17.doubleValue());
                                                    AccountDetails accountDetails6 = new AccountDetails();
                                                    accountDetails6.setCreditCardName(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditCardName());
                                                    accountDetails6.setCreditCardNo(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditCardNo());
                                                    accountDetails6.setCreditCardBalance(String.valueOf(valueOf18));
                                                    TransferRVAdapter.this.dataManager.updateCredit(accountDetails6, ((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditId());
                                                    ((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).setCreditCardBalance(String.valueOf(valueOf18));
                                                }
                                            }
                                        }
                                    }
                                } else if (TransferRVAdapter.this.transferFromAccType.equals("Cash")) {
                                    String cash = TransferRVAdapter.this.dataManager.getCash();
                                    if (cash == null) {
                                        Toast.makeText(TransferRVAdapter.this.context, "you don't have sufficient cash balance", 0).show();
                                    } else {
                                        Double valueOf19 = Double.valueOf(cash);
                                        Double valueOf20 = Double.valueOf(editText.getText().toString());
                                        Double valueOf21 = Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount());
                                        Double.valueOf(0.0d);
                                        if (valueOf20.doubleValue() < valueOf21.doubleValue()) {
                                            Double valueOf22 = Double.valueOf(valueOf19.doubleValue() + Double.valueOf(valueOf21.doubleValue() - valueOf20.doubleValue()).doubleValue());
                                            new AccountDetails();
                                            TransferRVAdapter.this.dataManager.addCash(String.valueOf(valueOf22));
                                        } else if (valueOf21.doubleValue() < valueOf20.doubleValue()) {
                                            Double valueOf23 = Double.valueOf(valueOf20.doubleValue() - valueOf21.doubleValue());
                                            if (valueOf19.doubleValue() < valueOf23.doubleValue()) {
                                                Toast.makeText(TransferRVAdapter.this.context, "you don't have sufficient cash balance", 0).show();
                                            } else {
                                                TransferRVAdapter.this.dataManager.addCash(String.valueOf(Double.valueOf(valueOf19.doubleValue() - valueOf23.doubleValue())));
                                            }
                                        }
                                    }
                                }
                                if (TransferRVAdapter.this.transferToAccType.equals("Bank Account")) {
                                    for (int i4 = 0; i4 < TransferRVAdapter.this.arrayListBank.size(); i4++) {
                                        if (((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getBankId() == TransferRVAdapter.this.transferToAccId) {
                                            Double valueOf24 = Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getInitialBalance());
                                            Double valueOf25 = Double.valueOf(editText.getText().toString());
                                            Double valueOf26 = Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount());
                                            Double.valueOf(0.0d);
                                            if (valueOf25.doubleValue() < valueOf26.doubleValue()) {
                                                Double valueOf27 = Double.valueOf(valueOf24.doubleValue() - Double.valueOf(valueOf26.doubleValue() - valueOf25.doubleValue()).doubleValue());
                                                AccountDetails accountDetails7 = new AccountDetails();
                                                accountDetails7.setBankName(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getBankName());
                                                accountDetails7.setAccountType(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getAccountType());
                                                accountDetails7.setBankAccNo(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getBankAccNo());
                                                accountDetails7.setInitialBalance(String.valueOf(valueOf27));
                                                TransferRVAdapter.this.dataManager.updateBank(accountDetails7, ((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getBankId());
                                                ((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).setInitialBalance(String.valueOf(valueOf27));
                                            } else if (valueOf26.doubleValue() < valueOf25.doubleValue()) {
                                                Double valueOf28 = Double.valueOf(valueOf25.doubleValue() - valueOf26.doubleValue());
                                                if (valueOf24.doubleValue() < valueOf28.doubleValue()) {
                                                    Toast.makeText(TransferRVAdapter.this.context, "you don't have sufficient bank account balance", 0).show();
                                                } else {
                                                    Double valueOf29 = Double.valueOf(valueOf24.doubleValue() + valueOf28.doubleValue());
                                                    AccountDetails accountDetails8 = new AccountDetails();
                                                    accountDetails8.setBankName(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getBankName());
                                                    accountDetails8.setAccountType(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getAccountType());
                                                    accountDetails8.setBankAccNo(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getBankAccNo());
                                                    accountDetails8.setInitialBalance(String.valueOf(valueOf29));
                                                    TransferRVAdapter.this.dataManager.updateBank(accountDetails8, ((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getBankId());
                                                    ((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).setInitialBalance(String.valueOf(valueOf29));
                                                }
                                            }
                                        }
                                    }
                                } else if (TransferRVAdapter.this.transferToAccType.equals("E-wallet")) {
                                    for (int i5 = 0; i5 < TransferRVAdapter.this.arrayListWallet.size(); i5++) {
                                        if (((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).geteWalletId() == TransferRVAdapter.this.transferToAccId) {
                                            Double valueOf30 = Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).getEwalletBalance());
                                            Double valueOf31 = Double.valueOf(editText.getText().toString());
                                            Double valueOf32 = Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount());
                                            Double.valueOf(0.0d);
                                            if (valueOf31.doubleValue() < valueOf32.doubleValue()) {
                                                Double valueOf33 = Double.valueOf(valueOf30.doubleValue() - Double.valueOf(valueOf32.doubleValue() - valueOf31.doubleValue()).doubleValue());
                                                AccountDetails accountDetails9 = new AccountDetails();
                                                accountDetails9.seteWalletName(((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).geteWalletName());
                                                accountDetails9.setEwalletBalance(String.valueOf(valueOf33));
                                                TransferRVAdapter.this.dataManager.updateWallet(accountDetails9, ((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).geteWalletId());
                                                ((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).setEwalletBalance(String.valueOf(valueOf33));
                                            } else if (valueOf32.doubleValue() < valueOf31.doubleValue()) {
                                                Double valueOf34 = Double.valueOf(valueOf31.doubleValue() - valueOf32.doubleValue());
                                                if (valueOf30.doubleValue() < valueOf34.doubleValue()) {
                                                    Toast.makeText(TransferRVAdapter.this.context, "you don't have sufficient wallet balance", 0).show();
                                                } else {
                                                    Double valueOf35 = Double.valueOf(valueOf30.doubleValue() + valueOf34.doubleValue());
                                                    AccountDetails accountDetails10 = new AccountDetails();
                                                    accountDetails10.seteWalletName(((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).geteWalletName());
                                                    accountDetails10.setEwalletBalance(String.valueOf(valueOf35));
                                                    TransferRVAdapter.this.dataManager.updateWallet(accountDetails10, ((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).geteWalletId());
                                                    ((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).setEwalletBalance(String.valueOf(valueOf35));
                                                }
                                            }
                                        }
                                    }
                                } else if (TransferRVAdapter.this.transferToAccType.equals("Credit Card")) {
                                    for (int i6 = 0; i6 < TransferRVAdapter.this.arrayListCredit.size(); i6++) {
                                        if (((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditId() == TransferRVAdapter.this.transferToAccId) {
                                            Double valueOf36 = Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditCardBalance());
                                            Double valueOf37 = Double.valueOf(editText.getText().toString());
                                            Double valueOf38 = Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount());
                                            Double.valueOf(0.0d);
                                            if (valueOf37.doubleValue() < valueOf38.doubleValue()) {
                                                Double valueOf39 = Double.valueOf(valueOf36.doubleValue() - Double.valueOf(valueOf38.doubleValue() - valueOf37.doubleValue()).doubleValue());
                                                AccountDetails accountDetails11 = new AccountDetails();
                                                accountDetails11.setCreditCardName(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditCardName());
                                                accountDetails11.setCreditCardNo(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditCardNo());
                                                accountDetails11.setCreditCardBalance(String.valueOf(valueOf39));
                                                TransferRVAdapter.this.dataManager.updateCredit(accountDetails11, ((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditId());
                                                ((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).setCreditCardBalance(String.valueOf(valueOf39));
                                            } else if (valueOf38.doubleValue() < valueOf37.doubleValue()) {
                                                Double valueOf40 = Double.valueOf(valueOf37.doubleValue() - valueOf38.doubleValue());
                                                if (valueOf36.doubleValue() < valueOf40.doubleValue()) {
                                                    Toast.makeText(TransferRVAdapter.this.context, "you don't have sufficient credit balance", 0).show();
                                                } else {
                                                    Double valueOf41 = Double.valueOf(valueOf36.doubleValue() + valueOf40.doubleValue());
                                                    AccountDetails accountDetails12 = new AccountDetails();
                                                    accountDetails12.setCreditCardName(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditCardName());
                                                    accountDetails12.setCreditCardNo(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditCardNo());
                                                    accountDetails12.setCreditCardBalance(String.valueOf(valueOf41));
                                                    TransferRVAdapter.this.dataManager.updateCredit(accountDetails12, ((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditId());
                                                    ((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).setCreditCardBalance(String.valueOf(valueOf41));
                                                }
                                            }
                                        }
                                    }
                                } else if (TransferRVAdapter.this.transferToAccType.equals("Cash")) {
                                    String cash2 = TransferRVAdapter.this.dataManager.getCash();
                                    if (cash2 == null) {
                                        Toast.makeText(TransferRVAdapter.this.context, "you don't have sufficient cash balance", 0).show();
                                    } else {
                                        Double valueOf42 = Double.valueOf(cash2);
                                        Double valueOf43 = Double.valueOf(editText.getText().toString());
                                        Double valueOf44 = Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount());
                                        Double.valueOf(0.0d);
                                        if (valueOf44.doubleValue() < valueOf43.doubleValue()) {
                                            TransferRVAdapter.this.dataManager.addCash(String.valueOf(Double.valueOf(valueOf42.doubleValue() + Double.valueOf(valueOf43.doubleValue() - valueOf44.doubleValue()).doubleValue())));
                                        } else if (valueOf43.doubleValue() < valueOf44.doubleValue()) {
                                            Double valueOf45 = Double.valueOf(valueOf44.doubleValue() - valueOf43.doubleValue());
                                            if (valueOf42.doubleValue() < valueOf45.doubleValue()) {
                                                Toast.makeText(TransferRVAdapter.this.context, "you don't have sufficient cash balance", 0).show();
                                            } else {
                                                TransferRVAdapter.this.dataManager.addCash(String.valueOf(Double.valueOf(valueOf42.doubleValue() - valueOf45.doubleValue())));
                                            }
                                        }
                                    }
                                }
                            }
                            String format = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(textView.getText().toString()));
                            Transactions transactions = new Transactions();
                            transactions.setAmount(editText.getText().toString());
                            transactions.setFromAccount(TransferRVAdapter.this.transferFromAccount);
                            transactions.setFromAccountId(String.valueOf(TransferRVAdapter.this.transferFromAccId));
                            transactions.setToAccountId(String.valueOf(TransferRVAdapter.this.transferToAccId));
                            transactions.setToAccount(TransferRVAdapter.this.transferToAccount);
                            transactions.setDate(format);
                            transactions.setComment(editText2.getText().toString());
                            TransferRVAdapter.this.dataManager.updateTransaction(transactions, ((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getTransactionId());
                            TransferRVAdapter.this.context.startActivity(new Intent(TransferRVAdapter.this.context, ReportFilterActivity.class));
                            ((Activity) TransferRVAdapter.this.context).finish();
                            TransferRVAdapter.this.notifyItemChanged(MyViewHolders.this.getAdapterPosition());
                            TransferRVAdapter.this.materialDialog_edit.dismiss();
                            Toast.makeText(TransferRVAdapter.this.context, "Data has been successfully updated ", 0).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                TransferRVAdapter.this.materialDialog_edit.setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        TransferRVAdapter.this.materialDialog_edit.dismiss();
                    }
                });
                TransferRVAdapter.this.materialDialog_edit.show();
            }
            if (view.getId() == R.id.imgTransferList_delete) {
                TransferRVAdapter.this.materialDialog_delete = new MaterialDialog(TransferRVAdapter.this.context).setTitle((CharSequence) "Remove Data").setMessage((CharSequence) "Are you sure you want to delete that data?").setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        TransferRVAdapter.this.materialDialog_delete.dismiss();
                        try {
                            if (TransferRVAdapter.this.transferFromAccType.equals("Bank Account")) {
                                for (int i = 0; i < TransferRVAdapter.this.arrayListBank.size(); i++) {
                                    if (((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getBankId() == TransferRVAdapter.this.transferFromAccId) {
                                        Double valueOf = Double.valueOf(Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getInitialBalance()).doubleValue() + Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount()).doubleValue());
                                        AccountDetails accountDetails = new AccountDetails();
                                        accountDetails.setBankName(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getBankName());
                                        accountDetails.setAccountType(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getAccountType());
                                        accountDetails.setBankAccNo(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getBankAccNo());
                                        accountDetails.setInitialBalance(String.valueOf(valueOf));
                                        TransferRVAdapter.this.dataManager.updateBank(accountDetails, ((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i)).getBankId());
                                    }
                                }
                            } else if (TransferRVAdapter.this.transferFromAccType.equals("E-wallet")) {
                                for (int i2 = 0; i2 < TransferRVAdapter.this.arrayListWallet.size(); i2++) {
                                    if (((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).geteWalletId() == TransferRVAdapter.this.transferFromAccId) {
                                        Double valueOf2 = Double.valueOf(Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).getEwalletBalance()).doubleValue() + Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount()).doubleValue());
                                        AccountDetails accountDetails2 = new AccountDetails();
                                        accountDetails2.seteWalletName(((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).geteWalletName());
                                        accountDetails2.setEwalletBalance(String.valueOf(valueOf2));
                                        TransferRVAdapter.this.dataManager.updateWallet(accountDetails2, ((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i2)).geteWalletId());
                                    }
                                }
                            } else if (TransferRVAdapter.this.transferFromAccType.equals("Credit Card")) {
                                for (int i3 = 0; i3 < TransferRVAdapter.this.arrayListCredit.size(); i3++) {
                                    if (((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditId() == TransferRVAdapter.this.transferFromAccId) {
                                        Double valueOf3 = Double.valueOf(Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditCardBalance()).doubleValue() + Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount()).doubleValue());
                                        AccountDetails accountDetails3 = new AccountDetails();
                                        accountDetails3.setCreditCardName(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditCardName());
                                        accountDetails3.setCreditCardNo(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditCardNo());
                                        accountDetails3.setCreditCardBalance(String.valueOf(valueOf3));
                                        TransferRVAdapter.this.dataManager.updateCredit(accountDetails3, ((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i3)).getCreditId());
                                    }
                                }
                            } else if (TransferRVAdapter.this.transferFromAccType.equals("Cash")) {
                                Double valueOf4 = Double.valueOf(Double.valueOf(TransferRVAdapter.this.dataManager.getCash()).doubleValue() + Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount()).doubleValue());
                                new AccountDetails();
                                TransferRVAdapter.this.dataManager.addCash(String.valueOf(valueOf4));
                            }
                            if (TransferRVAdapter.this.transferToAccType.equals("Bank Account")) {
                                for (int i4 = 0; i4 < TransferRVAdapter.this.arrayListBank.size(); i4++) {
                                    if (((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getBankId() == TransferRVAdapter.this.transferToAccId) {
                                        Double valueOf5 = Double.valueOf(Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getInitialBalance()).doubleValue() - Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount()).doubleValue());
                                        AccountDetails accountDetails4 = new AccountDetails();
                                        accountDetails4.setBankName(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getBankName());
                                        accountDetails4.setAccountType(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getAccountType());
                                        accountDetails4.setBankAccNo(((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getBankAccNo());
                                        accountDetails4.setInitialBalance(String.valueOf(valueOf5));
                                        TransferRVAdapter.this.dataManager.updateBank(accountDetails4, ((AccountDetails) TransferRVAdapter.this.arrayListBank.get(i4)).getBankId());
                                    }
                                }
                            } else if (TransferRVAdapter.this.transferToAccType.equals("E-wallet")) {
                                for (int i5 = 0; i5 < TransferRVAdapter.this.arrayListWallet.size(); i5++) {
                                    if (((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).geteWalletId() == TransferRVAdapter.this.transferToAccId) {
                                        Double valueOf6 = Double.valueOf(Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).getEwalletBalance()).doubleValue() - Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount()).doubleValue());
                                        AccountDetails accountDetails5 = new AccountDetails();
                                        accountDetails5.seteWalletName(((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).geteWalletName());
                                        accountDetails5.setEwalletBalance(String.valueOf(valueOf6));
                                        TransferRVAdapter.this.dataManager.updateWallet(accountDetails5, ((AccountDetails) TransferRVAdapter.this.arrayListWallet.get(i5)).geteWalletId());
                                    }
                                }
                            } else if (TransferRVAdapter.this.transferToAccType.equals("Credit Card")) {
                                for (int i6 = 0; i6 < TransferRVAdapter.this.arrayListCredit.size(); i6++) {
                                    if (((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditId() == TransferRVAdapter.this.transferToAccId) {
                                        Double valueOf7 = Double.valueOf(Double.valueOf(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditCardBalance()).doubleValue() - Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount()).doubleValue());
                                        AccountDetails accountDetails6 = new AccountDetails();
                                        accountDetails6.setCreditCardName(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditCardName());
                                        accountDetails6.setCreditCardNo(((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditCardNo());
                                        accountDetails6.setCreditCardBalance(String.valueOf(valueOf7));
                                        TransferRVAdapter.this.dataManager.updateCredit(accountDetails6, ((AccountDetails) TransferRVAdapter.this.arrayListCredit.get(i6)).getCreditId());
                                    }
                                }
                            } else if (TransferRVAdapter.this.transferToAccType.equals("Cash")) {
                                Double valueOf8 = Double.valueOf(Double.valueOf(TransferRVAdapter.this.dataManager.getCash()).doubleValue() - Double.valueOf(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getAmount()).doubleValue());
                                new AccountDetails();
                                TransferRVAdapter.this.dataManager.addCash(String.valueOf(valueOf8));
                            }
                            TransferRVAdapter.this.dataManager.deleteTransfer(((Transactions) TransferRVAdapter.this.transactionList.get(MyViewHolders.this.getAdapterPosition())).getTransactionId());
                            TransferRVAdapter.this.transactionList.remove(MyViewHolders.this.getAdapterPosition());
                            TransferRVAdapter.this.notifyItemRemoved(MyViewHolders.this.getAdapterPosition());
                            TransferRVAdapter.this.context.startActivity(new Intent(TransferRVAdapter.this.context, ReportFilterActivity.class));
                            ((Activity) TransferRVAdapter.this.context).finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(TransferRVAdapter.this.context, "Data has been successfully deleted ", 0).show();
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        TransferRVAdapter.this.materialDialog_delete.dismiss();
                    }
                });
                TransferRVAdapter.this.materialDialog_delete.show();
            }
        }
    }

    public TransferRVAdapter(Context context2, ArrayList<Transactions> arrayList, ArrayList<AccountDetails> arrayList2) {
        this.context = context2;
        this.transactionList = arrayList;
        this.accountList = arrayList2;
        DaggerApplication.get(context2).getComponent().inject(this);
        getAccountList();
    }

    public MyViewHolders onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolders(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_transferlist, null));
    }

    public void onBindViewHolder(MyViewHolders myViewHolders, int i) {
        String str;
        Calendar.getInstance();
        String str2 = "";
        try {
            str = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(((Transactions) this.transactionList.get(i)).getDate()));
        } catch (Exception e) {
            e.printStackTrace();
            str = str2;
        }
        myViewHolders.tvTransferList_date.setText(str);
        TextView textView = myViewHolders.tvTransferList_amount;
        StringBuilder sb = new StringBuilder();
        sb.append("₹ ");
        sb.append(((Transactions) this.transactionList.get(i)).getAmount());
        textView.setText(sb.toString());
        myViewHolders.tvTransferList_fromAccount.setText(((Transactions) this.transactionList.get(i)).getFromAccount());
        myViewHolders.tvTransferList_toAccount.setText(((Transactions) this.transactionList.get(i)).getToAccount());
        myViewHolders.tvTransferList_comment.setText(((Transactions) this.transactionList.get(i)).getComment());
    }

    public int getItemCount() {
        return this.transactionList.size();
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
