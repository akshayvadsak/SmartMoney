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

public class DebitCardRVAdapter extends RecyclerView.Adapter<DebitCardRVAdapter.MyViewHolders> {
    Context context;
    @Inject
    DataManager dataManager;
    ArrayList<AccountDetails> debitDetailList;
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
                DebitCardRVAdapter.this.materialDialog_edit = new MaterialDialog(DebitCardRVAdapter.this.context);
                View inflate = ((LayoutInflater) DebitCardRVAdapter.this.context.getSystemService("layout_inflater")).inflate(R.layout.item_edit_card_details, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtAdap_walletName);
                final EditText editText2 = (EditText) inflate.findViewById(R.id.edtAdap_walletBalance);
                final EditText editText3 = (EditText) inflate.findViewById(R.id.edtAdap_cardNumber);
                editText.setText(((AccountDetails) DebitCardRVAdapter.this.debitDetailList.get(getAdapterPosition())).getDebitCardName());
                editText2.setText(((AccountDetails) DebitCardRVAdapter.this.debitDetailList.get(getAdapterPosition())).getDebitCardBalance());
                editText3.setText(((AccountDetails) DebitCardRVAdapter.this.debitDetailList.get(getAdapterPosition())).getDebitCardNo());
                DebitCardRVAdapter.this.materialDialog_edit = new MaterialDialog(DebitCardRVAdapter.this.context).setTitle((CharSequence) "Edit Account").setContentView(inflate).setCanceledOnTouchOutside(false).setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter name of Debit card");
                        } else if (editText2.getText().equals("")) {
                            editText2.setError("Enter initial balance");
                        } else if (editText3.getText().equals("")) {
                            editText3.setError("Enter Card number");
                        } else {
                            AccountDetails accountDetails = new AccountDetails();
                            accountDetails.setDebitCardName(editText.getText().toString());
                            accountDetails.setDebitCardBalance(editText2.getText().toString());
                            accountDetails.setDebitCardNo(editText3.getText().toString());
                            DebitCardRVAdapter.this.notifyItemChanged(MyViewHolders.this.getAdapterPosition());
                            DebitCardRVAdapter.this.context.startActivity(new Intent(DebitCardRVAdapter.this.context, SettingsActivity.class));
                            ((Activity) DebitCardRVAdapter.this.context).finish();
                            DebitCardRVAdapter.this.materialDialog_edit.dismiss();
                            Toast.makeText(DebitCardRVAdapter.this.context, "Account has been successfully edited ", 0).show();
                        }
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        DebitCardRVAdapter.this.materialDialog_edit.dismiss();
                    }
                });
                DebitCardRVAdapter.this.materialDialog_edit.show();
            }
            if (view.getId() == R.id.imgAccount_delete) {
                DebitCardRVAdapter.this.materialDialog_delete = new MaterialDialog(DebitCardRVAdapter.this.context).setTitle((CharSequence) "Remove Account").setMessage((CharSequence) "Are you sure you want to delete that account?").setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        DebitCardRVAdapter.this.materialDialog_delete.dismiss();
                        try {
                            DebitCardRVAdapter.this.dataManager.deleteDebitCard(((AccountDetails) DebitCardRVAdapter.this.debitDetailList.get(MyViewHolders.this.getAdapterPosition())).getDebitId());
                            DebitCardRVAdapter.this.debitDetailList.remove(MyViewHolders.this.getAdapterPosition());
                            DebitCardRVAdapter.this.notifyItemRemoved(MyViewHolders.this.getAdapterPosition());
                            DebitCardRVAdapter.this.context.startActivity(new Intent(DebitCardRVAdapter.this.context, SettingsActivity.class));
                            ((Activity) DebitCardRVAdapter.this.context).finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(DebitCardRVAdapter.this.context, "Account has been successfully deleted ", 0).show();
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        DebitCardRVAdapter.this.materialDialog_delete.dismiss();
                    }
                });
                DebitCardRVAdapter.this.materialDialog_delete.show();
            }
        }
    }

    public DebitCardRVAdapter(Context context2, ArrayList<AccountDetails> arrayList) {
        this.context = context2;
        this.debitDetailList = arrayList;
        DaggerApplication.get(context2).getComponent().inject(this);
    }

    public MyViewHolders onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolders(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bank_account, null));
    }

    public void onBindViewHolder(MyViewHolders myViewHolders, int i) {
        myViewHolders.tvBankAdapter_name.setText(((AccountDetails) this.debitDetailList.get(i)).getDebitCardName());
        TextView textView = myViewHolders.tvBankAdapter_balance;
        StringBuilder sb = new StringBuilder();
        sb.append("₹ ");
        sb.append(((AccountDetails) this.debitDetailList.get(i)).getDebitCardBalance());
        textView.setText(sb.toString());
        myViewHolders.tvBankAdapter_number.setText(((AccountDetails) this.debitDetailList.get(i)).getDebitCardNo());
        myViewHolders.tvBankAdapter_type.setVisibility(8);
    }

    public int getItemCount() {
        return this.debitDetailList.size();
    }
}
