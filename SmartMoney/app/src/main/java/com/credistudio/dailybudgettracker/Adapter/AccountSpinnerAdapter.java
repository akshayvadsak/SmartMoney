package com.credistudio.dailybudgettracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.credistudio.dailybudgettracker.Classes.AccountDetails;
import com.credistudio.dailybudgettracker.R;
import java.util.ArrayList;

public class AccountSpinnerAdapter extends ArrayAdapter<AccountDetails> {
    TextView account_bankACType;
    TextView account_text;
    TextView account_type;
    private Activity activity;
    Context context;
    private ArrayList<AccountDetails> data;
    LayoutInflater inflater;
    public Resources res;

    public AccountSpinnerAdapter(@NonNull Context context2, ArrayList<AccountDetails> arrayList, int i) {
        super(context2, i, arrayList);
        this.context = context2;
        this.data = arrayList;
        this.inflater = (LayoutInflater) context2.getSystemService("layout_inflater");
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getCustomView(i, view, viewGroup);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return getCustomView(i, view, viewGroup);
    }

    public View getCustomView(int i, View view, ViewGroup viewGroup) {
        View inflate = this.inflater.inflate(R.layout.item_account, viewGroup, false);
        this.account_text = (TextView) inflate.findViewById(R.id.account_text);
        this.account_type = (TextView) inflate.findViewById(R.id.account_type);
        this.account_bankACType = (TextView) inflate.findViewById(R.id.account_bankACType);
        if (i == 0) {
            Log.e("country Adapter..", "...position 0......");
            this.account_text.setText("Select Account");
        } else if (i > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("...pos..");
            sb.append(i);
            sb.append("......");
            Log.e("country Adapter..", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("...pos..");
            sb2.append(((AccountDetails) this.data.get(i)).getAccountName());
            sb2.append("......");
            Log.e("country Adapter..", sb2.toString());
            if (((AccountDetails) this.data.get(i)).getType().equals("Bank Account")) {
                this.account_bankACType.setVisibility(0);
            } else {
                this.account_bankACType.setVisibility(8);
            }
            this.account_text.setText(((AccountDetails) this.data.get(i)).getAccountName());
            TextView textView = this.account_type;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("(");
            sb3.append(((AccountDetails) this.data.get(i)).getTotalAmount());
            sb3.append(")");
            textView.setText(sb3.toString());
            TextView textView2 = this.account_bankACType;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("(");
            sb4.append(((AccountDetails) this.data.get(i)).getAccountType());
            sb4.append(")");
            textView2.setText(sb4.toString());
        }
        return inflate;
    }
}
