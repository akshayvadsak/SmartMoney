package com.credistudio.dailybudgettracker.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.credistudio.dailybudgettracker.DarkModePrefManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.credistudio.dailybudgettracker.Classes.Expense;
import com.credistudio.dailybudgettracker.Dagger.DaggerApplication;
import com.credistudio.dailybudgettracker.Dagger.DataManager;
import com.credistudio.dailybudgettracker.Database.DbImportExport;
import com.credistudio.dailybudgettracker.Fragment.FragmentHomePager;
import com.credistudio.dailybudgettracker.Interface.UpdateValues;
import com.credistudio.dailybudgettracker.MainActivity;
import com.credistudio.dailybudgettracker.R;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import me.drakeet.materialdialog.MaterialDialog;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UpdateValues {
    Double amountExpense = Double.valueOf(0.0d);
    Double amountIncome = Double.valueOf(0.0d);
    String currentDate = "";
    @Inject
    DataManager dataManager;
    DbImportExport dbImportExport;
    ArrayList<Expense> expenseList;
    ArrayList<Expense> incomeList;
    MaterialDialog materialDialog_export;
    MaterialDialog materialDialog_import;
    TabLayout tabs_home;
    TextView tvHome_expenseTotal;
    TextView tvHome_incomeTotal;
    UpdateValues updateValues = this;
    ViewPager viewpager_home;
    private InterstitialAd mInterstitialAd;
    private BottomNavigationView bottomNavigationView;
    private AdView mAdView;


    private class DefaultAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private Context context;
        private String[] tabTitles = {"Expenses", "Income", "Transfer"};

        public int getCount() {
            return 3;
        }

        public DefaultAdapter(FragmentManager fragmentManager, Context context2) {
            super(fragmentManager);
            this.context = context2;
        }

        public Fragment getItem(int i) {
            new Bundle().putInt("position", i);
            return FragmentHomePager.newInstance(i, HomeActivity.this.updateValues);
        }

        public CharSequence getPageTitle(int i) {
            return this.tabTitles[i];
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
//                case R.id.home:
//                    Intent myIntent5 = new Intent(HomeActivity.this, HomeActivity.class);
//                    HomeActivity.this.startActivity(myIntent5);
//                    return true;
                case R.id.excharet:
                    Intent myIntent = new Intent(HomeActivity.this, PieChartActivity.class);
                    HomeActivity.this.startActivity(myIntent);
                    mInterstitialAd.show();

                    return true;
                case R.id.inchart:
                    Intent myIntent1 = new Intent(HomeActivity.this, pieChartIncomeActivity.class);
                    HomeActivity.this.startActivity(myIntent1);
                    return true;
                case R.id.report:
                    Intent myIntent2 = new Intent(HomeActivity.this, ReportFilterActivity.class);
                    HomeActivity.this.startActivity(myIntent2);
                    mInterstitialAd.show();

                    return true;

                case R.id.setting:
                    Intent myIntent3 = new Intent(HomeActivity.this, MainActivity.class);
                    HomeActivity.this.startActivity(myIntent3);

                    return true;
//                case R.id.menu:
//                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//                    drawer.openDrawer(GravityCompat.START);
//                    return true;
            }
            return false;
        }
    };

//    private void loadFragment(Fragment fragment) {
//        // load fragment
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.viewpager_home, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }




    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_home);
        setDarkMode(getWindow());
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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new CoordinatorLayout.Behavior() {
        });

//        bottomNavigationView.setSelectedItemId(R.id.home);




//        toolbar.inflateMenu(R.menu.home);
        this.expenseList = new ArrayList<>();
        this.incomeList = new ArrayList<>();
        this.dbImportExport = new DbImportExport();
        this.tabs_home = (TabLayout) findViewById(R.id.tabs_home);
        this.viewpager_home = (ViewPager) findViewById(R.id.viewpager_home);
        this.tvHome_incomeTotal = (TextView) findViewById(R.id.tvHome_incomeTotal);
        this.tvHome_expenseTotal = (TextView) findViewById(R.id.tvHome_expenseTotal);
        ((DaggerApplication) getApplication()).getComponent().inject(this);
        Date time = Calendar.getInstance().getTime();
        PrintStream printStream = System.out;
        StringBuilder sb = new StringBuilder();
        sb.append("Current time => ");
        sb.append(time);
        printStream.println(sb.toString());
        this.currentDate = new SimpleDateFormat("yyyy-MM-dd").format(time);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(".............");
        sb2.append(this.currentDate);
        sb2.append("........<<<<<<<<<<<<<<<<currentDate>>>>>>>>>>>>>>>....");
        Log.e("Home Activity", sb2.toString());
        try {
            this.expenseList = this.dataManager.getExpense();
            this.incomeList = this.dataManager.getIncome();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!(this.expenseList == null || this.expenseList.size() == 0)) {
            Double.valueOf(0.0d);
            for (int i = 0; i < this.expenseList.size(); i++) {
                if (this.currentDate.equals(((Expense) this.expenseList.get(i)).getDate())) {
                    Double valueOf = Double.valueOf(((Expense) this.expenseList.get(i)).getAmount());
                    if (this.amountExpense.doubleValue() == 0.0d) {
                        this.amountExpense = valueOf;
                    } else {
                        this.amountExpense = Double.valueOf(this.amountExpense.doubleValue() + valueOf.doubleValue());
                    }
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(".............");
                sb3.append(this.amountExpense);
                sb3.append("............");
                Log.e("Exepense Total", sb3.toString());
            }
            TextView textView = this.tvHome_expenseTotal;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("₹ ");
            sb4.append(this.amountExpense);
            textView.setText(sb4.toString());
        }
        if (!(this.incomeList == null || this.incomeList.size() == 0)) {
            Double.valueOf(0.0d);
            for (int i2 = 0; i2 < this.incomeList.size(); i2++) {
                if (this.currentDate.equals(((Expense) this.incomeList.get(i2)).getDate())) {
                    Double valueOf2 = Double.valueOf(((Expense) this.incomeList.get(i2)).getAmount());
                    if (this.amountIncome.doubleValue() == 0.0d) {
                        this.amountIncome = valueOf2;
                    } else {
                        this.amountIncome = Double.valueOf(this.amountIncome.doubleValue() + valueOf2.doubleValue());
                    }
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(".............");
                    sb5.append(this.amountIncome);
                    sb5.append("............");
                    Log.e("Income Total", sb5.toString());
                }
            }
            TextView textView2 = this.tvHome_incomeTotal;
            StringBuilder sb6 = new StringBuilder();
            sb6.append("₹ ");
            sb6.append(this.amountIncome);
            textView2.setText(sb6.toString());
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.nav_notification) {
                    HomeActivity.this.startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
                    mInterstitialAd.show();

//                } else if (item.getItemId() == R.id.nav_report) {
//                    HomeActivity.this.startActivity(new Intent(HomeActivity.this, ReportFilterActivity.class));
//                    mInterstitialAd.show();
                }
                return false;
            }
//
        });
        this.viewpager_home.setPageMargin((int) TypedValue.applyDimension(1, 3.0f, getResources().getDisplayMetrics()));
        this.viewpager_home.setOffscreenPageLimit(1);
        this.viewpager_home.setAdapter(new DefaultAdapter(getSupportFragmentManager(), this));
        this.tabs_home.setSelectedTabIndicatorHeight(8);
        this.tabs_home.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        this.tabs_home.setupWithViewPager(this.viewpager_home);
    }


    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
            super.onBackPressed();
        }
//    }




    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_report) {
            finish();
            startActivity(new Intent(this, ReportFilterActivity.class));
        } else if (itemId == R.id.nav_setting) {
            Log.e("TAG", "The interstitial wasn't loaded yet.====================================================");
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
        } else if (itemId == R.id.nav_graph) {
            finish();
            startActivity(new Intent(this, PieChartActivity.class));
        } else if (itemId == R.id.nav_notification) {
            finish();
            startActivity(new Intent(this, NotificationActivity.class));
        } else if (itemId == R.id.nav_catManagement) {
            finish();
            startActivity(new Intent(this, CategoryManagementActivity.class));
        } else if (itemId == R.id.nav_Export) {
            this.materialDialog_export = new MaterialDialog(this).setTitle((CharSequence) "Export Data").setMessage((CharSequence) "Do you  want to backup data and export to your device ?").setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                @SuppressLint("WrongConstant")
                public void onClick(View view) {
                    HomeActivity.this.materialDialog_export.dismiss();
                    if (ContextCompat.checkSelfPermission(HomeActivity.this, "android.permission.READ_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(HomeActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        Log.e("permission", "granted");
                        DbImportExport dbImportExport = HomeActivity.this.dbImportExport;
                        DbImportExport.exportDb(HomeActivity.this);
                        Toast.makeText(HomeActivity.this, "Data has been save to your device ", 0).show();
                        return;
                    }
                    Log.e("permission", "not granted");
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                }
            }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                public void onClick(View view) {
                    HomeActivity.this.materialDialog_export.dismiss();
                }
            });
            this.materialDialog_export.show();
        } else if (itemId == R.id.nav_Import) {
            this.materialDialog_import = new MaterialDialog(this).setTitle((CharSequence) "Import Data").setMessage((CharSequence) "Do you  want to import previous data?").setPositiveButton("OK", (OnClickListener) new OnClickListener() {
                public void onClick(View view) {
                    HomeActivity.this.materialDialog_import.dismiss();
                    if (ContextCompat.checkSelfPermission(HomeActivity.this, "android.permission.READ_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(HomeActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        Log.e("permission", "granted");
                        DbImportExport dbImportExport = HomeActivity.this.dbImportExport;
                        DbImportExport.importIntoDb(HomeActivity.this);
                        DbImportExport dbImportExport2 = HomeActivity.this.dbImportExport;
                        DbImportExport.restoreDb(HomeActivity.this);
                        return;
                    }
                    Log.e("permission", "not granted");
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
                }
            }).setNegativeButton("Cancel", (OnClickListener) new OnClickListener() {
                public void onClick(View view) {
                    HomeActivity.this.materialDialog_import.dismiss();
                }
            });
            this.materialDialog_import.show();
        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateIncExp(Double d, Double d2, String str) {
        if (this.currentDate.equals(str)) {
            if (d.doubleValue() == 0.0d) {
                this.amountIncome = Double.valueOf(this.amountIncome.doubleValue() + d2.doubleValue());
                TextView textView = this.tvHome_incomeTotal;
                StringBuilder sb = new StringBuilder();
                sb.append("₹ ");
                sb.append(this.amountIncome);
                textView.setText(sb.toString());
            }
            if (d2.doubleValue() == 0.0d) {
                this.amountExpense = Double.valueOf(this.amountExpense.doubleValue() + d.doubleValue());
                TextView textView2 = this.tvHome_expenseTotal;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("₹ ");
                sb2.append(this.amountExpense);
                textView2.setText(sb2.toString());
            }
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1) {
            DbImportExport dbImportExport2 = this.dbImportExport;
            DbImportExport.exportDb(this);
        }
        if (i == 2) {
            DbImportExport dbImportExport3 = this.dbImportExport;
            DbImportExport.importIntoDb(this);
            DbImportExport dbImportExport4 = this.dbImportExport;
            DbImportExport.restoreDb(this);
        }
    }

    public void setDarkMode(Window window) {
        if (new DarkModePrefManager(this).isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            changeStatusBar(0, window);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            changeStatusBar(1, window);
        }
    }

    public void changeStatusBar(int mode, Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.contentBodyColor));
            //white mode
            if (mode == 1) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

    }
}
