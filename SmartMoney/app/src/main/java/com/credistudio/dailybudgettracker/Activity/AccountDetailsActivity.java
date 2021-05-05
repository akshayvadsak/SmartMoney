package com.credistudio.dailybudgettracker.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.credistudio.dailybudgettracker.Classes.AccountDetails;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.Database.DbImportExport;
import com.credistudio.dailybudgettracker.R;
import java.io.File;
import java.util.ArrayList;
import javax.inject.Inject;
import me.drakeet.materialdialog.MaterialDialog;

public class AccountDetailsActivity extends AppCompatActivity {
    protected static final File DATABASE_DIRECTORY = new File(Environment.getExternalStorageDirectory(), "SmartMoneyman");
    protected static final File IMPORT_FILE = new File(DATABASE_DIRECTORY, "appData.db");
    AccountDetails accountDetails_bank;
    AccountDetails accountDetails_credit;
    AccountDetails accountDetails_debit;
    AccountDetails accountDetails_eWallet;
    String accountType = "";
    Button btnUserDetails_submit;
    @Inject
    DataManager dataManager;
    DbImportExport dbImportExport;
    EditText edtUserDetails_accountNumber;
    EditText edtUserDetails_bankName;
    EditText edtUserDetails_cardBalance;
    EditText edtUserDetails_cardName;
    EditText edtUserDetails_cardNumber;
    EditText edtUserDetails_cashAmount;
    EditText edtUserDetails_debitCardBalance;
    EditText edtUserDetails_debitCardName;
    EditText edtUserDetails_debitCardNumber;
    EditText edtUserDetails_initialBalance;
    EditText edtUserDetails_walletBalance;
    EditText edtUserDetails_walletName;
    MaterialDialog materialDialog_edit;
    MaterialDialog materialDialog_import;
    @Inject
    SharedPreferences sharedPreferences;
    Spinner spinUserDetails_accountType;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent().getIntExtra("Status", 0) == 1) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }
        setContentView((int) R.layout.activity_account_details);
        ((DaggerApplication) getApplication()).getComponent().inject(this);
        this.edtUserDetails_bankName = (EditText) findViewById(R.id.edtUserDetails_bankName);
        this.edtUserDetails_accountNumber = (EditText) findViewById(R.id.edtUserDetails_accountNumber);
        this.edtUserDetails_initialBalance = (EditText) findViewById(R.id.edtUserDetails_initialBalance);
        this.edtUserDetails_walletName = (EditText) findViewById(R.id.edtUserDetails_walletName);
        this.edtUserDetails_walletBalance = (EditText) findViewById(R.id.edtUserDetails_walletBalance);
        this.edtUserDetails_cardName = (EditText) findViewById(R.id.edtUserDetails_cardName);
        this.edtUserDetails_cardBalance = (EditText) findViewById(R.id.edtUserDetails_cardBalance);
        this.edtUserDetails_debitCardName = (EditText) findViewById(R.id.edtUserDetails_debitCardName);
        this.edtUserDetails_debitCardBalance = (EditText) findViewById(R.id.edtUserDetails_debitCardBalance);
        this.edtUserDetails_cashAmount = (EditText) findViewById(R.id.edtUserDetails_cashAmount);
        this.edtUserDetails_cardNumber = (EditText) findViewById(R.id.edtUserDetails_cardNumber);
        this.edtUserDetails_debitCardNumber = (EditText) findViewById(R.id.edtUserDetails_debitCardNumber);
        this.spinUserDetails_accountType = (Spinner) findViewById(R.id.spinUserDetails_accountType);
        this.dbImportExport = new DbImportExport();
        this.btnUserDetails_submit = (Button) findViewById(R.id.btnUserDetails_submit);
        if (!IMPORT_FILE.exists()) {
            Toast.makeText(this, "Data file does not found", 0).show();
        } else {
            this.materialDialog_import = new MaterialDialog(this).setTitle((CharSequence) "Import Data").setMessage((CharSequence) "Do you  want to import previous data?").setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                public void onClick(View view) {
                    AccountDetailsActivity.this.materialDialog_import.dismiss();
                    if (ContextCompat.checkSelfPermission(AccountDetailsActivity.this, "android.permission.READ_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(AccountDetailsActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        Log.e("permission", "granted");
                        DbImportExport dbImportExport = AccountDetailsActivity.this.dbImportExport;
                        DbImportExport.importIntoDb(AccountDetailsActivity.this);
                        DbImportExport dbImportExport2 = AccountDetailsActivity.this.dbImportExport;
                        DbImportExport.restoreDb(AccountDetailsActivity.this);
                        AccountDetailsActivity.this.startActivity(new Intent(AccountDetailsActivity.this, HomeActivity.class));
                        return;
                    }
                    Log.e("permission", "not granted");
                    ActivityCompat.requestPermissions(AccountDetailsActivity.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
                }
            }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                public void onClick(View view) {
                    AccountDetailsActivity.this.materialDialog_import.dismiss();
                }
            });
            this.materialDialog_import.show();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add("Select Account Type");
        arrayList.add("Current Account");
        arrayList.add("Saving Account");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367062, arrayList);
        arrayAdapter.setDropDownViewResource(17367062);
        this.spinUserDetails_accountType.setAdapter(arrayAdapter);
        this.spinUserDetails_accountType.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i != 0) {
                    AccountDetailsActivity.this.accountType = (String) adapterView.getItemAtPosition(i);
                }
            }
        });
        this.btnUserDetails_submit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!AccountDetailsActivity.this.edtUserDetails_bankName.getText().toString().equals("") && !AccountDetailsActivity.this.edtUserDetails_initialBalance.getText().toString().equals("") && !AccountDetailsActivity.this.edtUserDetails_accountNumber.getText().toString().equals("") && AccountDetailsActivity.this.spinUserDetails_accountType.getSelectedItemPosition() != 0) {
                    AccountDetailsActivity.this.accountDetails_bank = new AccountDetails();
                    AccountDetailsActivity.this.accountDetails_bank.setBankName(AccountDetailsActivity.this.edtUserDetails_bankName.getText().toString());
                    AccountDetailsActivity.this.accountDetails_bank.setBankAccNo(AccountDetailsActivity.this.edtUserDetails_accountNumber.getText().toString());
                    AccountDetailsActivity.this.accountDetails_bank.setAccountType(AccountDetailsActivity.this.accountType);
                    AccountDetailsActivity.this.accountDetails_bank.setInitialBalance(AccountDetailsActivity.this.edtUserDetails_initialBalance.getText().toString());
                }
                if (!AccountDetailsActivity.this.edtUserDetails_walletName.getText().toString().equals("") && !AccountDetailsActivity.this.edtUserDetails_walletBalance.getText().toString().equals("")) {
                    AccountDetailsActivity.this.accountDetails_eWallet = new AccountDetails();
                    AccountDetailsActivity.this.accountDetails_eWallet.seteWalletName(AccountDetailsActivity.this.edtUserDetails_walletName.getText().toString());
                    AccountDetailsActivity.this.accountDetails_eWallet.setEwalletBalance(AccountDetailsActivity.this.edtUserDetails_walletBalance.getText().toString());
                }
                if (!AccountDetailsActivity.this.edtUserDetails_cardName.getText().toString().equals("") && !AccountDetailsActivity.this.edtUserDetails_cardName.getText().toString().equals("") && !AccountDetailsActivity.this.edtUserDetails_cardNumber.getText().toString().equals("")) {
                    AccountDetailsActivity.this.accountDetails_credit = new AccountDetails();
                    AccountDetailsActivity.this.accountDetails_credit.setCreditCardName(AccountDetailsActivity.this.edtUserDetails_cardName.getText().toString());
                    AccountDetailsActivity.this.accountDetails_credit.setCreditCardNo(AccountDetailsActivity.this.edtUserDetails_cardNumber.getText().toString());
                    AccountDetailsActivity.this.accountDetails_credit.setCreditCardBalance(AccountDetailsActivity.this.edtUserDetails_cardBalance.getText().toString());
                }
                if (AccountDetailsActivity.this.accountDetails_bank == null && AccountDetailsActivity.this.accountDetails_credit == null && AccountDetailsActivity.this.accountDetails_eWallet == null && AccountDetailsActivity.this.accountDetails_debit == null) {
                    Toast.makeText(AccountDetailsActivity.this, "Enter Atleast One Account Details", 0).show();
                } else if (AccountDetailsActivity.this.edtUserDetails_cashAmount.getText().toString().equals("")) {
                    AccountDetailsActivity.this.materialDialog_edit = new MaterialDialog(AccountDetailsActivity.this).setTitle((CharSequence) "Alert").setMessage((CharSequence) "Your cash balance is entered as 00.00 . Do you want to continue with this balance ?").setCanceledOnTouchOutside(false).setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                        public void onClick(View view) {
                            try {
                                AccountDetailsActivity.this.dataManager.addCash("0");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(AccountDetailsActivity.this, "Your details has been saved", 1).show();
                            AccountDetailsActivity.this.finish();
                            AccountDetailsActivity.this.startActivity(new Intent(AccountDetailsActivity.this, HomeActivity.class));
                        }
                    }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                        public void onClick(View view) {
                            AccountDetailsActivity.this.materialDialog_edit.dismiss();
                        }
                    });
                    AccountDetailsActivity.this.materialDialog_edit.show();
                } else {
                    try {
                        AccountDetailsActivity.this.dataManager.addCash(AccountDetailsActivity.this.edtUserDetails_cashAmount.getText().toString());
                        if (AccountDetailsActivity.this.accountDetails_bank != null) {
                            AccountDetailsActivity.this.dataManager.addBank(AccountDetailsActivity.this.accountDetails_bank);
                            AccountDetailsActivity.this.dataManager.getBank();
                        }
                        if (AccountDetailsActivity.this.accountDetails_credit != null) {
                            AccountDetailsActivity.this.dataManager.addCredit(AccountDetailsActivity.this.accountDetails_credit);
                            AccountDetailsActivity.this.dataManager.getCredit();
                        }
                        if (AccountDetailsActivity.this.accountDetails_eWallet != null) {
                            AccountDetailsActivity.this.dataManager.addWallet(AccountDetailsActivity.this.accountDetails_eWallet);
                            AccountDetailsActivity.this.dataManager.gettWallet();
                        }
                        AccountDetails accountDetails = AccountDetailsActivity.this.accountDetails_debit;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(AccountDetailsActivity.this, "Your details has been saved", 1).show();
                    AccountDetailsActivity.this.finish();
                    AccountDetailsActivity.this.startActivity(new Intent(AccountDetailsActivity.this, HomeActivity.class));
                }
            }
        });
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 2) {
            DbImportExport dbImportExport2 = this.dbImportExport;
            DbImportExport.importIntoDb(this);
            DbImportExport dbImportExport3 = this.dbImportExport;
            DbImportExport.restoreDb(this);
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
