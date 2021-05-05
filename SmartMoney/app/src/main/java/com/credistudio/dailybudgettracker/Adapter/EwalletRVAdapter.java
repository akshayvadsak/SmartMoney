package com.credistudio.dailybudgettracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

public class EwalletRVAdapter extends RecyclerView.Adapter<EwalletRVAdapter.MyViewHolders> {
    ArrayList<AccountDetails> accountList;
    Context context;
    @Inject
    DataManager dataManager;
    MaterialDialog materialDialog_delete;
    MaterialDialog materialDialog_edit;

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
                EwalletRVAdapter.this.materialDialog_edit = new MaterialDialog(EwalletRVAdapter.this.context);
                View inflate = ((LayoutInflater) EwalletRVAdapter.this.context.getSystemService("layout_inflater")).inflate(R.layout.item_edit_card_details, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtAdap_walletName);
                final EditText editText2 = (EditText) inflate.findViewById(R.id.edtAdap_walletBalance);
                ((EditText) inflate.findViewById(R.id.edtAdap_cardNumber)).setVisibility(8);
                editText.setText(((AccountDetails) EwalletRVAdapter.this.accountList.get(getAdapterPosition())).geteWalletName());
                editText2.setText(((AccountDetails) EwalletRVAdapter.this.accountList.get(getAdapterPosition())).getEwalletBalance());
                EwalletRVAdapter.this.materialDialog_edit = new MaterialDialog(EwalletRVAdapter.this.context).setTitle((CharSequence) "Edit Account").setContentView(inflate).setCanceledOnTouchOutside(false).setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter name of Wallet");
                        } else if (editText2.getText().equals("")) {
                            editText2.setError("Enter initial balance");
                        } else {
                            AccountDetails accountDetails = new AccountDetails();
                            accountDetails.seteWalletName(editText.getText().toString());
                            accountDetails.setEwalletBalance(editText2.getText().toString());
                            EwalletRVAdapter.this.dataManager.updateWallet(accountDetails, ((AccountDetails) EwalletRVAdapter.this.accountList.get(MyViewHolders.this.getAdapterPosition())).geteWalletId());
                            EwalletRVAdapter.this.notifyItemChanged(MyViewHolders.this.getAdapterPosition());
                            EwalletRVAdapter.this.context.startActivity(new Intent(EwalletRVAdapter.this.context, SettingsActivity.class));
                            ((Activity) EwalletRVAdapter.this.context).finish();
                            EwalletRVAdapter.this.materialDialog_edit.dismiss();
                            Toast.makeText(EwalletRVAdapter.this.context, "Account has been successfully edited ", 0).show();
                        }
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        EwalletRVAdapter.this.materialDialog_edit.dismiss();
                    }
                });
                EwalletRVAdapter.this.materialDialog_edit.show();
            }
            if (view.getId() == R.id.imgAccount_delete) {
                EwalletRVAdapter.this.materialDialog_delete = new MaterialDialog(EwalletRVAdapter.this.context).setTitle((CharSequence) "Remove Account").setMessage((CharSequence) "Are you sure you want to delete that account?").setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        EwalletRVAdapter.this.materialDialog_delete.dismiss();
                        try {
                            EwalletRVAdapter.this.dataManager.deleteWallet(((AccountDetails) EwalletRVAdapter.this.accountList.get(MyViewHolders.this.getAdapterPosition())).geteWalletId());
                            EwalletRVAdapter.this.accountList.remove(MyViewHolders.this.getAdapterPosition());
                            EwalletRVAdapter.this.notifyItemRemoved(MyViewHolders.this.getAdapterPosition());
                            EwalletRVAdapter.this.context.startActivity(new Intent(EwalletRVAdapter.this.context, SettingsActivity.class));
                            ((Activity) EwalletRVAdapter.this.context).finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(EwalletRVAdapter.this.context, "Account has been successfully deleted ", 0).show();
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        EwalletRVAdapter.this.materialDialog_delete.dismiss();
                    }
                });
                EwalletRVAdapter.this.materialDialog_delete.show();
            }
        }
    }

    public EwalletRVAdapter(Context context2, ArrayList<AccountDetails> arrayList) {
        this.context = context2;
        this.accountList = arrayList;
        DaggerApplication.get(context2).getComponent().inject(this);
    }

    public MyViewHolders onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolders(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bank_account, null));
    }

    public void onBindViewHolder(MyViewHolders myViewHolders, int i) {
        TextView textView = myViewHolders.tvBankAdapter_balance;
        StringBuilder sb = new StringBuilder();
        sb.append("₹ ");
        sb.append(((AccountDetails) this.accountList.get(i)).getEwalletBalance());
        textView.setText(sb.toString());
        myViewHolders.tvBankAdapter_name.setText(((AccountDetails) this.accountList.get(i)).geteWalletName());
        myViewHolders.tvBankAdapter_number.setVisibility(8);
        myViewHolders.tvBankAdapter_type.setVisibility(8);
    }

    public int getItemCount() {
        return this.accountList.size();
    }
}
