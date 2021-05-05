package com.credistudio.dailybudgettracker.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.credistudio.dailybudgettracker.Classes.Category;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.R;
import java.util.ArrayList;
import javax.inject.Inject;
import me.drakeet.materialdialog.MaterialDialog;

public class ExpenseCategoryRVAdapter extends RecyclerView.Adapter<ExpenseCategoryRVAdapter.MyViewHolders> {
    ArrayList<Category> categoryList;
    Context context;
    @Inject
    DataManager dataManager;
    MaterialDialog materialDialog_delete;

    public class MyViewHolders extends RecyclerView.ViewHolder implements OnClickListener {
        ImageView imgItemCM_removeCategory;
        TextView tvItemCM_nameCategory;

        public MyViewHolders(View view) {
            super(view);
            this.imgItemCM_removeCategory = (ImageView) view.findViewById(R.id.imgItemCM_removeCategory);
            this.tvItemCM_nameCategory = (TextView) view.findViewById(R.id.tvItemCM_nameCategory);
            this.imgItemCM_removeCategory.setOnClickListener(this);
        }

        public void onClick(View view) {
            ExpenseCategoryRVAdapter.this.materialDialog_delete = new MaterialDialog(ExpenseCategoryRVAdapter.this.context).setTitle((CharSequence) "Remove Category").setMessage((CharSequence) "Are you sure you want to delete this category?").setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                public void onClick(View view) {
                    ExpenseCategoryRVAdapter.this.materialDialog_delete.dismiss();
                    try {
                        ExpenseCategoryRVAdapter.this.dataManager.deleteExpenseCategory(((Category) ExpenseCategoryRVAdapter.this.categoryList.get(MyViewHolders.this.getAdapterPosition())).getCatId());
                        ExpenseCategoryRVAdapter.this.categoryList.remove(MyViewHolders.this.getAdapterPosition());
                        ExpenseCategoryRVAdapter.this.notifyItemRemoved(MyViewHolders.this.getAdapterPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(ExpenseCategoryRVAdapter.this.context, "Category has been successfully deleted ", 0).show();
                }
            }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                public void onClick(View view) {
                    ExpenseCategoryRVAdapter.this.materialDialog_delete.dismiss();
                }
            });
            ExpenseCategoryRVAdapter.this.materialDialog_delete.show();
        }
    }

    public ExpenseCategoryRVAdapter(ArrayList<Category> arrayList, Context context2) {
        this.categoryList = arrayList;
        this.context = context2;
        DaggerApplication.get(context2).getComponent().inject(this);
    }

    public MyViewHolders onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolders(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_management, null));
    }

    public void onBindViewHolder(MyViewHolders myViewHolders, int i) {
        myViewHolders.tvItemCM_nameCategory.setText(((Category) this.categoryList.get(i)).getCatName());
    }

    public int getItemCount() {
        return this.categoryList.size();
    }
}
