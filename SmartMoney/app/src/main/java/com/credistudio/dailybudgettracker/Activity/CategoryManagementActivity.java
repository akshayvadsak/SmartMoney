package com.credistudio.dailybudgettracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.credistudio.dailybudgettracker.Adapter.ExpenseCategoryRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.IncomeCategoryRVAdapter;
import com.credistudio.dailybudgettracker.Classes.Category;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.MainActivity;
import com.credistudio.dailybudgettracker.R;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.inject.Inject;
import me.drakeet.materialdialog.MaterialDialog;

public class CategoryManagementActivity extends AppCompatActivity {
    @Inject
    DataManager dataManager;
    ArrayList<Category> expenseCategoryList;
    ImageView imgCM_addExpenseCat;
    ImageView imgCM_addIncomeCat;
    ArrayList<Category> incomeCategoryList;
    LinearLayout linCM_expense;
    LinearLayout linCM_expenseAddCat;
    LinearLayout linCM_income;
    LinearLayout linCM_incomeAddCat;
    LinearLayout linCM_mainExpense;
    LinearLayout linCM_mainIncome;
    /* access modifiers changed from: private */
    public InterstitialAd mInterstitialAd;
    MaterialDialog materialDialog_catAdd;
    RecyclerView rcv_catManagementExpense;
    RecyclerView rcv_catManagementIncome;
    ExpenseCategoryRVAdapter rvAdapterExpense;
    IncomeCategoryRVAdapter rvAdapterIncome;
    int statusExpense = 0;
    int statusIncome = 0;
    Toolbar toolbar_categoryManagement;
    private AdView mAdView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_category_management);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ((DaggerApplication) getApplication()).getComponent().inject(this);
        this.imgCM_addExpenseCat = (ImageView) findViewById(R.id.imgCM_addExpenseCat);
        this.imgCM_addIncomeCat = (ImageView) findViewById(R.id.imgCM_addIncomeCat);
        this.linCM_incomeAddCat = (LinearLayout) findViewById(R.id.linCM_incomeAddCat);
        this.linCM_expenseAddCat = (LinearLayout) findViewById(R.id.linCM_expenseAddCat);
        this.linCM_expense = (LinearLayout) findViewById(R.id.linCM_expense);
        this.linCM_income = (LinearLayout) findViewById(R.id.linCM_income);
        this.linCM_mainIncome = (LinearLayout) findViewById(R.id.linCM_mainIncome);
        this.linCM_mainExpense = (LinearLayout) findViewById(R.id.linCM_mainExpense);
        this.toolbar_categoryManagement = (Toolbar) findViewById(R.id.toolbar_categoryManagement);

        setSupportActionBar(this.toolbar_categoryManagement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar_categoryManagement.getNavigationIcon().setTint(getResources().getColor(R.color.white));
        this.toolbar_categoryManagement.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CategoryManagementActivity.this.onBackPressed();
            }
        });
        this.rcv_catManagementExpense = (RecyclerView) findViewById(R.id.rcv_catManagementExpense);
        this.rcv_catManagementIncome = (RecyclerView) findViewById(R.id.rcv_catManagementIncome);
        this.incomeCategoryList = new ArrayList<>();
        this.expenseCategoryList = new ArrayList<>();
        getCategories();
        this.linCM_mainExpense.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (CategoryManagementActivity.this.statusExpense == 0) {
                    CategoryManagementActivity.this.linCM_expense.setVisibility(0);
                    CategoryManagementActivity.this.statusExpense = 1;
                    return;
                }
                CategoryManagementActivity.this.linCM_expense.setVisibility(8);
                CategoryManagementActivity.this.statusExpense = 0;
            }
        });
        this.linCM_mainIncome.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (CategoryManagementActivity.this.statusIncome == 0) {
                    CategoryManagementActivity.this.linCM_income.setVisibility(0);
                    CategoryManagementActivity.this.statusIncome = 1;
                    return;
                }
                CategoryManagementActivity.this.linCM_income.setVisibility(8);
                CategoryManagementActivity.this.statusIncome = 0;
            }
        });
        this.imgCM_addExpenseCat.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                View inflate = ((LayoutInflater) CategoryManagementActivity.this.getSystemService("layout_inflater")).inflate(R.layout.item_add_category, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtAddCat_catName);
                CategoryManagementActivity.this.materialDialog_catAdd = new MaterialDialog(CategoryManagementActivity.this).setTitle((CharSequence) "Add Category").setContentView(inflate).setCanceledOnTouchOutside(false).setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter Category Name");
                            return;
                        }
                        Category category = new Category();
                        category.setCatName(editText.getText().toString());
                        try {
                            CategoryManagementActivity.this.dataManager.addCategory(category);
                            CategoryManagementActivity.this.rvAdapterExpense.notifyDataSetChanged();
                            CategoryManagementActivity.this.materialDialog_catAdd.dismiss();
                            CategoryManagementActivity.this.finish();
                            CategoryManagementActivity.this.startActivity(new Intent(CategoryManagementActivity.this, CategoryManagementActivity.class));
                            Toast.makeText(CategoryManagementActivity.this, "Category has been successfully added ", 0).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        CategoryManagementActivity.this.materialDialog_catAdd.dismiss();
                    }
                });
                CategoryManagementActivity.this.materialDialog_catAdd.show();
            }
        });
        this.linCM_expenseAddCat.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CategoryManagementActivity.this.imgCM_addExpenseCat.performClick();
            }
        });
        this.imgCM_addIncomeCat.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                View inflate = ((LayoutInflater) CategoryManagementActivity.this.getSystemService("layout_inflater")).inflate(R.layout.item_add_category, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.edtAddCat_catName);
                CategoryManagementActivity.this.materialDialog_catAdd = new MaterialDialog(CategoryManagementActivity.this).setTitle((CharSequence) "Add Category").setContentView(inflate).setCanceledOnTouchOutside(false).setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Enter Category Name");
                            return;
                        }
                        Category category = new Category();
                        category.setCatName(editText.getText().toString());
                        try {
                            CategoryManagementActivity.this.dataManager.addCategoryIncome(category);
                            CategoryManagementActivity.this.materialDialog_catAdd.dismiss();
                            CategoryManagementActivity.this.finish();
                            CategoryManagementActivity.this.startActivity(new Intent(CategoryManagementActivity.this, CategoryManagementActivity.class));
                            Toast.makeText(CategoryManagementActivity.this, "Category has been successfully added ", 0).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                    public void onClick(View view) {
                        CategoryManagementActivity.this.materialDialog_catAdd.dismiss();
                    }
                });
                CategoryManagementActivity.this.materialDialog_catAdd.show();
            }
        });
        this.linCM_incomeAddCat.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CategoryManagementActivity.this.imgCM_addIncomeCat.performClick();
            }
        });
    }

    public void getCategories() {
        try {
            this.expenseCategoryList = this.dataManager.getCategory();
            this.incomeCategoryList = this.dataManager.getCategoryIncome();
            if (!(this.expenseCategoryList == null || this.expenseCategoryList.size() == 0)) {
                Collections.sort(this.expenseCategoryList, new Comparator<Category>() {
                    public int compare(Category category, Category category2) {
                        try {
                            return category.getCatName().compareTo(category2.getCatName());
                        } catch (Exception e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                StringBuilder sb = new StringBuilder();
                sb.append("............");
                sb.append(this.expenseCategoryList.size());
                sb.append(".......");
                Log.e("Category size", sb.toString());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(1);
                this.rcv_catManagementExpense.setLayoutManager(linearLayoutManager);
                this.rvAdapterExpense = new ExpenseCategoryRVAdapter(this.expenseCategoryList, this);
                this.rcv_catManagementExpense.setAdapter(this.rvAdapterExpense);
            }
            if (this.incomeCategoryList != null && this.incomeCategoryList.size() != 0) {
                Collections.sort(this.incomeCategoryList, new Comparator<Category>() {
                    public int compare(Category category, Category category2) {
                        try {
                            return category.getCatName().compareTo(category2.getCatName());
                        } catch (Exception e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });
                StringBuilder sb2 = new StringBuilder();
                sb2.append("............");
                sb2.append(this.incomeCategoryList.size());
                sb2.append(".......");
                Log.e("incomeCategoryList size", sb2.toString());
                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
                linearLayoutManager2.setOrientation(1);
                this.rcv_catManagementIncome.setLayoutManager(linearLayoutManager2);
                this.rvAdapterIncome = new IncomeCategoryRVAdapter(this.incomeCategoryList, this);
                this.rcv_catManagementIncome.setAdapter(this.rvAdapterIncome);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}
