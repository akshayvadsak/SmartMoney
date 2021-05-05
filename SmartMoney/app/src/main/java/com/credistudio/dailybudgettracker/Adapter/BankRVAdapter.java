package com.credistudio.dailybudgettracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.credistudio.dailybudgettracker.Activity.SettingsActivity;
import com.credistudio.dailybudgettracker.Classes.AccountDetails;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.R;
import java.util.ArrayList;
import javax.inject.Inject;
import me.drakeet.materialdialog.MaterialDialog;

public class BankRVAdapter extends RecyclerView.Adapter<BankRVAdapter.MyViewHolders> {
    String accountType = "";
    Context context;
    @Inject
    DataManager dataManager;
    MaterialDialog materialDialog_delete;
    MaterialDialog materialDialog_edit;
    ArrayList<AccountDetails> userBankList;

    public class MyViewHolders extends RecyclerView.ViewHolder implements OnClickListener {
        ImageView imgAccount_delete;
        ImageView imgAccount_edit;
        TextView tvBankAdapter_balance;
        TextView tvBankAdapter_name;
        TextView tvBankAdapter_number;
        TextView tvBankAdapter_type;

        public MyViewHolders(View view) {
            super(view);
            this.imgAccount_edit = (ImageView) view.findViewById(R.id.imgAccount_edit);
            this.imgAccount_delete = (ImageView) view.findViewById(R.id.imgAccount_delete);
            this.tvBankAdapter_name = (TextView) view.findViewById(R.id.tvBankAdapter_name);
            this.tvBankAdapter_balance = (TextView) view.findViewById(R.id.tvBankAdapter_balance);
            this.tvBankAdapter_number = (TextView) view.findViewById(R.id.tvBankAdapter_number);
            this.tvBankAdapter_type = (TextView) view.findViewById(R.id.tvBankAdapter_type);
            this.imgAccount_delete.setOnClickListener(this);
            this.imgAccount_edit.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (view.getId() == R.id.imgAccount_edit) {
                View inflate = ((LayoutInflater) BankRVAdapter.this.context.getSystemService("layout_inflater")).inflate(R.layout.item_edit_bankaccount, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtAdap_bankName);
                final EditText editText2 = (EditText) inflate.findViewById(R.id.edtAdap_initialBalance);
                final EditText editText3 = (EditText) inflate.findViewById(R.id.edtAdap_accountNumber);
                final Spinner spinner = (Spinner) inflate.findViewById(R.id.spinAdap_accountType);
                ArrayList arrayList = new ArrayList();
                arrayList.add("Select Account Type");
                arrayList.add("Current Account");
                arrayList.add("Saving Account");
                ArrayAdapter arrayAdapter = new ArrayAdapter(BankRVAdapter.this.context, 17367062, arrayList);
                arrayAdapter.setDropDownViewResource(17367062);
                spinner.setAdapter(arrayAdapter);
                editText.setText(((AccountDetails) BankRVAdapter.this.userBankList.get(getAdapterPosition())).getBankName());
                editText2.setText(((AccountDetails) BankRVAdapter.this.userBankList.get(getAdapterPosition())).getInitialBalance());
                editText3.setText(((AccountDetails) BankRVAdapter.this.userBankList.get(getAdapterPosition())).getBankAccNo());
                if (((AccountDetails) BankRVAdapter.this.userBankList.get(getAdapterPosition())).getAccountType().equals("Current Account")) {
                    spinner.setSelection(1);
                } else if (((AccountDetails) BankRVAdapter.this.userBankList.get(getAdapterPosition())).getAccountType().equals("Saving Account")) {
                    spinner.setSelection(2);
                }
                spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        if (i != 0) {
                            BankRVAdapter.this.accountType = (String) adapterView.getItemAtPosition(i);
                        }
                    }
                });
                BankRVAdapter bankRVAdapter = BankRVAdapter.this;
                OnClickListener r4 = new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter name of bank");
                        } else if (editText3.getText().toString().equals("")) {
                            editText3.setError("Enter account Number");
                        } else if (editText2.getText().equals("")) {
                            editText2.setError("Enter initial balance");
                        } else if (spinner.getSelectedItemPosition() == 0) {
                            Toast.makeText(BankRVAdapter.this.context, "Choose one account type", 0);
                        } else {
                            AccountDetails accountDetails = new AccountDetails();
                            accountDetails.setBankName(editText.getText().toString());
                            accountDetails.setBankAccNo(editText3.getText().toString());
                            accountDetails.setAccountType(BankRVAdapter.this.accountType);
                            accountDetails.setInitialBalance(editText2.getText().toString());
                            BankRVAdapter.this.dataManager.updateBank(accountDetails, ((AccountDetails) BankRVAdapter.this.userBankList.get(MyViewHolders.this.getAdapterPosition())).getBankId());
                            BankRVAdapter.this.context.startActivity(new Intent(BankRVAdapter.this.context, SettingsActivity.class));
                            ((Activity) BankRVAdapter.this.context).finish();
                            BankRVAdapter.this.notifyItemChanged(MyViewHolders.this.getAdapterPosition());
                            BankRVAdapter.this.materialDialog_edit.dismiss();
                            Toast.makeText(BankRVAdapter.this.context, "Account has been successfully edited ", 0).show();
                        }
                    }
                };
                bankRVAdapter.materialDialog_edit = new MaterialDialog(BankRVAdapter.this.context).setTitle((CharSequence) "Edit Account").setContentView(inflate).setCanceledOnTouchOutside(false).setPositiveButton("OK", (OnClickListener) r4).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        BankRVAdapter.this.materialDialog_edit.dismiss();
                    }
                });
                BankRVAdapter.this.materialDialog_edit.show();
            }
            if (view.getId() == R.id.imgAccount_delete) {
                BankRVAdapter.this.materialDialog_delete = new MaterialDialog(BankRVAdapter.this.context).setTitle((CharSequence) "Remove Account").setMessage((CharSequence) "Are you sure you want to delete that account?").setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        BankRVAdapter.this.materialDialog_delete.dismiss();
                        try {
                            BankRVAdapter.this.dataManager.deleteBank(((AccountDetails) BankRVAdapter.this.userBankList.get(MyViewHolders.this.getAdapterPosition())).getBankId());
                            BankRVAdapter.this.userBankList.remove(MyViewHolders.this.getAdapterPosition());
                            BankRVAdapter.this.notifyItemRemoved(MyViewHolders.this.getAdapterPosition());
                            BankRVAdapter.this.context.startActivity(new Intent(BankRVAdapter.this.context, SettingsActivity.class));
                            ((Activity) BankRVAdapter.this.context).finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(BankRVAdapter.this.context, "Account has been successfully deleted ", 0).show();
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        BankRVAdapter.this.materialDialog_delete.dismiss();
                    }
                });
                BankRVAdapter.this.materialDialog_delete.show();
            }
        }
    }

    public BankRVAdapter(Context context2, ArrayList<AccountDetails> arrayList) {
        this.context = context2;
        this.userBankList = arrayList;
        DaggerApplication.get(context2).getComponent().inject(this);
    }

    public MyViewHolders onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolders(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bank_account, null));
    }

    public void onBindViewHolder(MyViewHolders myViewHolders, int i) {
        myViewHolders.tvBankAdapter_name.setText(((AccountDetails) this.userBankList.get(i)).getBankName());
        TextView textView = myViewHolders.tvBankAdapter_balance;
        StringBuilder sb = new StringBuilder();
        sb.append("₹ ");
        sb.append(((AccountDetails) this.userBankList.get(i)).getInitialBalance());
        textView.setText(sb.toString());
        myViewHolders.tvBankAdapter_number.setText(((AccountDetails) this.userBankList.get(i)).getBankAccNo());
        myViewHolders.tvBankAdapter_type.setText(((AccountDetails) this.userBankList.get(i)).getAccountType());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("............Bank Id ......................");
        sb2.append(((AccountDetails) this.userBankList.get(i)).getBankId());
        sb2.append(".............===================================================");
        Log.e("BankRVAdapter ", sb2.toString());
    }

    public int getItemCount() {
        return this.userBankList.size();
    }
}
