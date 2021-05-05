package com.credistudio.dailybudgettracker.Dagger;

import android.content.Context;
import com.credistudio.dailybudgettracker.Activity.AccountDetailsActivity;
import com.credistudio.dailybudgettracker.Activity.CategoryManagementActivity;
import com.credistudio.dailybudgettracker.Activity.HomeActivity;
import com.credistudio.dailybudgettracker.Activity.NotificationActivity;
import com.credistudio.dailybudgettracker.Activity.PieChartActivity;
import com.credistudio.dailybudgettracker.Activity.ReportFilterActivity;
import com.credistudio.dailybudgettracker.Activity.SettingsActivity;
import com.credistudio.dailybudgettracker.Activity.SplashLockActivity;
import com.credistudio.dailybudgettracker.Activity.UserDetailActivity;
import com.credistudio.dailybudgettracker.Activity.pieChartIncomeActivity;
import com.credistudio.dailybudgettracker.Adapter.BankRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.CreditCardRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.DebitCardRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.EwalletRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.ExpenseCategoryRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.ExpensesRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.IncomeCategoryRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.IncomeRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.NotificationRVAdapter;
import com.credistudio.dailybudgettracker.Adapter.TransferRVAdapter;
import com.credistudio.dailybudgettracker.Fragment.FragmentHomePager;
import com.credistudio.dailybudgettracker.Fragment.FragmentReportFilterPager;
import com.credistudio.dailybudgettracker.ServiceUtility.NotificationService;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, DBModule.class})
public interface DaggerComponent {
    Context getContext();

    void inject(AccountDetailsActivity accountDetailsActivity);

    void inject(CategoryManagementActivity categoryManagementActivity);

    void inject(HomeActivity homeActivity);

    void inject(NotificationActivity notificationActivity);

    void inject(PieChartActivity pieChartActivity);
    void inject(pieChartIncomeActivity pieChartIncomeActivity);

    void inject(ReportFilterActivity reportFilterActivity);

    void inject(SettingsActivity settingsActivity);

    void inject(SplashLockActivity splashLockActivity);

    void inject(UserDetailActivity userDetailActivity);

    void inject(BankRVAdapter bankRVAdapter);

    void inject(CreditCardRVAdapter creditCardRVAdapter);

    void inject(DebitCardRVAdapter debitCardRVAdapter);

    void inject(EwalletRVAdapter ewalletRVAdapter);

    void inject(ExpenseCategoryRVAdapter expenseCategoryRVAdapter);

    void inject(ExpensesRVAdapter expensesRVAdapter);

    void inject(IncomeCategoryRVAdapter incomeCategoryRVAdapter);

    void inject(IncomeRVAdapter incomeRVAdapter);

    void inject(NotificationRVAdapter notificationRVAdapter);

    void inject(TransferRVAdapter transferRVAdapter);

    void inject(DaggerApplication daggerApplication);

    void inject(FragmentHomePager fragmentHomePager);

    void inject(FragmentReportFilterPager fragmentReportFilterPager);

    void inject(NotificationService notificationService);
}
