package com.credistudio.dailybudgettracker.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.Fragment.FragmentReportFilterPager;
import com.credistudio.dailybudgettracker.R;
import com.google.android.gms.ads.InterstitialAd;

import javax.inject.Inject;

public class ReportFilterActivity extends AppCompatActivity {
    @Inject
    DataManager dataManager;
    TextView headingTvExpenseList_pickDate;
    /* access modifiers changed from: private */
    public InterstitialAd mInterstitialAd;
    RecyclerView rcv_expenseList;
    Spinner spinExpenseList_category;
    TabLayout tabs_report;
    Toolbar toolbar_expenseList;
    TextView tvExpenseList_pickDate;
    ViewPager viewpager_report;
    private AdView mAdView;

    private class ReportAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private Context context;
        private String[] tabTitles = {"Expenses", "Income", "Transfer"};

        public int getCount() {
            return 3;
        }

        public ReportAdapter(FragmentManager fragmentManager, Context context2) {
            super(fragmentManager);
            this.context = context2;
        }

        public Fragment getItem(int i) {
            new Bundle().putInt("position", i);
            return FragmentReportFilterPager.newInstance(i);
        }

        public CharSequence getPageTitle(int i) {
            return this.tabTitles[i];
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_report_filter);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.toolbar_expenseList = (Toolbar) findViewById(R.id.toolbar_report);
        this.tabs_report = (TabLayout) findViewById(R.id.tabs_report);
        this.viewpager_report = (ViewPager) findViewById(R.id.viewpager_report);

        setSupportActionBar(this.toolbar_expenseList);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar_expenseList.getNavigationIcon().setTint(getResources().getColor(R.color.white));
        this.toolbar_expenseList.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ReportFilterActivity.this.onBackPressed();
            }
        });
        this.viewpager_report.setPageMargin((int) TypedValue.applyDimension(1, 3.0f, getResources().getDisplayMetrics()));
        this.viewpager_report.setOffscreenPageLimit(1);
        this.viewpager_report.setAdapter(new ReportAdapter(getSupportFragmentManager(), this));
        this.tabs_report.setSelectedTabIndicatorHeight(8);
        this.tabs_report.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        this.tabs_report.setupWithViewPager(this.viewpager_report);
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
    }
}
