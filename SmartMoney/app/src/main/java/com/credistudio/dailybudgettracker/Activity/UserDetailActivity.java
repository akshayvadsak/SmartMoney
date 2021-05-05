package com.credistudio.dailybudgettracker.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.credistudio.dailybudgettracker.Classes.Category;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.R;
import com.credistudio.dailybudgettracker.ServiceUtility.Util;
import java.util.ArrayList;
import javax.inject.Inject;

public class UserDetailActivity extends AppCompatActivity {
    Button btnUserDetails_submit;
    ArrayList<Category> categoryArrayList;
    ArrayList<Category> categoryListIncome;
    @Inject
    DataManager dataManager;
    EditText edtUserDetail_name;
    @Inject
    SharedPreferences sharedPreferences;
    String userName = "";
    Util util;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((DaggerApplication) getApplication()).getComponent().inject(this);
        this.util = new Util();
        this.userName = this.sharedPreferences.getString("UserName", "");
        if (!this.userName.equals("")) {
            finish();
            startActivity(new Intent(this, SplashLockActivity.class));
            return;
        }
        setContentView((int) R.layout.activity_user_detail);
        getCategory();
        this.btnUserDetails_submit = (Button) findViewById(R.id.btnUserDetail_submit);
        this.edtUserDetail_name = (EditText) findViewById(R.id.edtUserDetail_name);
        this.btnUserDetails_submit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (UserDetailActivity.this.edtUserDetail_name.getText().toString().equals("")) {
                    Toast.makeText(UserDetailActivity.this, "Enter user name", 1).show();
                    return;
                }
                UserDetailActivity.this.util.showDialog(UserDetailActivity.this);
                UserDetailActivity.this.sharedPreferences.edit().putString("UserName", UserDetailActivity.this.edtUserDetail_name.getText().toString().trim()).apply();
                UserDetailActivity.this.util.dismissDialog();
                UserDetailActivity.this.finish();
                UserDetailActivity.this.startActivity(new Intent(UserDetailActivity.this, SplashLockActivity.class));
            }
        });
    }

    public void getCategory() {
        this.categoryArrayList = new ArrayList<>();
        this.categoryListIncome = new ArrayList<>();
        Category category = new Category();
        category.setCatName("Advertising");
        this.categoryArrayList.add(category);
        Category category2 = new Category();
        category2.setCatName("Bank and credit card Interest");
        this.categoryArrayList.add(category2);
        Category category3 = new Category();
        category3.setCatName("Car and Truck");
        this.categoryArrayList.add(category3);
        Category category4 = new Category();
        category4.setCatName("Comissions and Fees");
        this.categoryArrayList.add(category4);
        Category category5 = new Category();
        category5.setCatName("Contract Labour");
        this.categoryArrayList.add(category5);
        Category category6 = new Category();
        category6.setCatName("Contributions");
        this.categoryArrayList.add(category6);
        Category category7 = new Category();
        category7.setCatName("Depreciation");
        this.categoryArrayList.add(category7);
        Category category8 = new Category();
        category8.setCatName("Dividend Payments");
        this.categoryArrayList.add(category8);
        Category category9 = new Category();
        category9.setCatName("Employee Benefit Programs");
        this.categoryArrayList.add(category9);
        Category category10 = new Category();
        category10.setCatName("Entertainment");
        this.categoryArrayList.add(category10);
        Category category11 = new Category();
        category11.setCatName("Gift");
        this.categoryArrayList.add(category11);
        Category category12 = new Category();
        category12.setCatName("Insurance");
        this.categoryArrayList.add(category12);
        Category category13 = new Category();
        category13.setCatName("Legal, Accountant and Other Professional Services");
        this.categoryArrayList.add(category13);
        Category category14 = new Category();
        category14.setCatName("Meals");
        this.categoryArrayList.add(category14);
        Category category15 = new Category();
        category15.setCatName("Mortgage Interest");
        this.categoryArrayList.add(category15);
        Category category16 = new Category();
        category16.setCatName("Non-Deductible Expense");
        this.categoryArrayList.add(category16);
        Category category17 = new Category();
        category17.setCatName("Office");
        this.categoryArrayList.add(category17);
        Category category18 = new Category();
        category18.setCatName("Other Business Property Leasing");
        this.categoryArrayList.add(category18);
        Category category19 = new Category();
        category19.setCatName("Owner Draws");
        this.categoryArrayList.add(category19);
        Category category20 = new Category();
        category20.setCatName("Payroll Taxes");
        this.categoryArrayList.add(category20);
        Category category21 = new Category();
        category21.setCatName("Electricity");
        this.categoryArrayList.add(category21);
        Category category22 = new Category();
        category22.setCatName("Phone");
        this.categoryArrayList.add(category22);
        Category category23 = new Category();
        category23.setCatName("Postage");
        this.categoryArrayList.add(category23);
        Category category24 = new Category();
        category24.setCatName("Rent");
        this.categoryArrayList.add(category24);
        Category category25 = new Category();
        category25.setCatName("Repairs and Maintenance");
        this.categoryArrayList.add(category25);
        Category category26 = new Category();
        category26.setCatName("Supplies");
        this.categoryArrayList.add(category26);
        Category category27 = new Category();
        category27.setCatName("Taxes and Licenses");
        this.categoryArrayList.add(category27);
        Category category28 = new Category();
        category28.setCatName("Transfer Funds");
        this.categoryArrayList.add(category28);
        Category category29 = new Category();
        category29.setCatName("Travel");
        this.categoryArrayList.add(category29);
        Category category30 = new Category();
        category30.setCatName("Utilities");
        this.categoryArrayList.add(category30);
        Category category31 = new Category();
        category31.setCatName("Vehicle, Machinery and Equipment Rental or Leasing");
        this.categoryArrayList.add(category31);
        Category category32 = new Category();
        category32.setCatName("Wages");
        this.categoryArrayList.add(category32);
        Category category33 = new Category();
        category33.setCatName("Food");
        this.categoryArrayList.add(category33);
        Category category34 = new Category();
        category34.setCatName("Health Care");
        this.categoryArrayList.add(category34);
        Category category35 = new Category();
        category35.setCatName("Home");
        this.categoryArrayList.add(category35);
        Category category36 = new Category();
        category36.setCatName("Household");
        this.categoryArrayList.add(category36);
        Category category37 = new Category();
        category37.setCatName("Loans");
        this.categoryArrayList.add(category37);
        Category category38 = new Category();
        category38.setCatName("Salary");
        this.categoryArrayList.add(category38);
        Category category39 = new Category();
        category39.setCatName("Self");
        this.categoryArrayList.add(category39);
        Category category40 = new Category();
        category40.setCatName("Regular Income");
        this.categoryListIncome.add(category40);
        Category category41 = new Category();
        category41.setCatName("Owner Contribution");
        this.categoryListIncome.add(category41);
        Category category42 = new Category();
        category42.setCatName("Interest Income");
        this.categoryListIncome.add(category42);
        Category category43 = new Category();
        category43.setCatName("Expense Refund");
        this.categoryListIncome.add(category43);
        Category category44 = new Category();
        category44.setCatName("Other Income");
        this.categoryListIncome.add(category44);
        Category category45 = new Category();
        category45.setCatName("Salary");
        this.categoryListIncome.add(category45);
        Category category46 = new Category();
        category46.setCatName("Equities");
        this.categoryListIncome.add(category46);
        Category category47 = new Category();
        category47.setCatName("Rent and Royalties");
        this.categoryListIncome.add(category47);
        Category category48 = new Category();
        category48.setCatName("Home equity");
        this.categoryListIncome.add(category48);
        Category category49 = new Category();
        category49.setCatName("Part Time Work");
        this.categoryListIncome.add(category49);
        Category category50 = new Category();
        category50.setCatName("Account Transfer");
        this.categoryListIncome.add(category50);
        Category category51 = new Category();
        category51.setCatName("Selling Software");
        this.categoryListIncome.add(category51);
        Category category52 = new Category();
        category52.setCatName("Software Customization");
        this.categoryListIncome.add(category52);
        Category category53 = new Category();
        category53.setCatName("Money Return");
        this.categoryListIncome.add(category53);
        try {
            this.dataManager.addCategoryList(this.categoryArrayList);
            this.dataManager.addCategoryListIncome(this.categoryListIncome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
