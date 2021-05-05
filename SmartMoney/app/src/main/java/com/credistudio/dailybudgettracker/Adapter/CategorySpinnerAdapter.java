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

import com.credistudio.dailybudgettracker.Classes.Category;
import com.credistudio.dailybudgettracker.R;
import java.util.ArrayList;

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {
    private Activity activity;
    Context context;
    private ArrayList<Category> data;
    LayoutInflater inflater;
    public Resources res;
    TextView text;

    public CategorySpinnerAdapter(@NonNull Context context2, int i, @NonNull ArrayList<Category> arrayList) {
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
        this.text = (TextView) inflate.findViewById(R.id.account_text);
        if (i == 0) {
            Log.e("country Adapter..", "...position 0......");
            this.text.setText("Select Category");
        } else if (i > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("...pos..");
            sb.append(i);
            sb.append("......");
            Log.e("category Adapter..", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("...pos..");
            sb2.append(((Category) this.data.get(i)).getCatName());
            sb2.append("......");
            Log.e("category Adapter..", sb2.toString());
            this.text.setText(((Category) this.data.get(i)).getCatName());
        }
        return inflate;
    }
}
