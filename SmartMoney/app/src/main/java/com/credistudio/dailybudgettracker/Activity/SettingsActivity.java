package com.credistudio.dailybudgettracker.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.credistudio.dailybudgettracker.Adapter.BankRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.CreditCardRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.DebitCardRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.EwalletRVAdapter;
import com.credistudio.dailybudgettracker.Classes.AccountDetails;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.MainActivity;
import com.credistudio.dailybudgettracker.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import javax.inject.Inject;
import me.drakeet.materialdialog.MaterialDialog;

public class SettingsActivity extends AppCompatActivity {
    String accountType = "";
    String amount = "00";
    ArrayList<AccountDetails> arrayListBank;
    ArrayList<AccountDetails> arrayListCredit;
    ArrayList<AccountDetails> arrayListDebit;
    ArrayList<AccountDetails> arrayListWallet;
    @Inject
    DataManager dataManager;
    ImageView imgSetting_bankAdd;
    ImageView imgSetting_creditAdd;
    ImageView imgSetting_debitAdd;
    ImageView imgSetting_lockArrow;
    ImageView imgSetting_walletAdd;
    /* access modifiers changed from: private */
    public InterstitialAd mInterstitialAd;
    MaterialDialog materialDialog_accountAdd;
    MaterialDialog materialDialog_bankadd;
    RecyclerView rcvSetting_bank;
    RecyclerView rcvSetting_creditCard;
    RecyclerView rcvSetting_debitCard;
    RecyclerView rcvSetting_eWallet;
    Toolbar toolbar_setting;
    TextView tvSetting_cashAmount;
    TextView tvSetting_lock;
    private AdView mAdView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_settings);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.intersatital));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        ((DaggerApplication) getApplication()).getComponent().inject(this);
        this.arrayListBank = new ArrayList<>();
        this.arrayListWallet = new ArrayList<>();
        this.arrayListCredit = new ArrayList<>();
        this.arrayListDebit = new ArrayList<>();
        this.toolbar_setting = (Toolbar) findViewById(R.id.toolbar_setting);
        setSupportActionBar(this.toolbar_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.toolbar_setting.getNavigationIcon().setTint(getResources().getColor(R.color.white));
        }
        this.toolbar_setting.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.this.onBackPressed();
            }
        });
        this.tvSetting_lock = (TextView) findViewById(R.id.tvSetting_lock);
        this.rcvSetting_bank = (RecyclerView) findViewById(R.id.rcvSetting_bank);
        this.rcvSetting_eWallet = (RecyclerView) findViewById(R.id.rcvSetting_eWallet);
        this.rcvSetting_creditCard = (RecyclerView) findViewById(R.id.rcvSetting_creditCard);
        this.rcvSetting_debitCard = (RecyclerView) findViewById(R.id.rcvSetting_debitCard);
        this.imgSetting_lockArrow = (ImageView) findViewById(R.id.imgSetting_lockArrow);
        this.imgSetting_bankAdd = (ImageView) findViewById(R.id.imgSetting_bankAdd);
        this.imgSetting_walletAdd = (ImageView) findViewById(R.id.imgSetting_walletAdd);
        this.imgSetting_creditAdd = (ImageView) findViewById(R.id.imgSetting_creditAdd);
        this.imgSetting_debitAdd = (ImageView) findViewById(R.id.imgSetting_debitAdd);
        this.tvSetting_cashAmount = (TextView) findViewById(R.id.tvSetting_cashAmount);

        this.imgSetting_lockArrow.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.this.finish();
                Intent intent = new Intent(SettingsActivity.this, LockSettingActivity.class);
                intent.putExtra("LockStatus", 0);
                SettingsActivity.this.startActivity(intent);
                mInterstitialAd.show();
            }
        });
        this.tvSetting_lock.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.this.imgSetting_lockArrow.performClick();
            }
        });
        try {
            this.arrayListBank = this.dataManager.getBank();
            this.arrayListWallet = this.dataManager.gettWallet();
            this.arrayListCredit = this.dataManager.getCredit();
            this.amount = this.dataManager.getCash();
            TextView textView = this.tvSetting_cashAmount;
            StringBuilder sb = new StringBuilder();
            sb.append("₹ ");
            sb.append(this.amount);
            textView.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        getAccounts();
        this.imgSetting_bankAdd.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                @SuppressLint("WrongConstant") View inflate = ((LayoutInflater) SettingsActivity.this.getSystemService("layout_inflater")).inflate(R.layout.item_edit_bankaccount, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtAdap_bankName);
                final EditText editText2 = (EditText) inflate.findViewById(R.id.edtAdap_accountNumber);
                final Spinner spinner = (Spinner) inflate.findViewById(R.id.spinAdap_accountType);
                final EditText editText3 = (EditText) inflate.findViewById(R.id.edtAdap_initialBalance);
                ArrayList arrayList = new ArrayList();
                arrayList.add("Select Account Type");
                arrayList.add("Current Account");
                arrayList.add("Saving Account");
                @SuppressLint("ResourceType") ArrayAdapter arrayAdapter = new ArrayAdapter(SettingsActivity.this, 17367062, arrayList);
                arrayAdapter.setDropDownViewResource(17367062);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        if (i != 0) {
                            SettingsActivity.this.accountType = (String) adapterView.getItemAtPosition(i);
                        }
                    }
                });
                SettingsActivity settingsActivity = SettingsActivity.this;
                OnClickListener r2 = new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter name of bank");
                        } else if (editText2.getText().toString().equals("")) {
                            editText2.setError("Enter account Number");
                        } else if (editText3.getText().equals("")) {
                            editText3.setError("Enter initial balance");
                        } else if (spinner.getSelectedItemPosition() == 0) {
                            Toast.makeText(SettingsActivity.this, "Choose one account type", 0);
                        } else {
                            AccountDetails accountDetails = new AccountDetails();
                            accountDetails.setBankName(editText.getText().toString());
                            accountDetails.setBankAccNo(editText2.getText().toString());
                            accountDetails.setInitialBalance(editText3.getText().toString());
                            accountDetails.setAccountType(SettingsActivity.this.accountType);
                            try {
                                SettingsActivity.this.dataManager.addBank(accountDetails);
                                if (SettingsActivity.this.arrayListBank == null) {
                                    SettingsActivity.this.arrayListBank = new ArrayList<>();
                                    SettingsActivity.this.arrayListBank.add(0, accountDetails);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SettingsActivity.this);
                                    linearLayoutManager.setOrientation(1);
                                    SettingsActivity.this.rcvSetting_bank.setLayoutManager(linearLayoutManager);
                                    SettingsActivity.this.rcvSetting_bank.setAdapter(new BankRVAdapter(SettingsActivity.this, SettingsActivity.this.arrayListBank));
                                } else {
                                    SettingsActivity.this.arrayListBank.add(SettingsActivity.this.arrayListBank.size(), accountDetails);
                                }
                                SettingsActivity.this.materialDialog_bankadd.dismiss();
                                Toast.makeText(SettingsActivity.this, "Account has been successfully added ", 0).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                settingsActivity.materialDialog_bankadd = new MaterialDialog(SettingsActivity.this).setTitle((CharSequence) "Add Account").setContentView(inflate).setCanceledOnTouchOutside(false).setPositiveButton("OK", (OnClickListener) r2).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        SettingsActivity.this.materialDialog_bankadd.dismiss();
                    }
                });
                SettingsActivity.this.materialDialog_bankadd.show();
            }
        });
        this.imgSetting_walletAdd.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                View inflate = ((LayoutInflater) SettingsActivity.this.getSystemService("layout_inflater")).inflate(R.layout.item_edit_card_details, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtAdap_walletName);
                final EditText editText2 = (EditText) inflate.findViewById(R.id.edtAdap_walletBalance);
                ((EditText) inflate.findViewById(R.id.edtAdap_cardNumber)).setVisibility(8);
                SettingsActivity.this.materialDialog_accountAdd = new MaterialDialog(SettingsActivity.this).setTitle((CharSequence) "Add Account").setContentView(inflate).setCanceledOnTouchOutside(false).setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter name of E-wallet");
                        } else if (editText2.getText().equals("")) {
                            editText2.setError("Enter initial balance");
                        } else {
                            AccountDetails accountDetails = new AccountDetails();
                            accountDetails.seteWalletName(editText.getText().toString());
                            accountDetails.setEwalletBalance(editText2.getText().toString());
                            try {
                                SettingsActivity.this.dataManager.addWallet(accountDetails);
                                if (SettingsActivity.this.arrayListWallet == null) {
                                    SettingsActivity.this.arrayListWallet = new ArrayList<>();
                                    SettingsActivity.this.arrayListWallet.add(0, accountDetails);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SettingsActivity.this);
                                    linearLayoutManager.setOrientation(1);
                                    SettingsActivity.this.rcvSetting_eWallet.setLayoutManager(linearLayoutManager);
                                    SettingsActivity.this.rcvSetting_eWallet.setAdapter(new EwalletRVAdapter(SettingsActivity.this, SettingsActivity.this.arrayListWallet));
                                } else {
                                    SettingsActivity.this.arrayListWallet.add(SettingsActivity.this.arrayListWallet.size(), accountDetails);
                                }
                                SettingsActivity.this.materialDialog_accountAdd.dismiss();
                                Toast.makeText(SettingsActivity.this, "Account has been successfully added ", 0).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        SettingsActivity.this.materialDialog_accountAdd.dismiss();
                    }
                });
                SettingsActivity.this.materialDialog_accountAdd.show();
            }
        });
        this.imgSetting_creditAdd.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                View inflate = ((LayoutInflater) SettingsActivity.this.getSystemService("layout_inflater")).inflate(R.layout.item_edit_card_details, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtAdap_walletName);
                final EditText editText2 = (EditText) inflate.findViewById(R.id.edtAdap_walletBalance);
                final EditText editText3 = (EditText) inflate.findViewById(R.id.edtAdap_cardNumber);
                SettingsActivity.this.materialDialog_accountAdd = new MaterialDialog(SettingsActivity.this).setTitle((CharSequence) "Add Account").setContentView(inflate).setCanceledOnTouchOutside(false).setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter name of credit card");
                        } else if (editText2.getText().equals("")) {
                            editText2.setError("Enter initial balance");
                        } else if (editText3.getText().equals("")) {
                            editText3.setError("Enter Card number");
                        } else {
                            AccountDetails accountDetails = new AccountDetails();
                            accountDetails.setCreditCardName(editText.getText().toString());
                            accountDetails.setCreditCardBalance(editText2.getText().toString());
                            accountDetails.setCreditCardNo(editText3.getText().toString());
                            try {
                                SettingsActivity.this.dataManager.addCredit(accountDetails);
                                if (SettingsActivity.this.arrayListCredit == null) {
                                    SettingsActivity.this.arrayListCredit = new ArrayList<>();
                                    SettingsActivity.this.arrayListCredit.add(0, accountDetails);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SettingsActivity.this);
                                    linearLayoutManager.setOrientation(1);
                                    SettingsActivity.this.rcvSetting_creditCard.setLayoutManager(linearLayoutManager);
                                    SettingsActivity.this.rcvSetting_creditCard.setAdapter(new CreditCardRVAdapter(SettingsActivity.this, SettingsActivity.this.arrayListCredit));
                                } else {
                                    SettingsActivity.this.arrayListCredit.add(SettingsActivity.this.arrayListCredit.size(), accountDetails);
                                }
                                SettingsActivity.this.materialDialog_accountAdd.dismiss();
                                Toast.makeText(SettingsActivity.this, "Account has been successfully added ", 0).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        SettingsActivity.this.materialDialog_accountAdd.dismiss();
                    }
                });
                SettingsActivity.this.materialDialog_accountAdd.show();
            }
        });
        this.imgSetting_debitAdd.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                View inflate = ((LayoutInflater) SettingsActivity.this.getSystemService("layout_inflater")).inflate(R.layout.item_edit_card_details, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtAdap_walletName);
                final EditText editText2 = (EditText) inflate.findViewById(R.id.edtAdap_walletBalance);
                final EditText editText3 = (EditText) inflate.findViewById(R.id.edtAdap_cardNumber);
                SettingsActivity.this.materialDialog_accountAdd = new MaterialDialog(SettingsActivity.this).setTitle((CharSequence) "Add Account").setContentView(inflate).setCanceledOnTouchOutside(false).setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter name of debit card");
                        } else if (editText2.getText().equals("")) {
                            editText2.setError("Enter initial balance");
                        } else if (editText3.getText().equals("")) {
                            editText3.setError("Enter Card number");
                        } else {
                            AccountDetails accountDetails = new AccountDetails();
                            accountDetails.setDebitCardName(editText.getText().toString());
                            accountDetails.setDebitCardBalance(editText2.getText().toString());
                            accountDetails.setDebitCardNo(editText3.getText().toString());
                            try {
                                if (SettingsActivity.this.arrayListDebit == null) {
                                    SettingsActivity.this.arrayListDebit = new ArrayList<>();
                                    SettingsActivity.this.arrayListDebit.add(0, accountDetails);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SettingsActivity.this);
                                    linearLayoutManager.setOrientation(1);
                                    SettingsActivity.this.rcvSetting_debitCard.setLayoutManager(linearLayoutManager);
                                    SettingsActivity.this.rcvSetting_debitCard.setAdapter(new DebitCardRVAdapter(SettingsActivity.this, SettingsActivity.this.arrayListDebit));
                                } else {
                                    SettingsActivity.this.arrayListDebit.add(SettingsActivity.this.arrayListDebit.size(), accountDetails);
                                }
                                SettingsActivity.this.materialDialog_accountAdd.dismiss();
                                Toast.makeText(SettingsActivity.this, "Account has been successfully added ", 0).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        SettingsActivity.this.materialDialog_accountAdd.dismiss();
                    }
                });
                SettingsActivity.this.materialDialog_accountAdd.show();
            }
        });
    }

    public void getAccounts() {
        if (!(this.arrayListBank == null || this.arrayListBank.size() == 0)) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(1);
            this.rcvSetting_bank.setLayoutManager(linearLayoutManager);
            this.rcvSetting_bank.setAdapter(new BankRVAdapter(this, this.arrayListBank));
        }
        if (!(this.arrayListWallet == null || this.arrayListWallet.size() == 0)) {
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
            linearLayoutManager2.setOrientation(1);
            this.rcvSetting_eWallet.setLayoutManager(linearLayoutManager2);
            this.rcvSetting_eWallet.setAdapter(new EwalletRVAdapter(this, this.arrayListWallet));
        }
        if (!(this.arrayListCredit == null || this.arrayListCredit.size() == 0)) {
            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
            linearLayoutManager3.setOrientation(1);
            this.rcvSetting_creditCard.setLayoutManager(linearLayoutManager3);
            this.rcvSetting_creditCard.setAdapter(new CreditCardRVAdapter(this, this.arrayListCredit));
        }
        if (this.arrayListDebit != null && this.arrayListDebit.size() != 0) {
            LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this);
            linearLayoutManager4.setOrientation(1);
            this.rcvSetting_debitCard.setLayoutManager(linearLayoutManager4);
            this.rcvSetting_debitCard.setAdapter(new DebitCardRVAdapter(this, this.arrayListDebit));
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}
