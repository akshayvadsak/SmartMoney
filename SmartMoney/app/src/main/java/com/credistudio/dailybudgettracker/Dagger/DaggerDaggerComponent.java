//package com.credistudio.dailybudgettracker.Dagger;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import com.credistudio.dailybudgettracker.Activity.AccountDetailsActivity;
//import com.credistudio.dailybudgettracker.Activity.AccountDetailsActivity_MembersInjector;
//import com.credistudio.dailybudgettracker.Activity.CategoryManagementActivity;
//import com.credistudio.dailybudgettracker.Activity.CategoryManagementActivity_MembersInjector;
//import com.credistudio.dailybudgettracker.Activity.HomeActivity;
//import com.credistudio.dailybudgettracker.Activity.HomeActivity_MembersInjector;
//import com.credistudio.dailybudgettracker.Activity.NotificationActivity;
//import com.credistudio.dailybudgettracker.Activity.NotificationActivity_MembersInjector;
//import com.credistudio.dailybudgettracker.Activity.PieChartActivity;
//import com.credistudio.dailybudgettracker.Activity.PieChartActivity_MembersInjector;
//import com.credistudio.dailybudgettracker.Activity.ReportFilterActivity;
//import com.credistudio.dailybudgettracker.Activity.ReportFilterActivity_MembersInjector;
//import com.credistudio.dailybudgettracker.Activity.SettingsActivity;
//import com.credistudio.dailybudgettracker.Activity.SettingsActivity_MembersInjector;
//import com.credistudio.dailybudgettracker.Activity.SplashLockActivity;
//import com.credistudio.dailybudgettracker.Activity.SplashLockActivity_MembersInjector;
//import com.credistudio.dailybudgettracker.Activity.UserDetailActivity;
//import com.credistudio.dailybudgettracker.Activity.UserDetailActivity_MembersInjector;
//import com.credistudio.dailybudgettracker.Adapter.BankRVAdapter;
//import com.credistudio.dailybudgettracker.Adapter.BankRVAdapter_MembersInjector;
//import com.credistudio.dailybudgettracker.Adapter.CreditCardRVAdapter;
//import com.credistudio.dailybudgettracker.Adapter.CreditCardRVAdapter_MembersInjector;
//import com.credistudio.dailybudgettracker.Adapter.DebitCardRVAdapter;
//import com.credistudio.dailybudgettracker.Adapter.DebitCardRVAdapter_MembersInjector;
//import com.credistudio.dailybudgettracker.Adapter.EwalletRVAdapter;
//import com.credistudio.dailybudgettracker.Adapter.EwalletRVAdapter_MembersInjector;
//import com.credistudio.dailybudgettracker.Adapter.ExpenseCategoryRVAdapter;
//import com.credistudio.dailybudgettracker.Adapter.ExpenseCategoryRVAdapter_MembersInjector;
//import com.credistudio.dailybudgettracker.Adapter.ExpensesRVAdapter;
//import com.credistudio.dailybudgettracker.Adapter.ExpensesRVAdapter_MembersInjector;
//import com.credistudio.dailybudgettracker.Adapter.IncomeCategoryRVAdapter;
//import com.credistudio.dailybudgettracker.Adapter.IncomeCategoryRVAdapter_MembersInjector;
//import com.credistudio.dailybudgettracker.Adapter.IncomeRVAdapter;
//import com.credistudio.dailybudgettracker.Adapter.IncomeRVAdapter_MembersInjector;
//import com.credistudio.dailybudgettracker.Adapter.NotificationRVAdapter;
//import com.credistudio.dailybudgettracker.Adapter.NotificationRVAdapter_MembersInjector;
//import com.credistudio.dailybudgettracker.Adapter.TransferRVAdapter;
//import com.credistudio.dailybudgettracker.Adapter.TransferRVAdapter_MembersInjector;
//import com.credistudio.dailybudgettracker.Database.DBHelper;
//import com.credistudio.dailybudgettracker.Fragment.FragmentHomePager;
//import com.credistudio.dailybudgettracker.Fragment.FragmentHomePager_MembersInjector;
//import com.credistudio.dailybudgettracker.Fragment.FragmentReportFilterPager;
//import com.credistudio.dailybudgettracker.Fragment.FragmentReportFilterPager_MembersInjector;
//import com.credistudio.dailybudgettracker.ServiceUtility.NotificationService;
//import com.credistudio.dailybudgettracker.ServiceUtility.NotificationService_MembersInjector;
//import dagger.MembersInjector;
//import dagger.internal.DoubleCheck;
//import dagger.internal.MembersInjectors;
//import dagger.internal.Preconditions;
//import javax.inject.Provider;
//
//public final class DaggerDaggerComponent implements DaggerComponent {
//    static final /* synthetic */ boolean $assertionsDisabled = false;
//    private MembersInjector<AccountDetailsActivity> accountDetailsActivityMembersInjector;
//    private MembersInjector<BankRVAdapter> bankRVAdapterMembersInjector;
//    private MembersInjector<CategoryManagementActivity> categoryManagementActivityMembersInjector;
//    private MembersInjector<CreditCardRVAdapter> creditCardRVAdapterMembersInjector;
//    private Provider<DataManager> dataManagerProvider;
//    private MembersInjector<DebitCardRVAdapter> debitCardRVAdapterMembersInjector;
//    private MembersInjector<EwalletRVAdapter> ewalletRVAdapterMembersInjector;
//    private MembersInjector<ExpenseCategoryRVAdapter> expenseCategoryRVAdapterMembersInjector;
//    private MembersInjector<ExpensesRVAdapter> expensesRVAdapterMembersInjector;
//    private MembersInjector<FragmentHomePager> fragmentHomePagerMembersInjector;
//    private MembersInjector<FragmentReportFilterPager> fragmentReportFilterPagerMembersInjector;
//    private MembersInjector<HomeActivity> homeActivityMembersInjector;
//    private MembersInjector<IncomeCategoryRVAdapter> incomeCategoryRVAdapterMembersInjector;
//    private MembersInjector<IncomeRVAdapter> incomeRVAdapterMembersInjector;
//    private MembersInjector<NotificationActivity> notificationActivityMembersInjector;
//    private MembersInjector<NotificationRVAdapter> notificationRVAdapterMembersInjector;
//    private MembersInjector<NotificationService> notificationServiceMembersInjector;
//    private MembersInjector<PieChartActivity> pieChartActivityMembersInjector;
//    private Provider<DBHelper> provideDBHelperProvider;
//    private Provider<Context> providesApplicationContextProvider;
//    private Provider<SharedPreferences> providesSharedPrefrencesProvider;
//    private MembersInjector<ReportFilterActivity> reportFilterActivityMembersInjector;
//    private MembersInjector<SettingsActivity> settingsActivityMembersInjector;
//    private MembersInjector<SplashLockActivity> splashLockActivityMembersInjector;
//    private MembersInjector<TransferRVAdapter> transferRVAdapterMembersInjector;
//    private MembersInjector<UserDetailActivity> userDetailActivityMembersInjector;
//
//    public static final class Builder {
//        /* access modifiers changed from: private */
//        public AppModule appModule;
//        /* access modifiers changed from: private */
//        public DBModule dBModule;
//
//        private Builder() {
//        }
//
//        public DaggerComponent build() {
//            if (this.appModule == null) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(AppModule.class.getCanonicalName());
//                sb.append(" must be set");
//                throw new IllegalStateException(sb.toString());
//            } else if (this.dBModule != null) {
//                return new DaggerDaggerComponent(this);
//            } else {
//                StringBuilder sb2 = new StringBuilder();
//                sb2.append(DBModule.class.getCanonicalName());
//                sb2.append(" must be set");
//                throw new IllegalStateException(sb2.toString());
//            }
//        }
//
//        public Builder appModule(AppModule appModule2) {
//            this.appModule = (AppModule) Preconditions.checkNotNull(appModule2);
//            return this;
//        }
//
//        public Builder dBModule(DBModule dBModule2) {
//            this.dBModule = (DBModule) Preconditions.checkNotNull(dBModule2);
//            return this;
//        }
//    }
//
//    private DaggerDaggerComponent(Builder builder) {
//        initialize(builder);
//    }
//
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    private void initialize(Builder builder) {
//        this.providesApplicationContextProvider = DoubleCheck.provider(AppModule_ProvidesApplicationContextFactory.create(builder.appModule));
//        this.provideDBHelperProvider = DoubleCheck.provider(DBModule_ProvideDBHelperFactory.create(builder.dBModule));
//        this.dataManagerProvider = DataManager_Factory.create(this.providesApplicationContextProvider, this.provideDBHelperProvider);
//        this.providesSharedPrefrencesProvider = DoubleCheck.provider(AppModule_ProvidesSharedPrefrencesFactory.create(builder.appModule, this.providesApplicationContextProvider));
//        this.accountDetailsActivityMembersInjector = AccountDetailsActivity_MembersInjector.create(this.dataManagerProvider, this.providesSharedPrefrencesProvider);
//        this.userDetailActivityMembersInjector = UserDetailActivity_MembersInjector.create(this.providesSharedPrefrencesProvider, this.dataManagerProvider);
//        this.fragmentHomePagerMembersInjector = FragmentHomePager_MembersInjector.create(this.dataManagerProvider);
//        this.reportFilterActivityMembersInjector = ReportFilterActivity_MembersInjector.create(this.dataManagerProvider);
//        this.settingsActivityMembersInjector = SettingsActivity_MembersInjector.create(this.dataManagerProvider);
//        this.homeActivityMembersInjector = HomeActivity_MembersInjector.create(this.dataManagerProvider);
//        this.fragmentReportFilterPagerMembersInjector = FragmentReportFilterPager_MembersInjector.create(this.dataManagerProvider);
//        this.bankRVAdapterMembersInjector = BankRVAdapter_MembersInjector.create(this.dataManagerProvider);
//        this.creditCardRVAdapterMembersInjector = CreditCardRVAdapter_MembersInjector.create(this.dataManagerProvider);
//        this.ewalletRVAdapterMembersInjector = EwalletRVAdapter_MembersInjector.create(this.dataManagerProvider);
//        this.debitCardRVAdapterMembersInjector = DebitCardRVAdapter_MembersInjector.create(this.dataManagerProvider);
//        this.notificationServiceMembersInjector = NotificationService_MembersInjector.create(this.dataManagerProvider);
//        this.notificationActivityMembersInjector = NotificationActivity_MembersInjector.create(this.dataManagerProvider);
//        this.notificationRVAdapterMembersInjector = NotificationRVAdapter_MembersInjector.create(this.dataManagerProvider);
//        this.splashLockActivityMembersInjector = SplashLockActivity_MembersInjector.create(this.providesSharedPrefrencesProvider);
//        this.pieChartActivityMembersInjector = PieChartActivity_MembersInjector.create(this.dataManagerProvider);
//        this.expenseCategoryRVAdapterMembersInjector = ExpenseCategoryRVAdapter_MembersInjector.create(this.dataManagerProvider);
//        this.incomeCategoryRVAdapterMembersInjector = IncomeCategoryRVAdapter_MembersInjector.create(this.dataManagerProvider);
//        this.categoryManagementActivityMembersInjector = CategoryManagementActivity_MembersInjector.create(this.dataManagerProvider);
//        this.expensesRVAdapterMembersInjector = ExpensesRVAdapter_MembersInjector.create(this.dataManagerProvider);
//        this.incomeRVAdapterMembersInjector = IncomeRVAdapter_MembersInjector.create(this.dataManagerProvider);
//        this.transferRVAdapterMembersInjector = TransferRVAdapter_MembersInjector.create(this.dataManagerProvider);
//    }
//
//    public void inject(DaggerApplication daggerApplication) {
//        MembersInjectors.noOp().injectMembers(daggerApplication);
//    }
//
//    public void inject(AccountDetailsActivity accountDetailsActivity) {
//        this.accountDetailsActivityMembersInjector.injectMembers(accountDetailsActivity);
//    }
//
//    public void inject(UserDetailActivity userDetailActivity) {
//        this.userDetailActivityMembersInjector.injectMembers(userDetailActivity);
//    }
//
//    public void inject(FragmentHomePager fragmentHomePager) {
//        this.fragmentHomePagerMembersInjector.injectMembers(fragmentHomePager);
//    }
//
//    public void inject(ReportFilterActivity reportFilterActivity) {
//        this.reportFilterActivityMembersInjector.injectMembers(reportFilterActivity);
//    }
//
//    public void inject(SettingsActivity settingsActivity) {
//        this.settingsActivityMembersInjector.injectMembers(settingsActivity);
//    }
//
//    public void inject(HomeActivity homeActivity) {
//        this.homeActivityMembersInjector.injectMembers(homeActivity);
//    }
//
//    public void inject(FragmentReportFilterPager fragmentReportFilterPager) {
//        this.fragmentReportFilterPagerMembersInjector.injectMembers(fragmentReportFilterPager);
//    }
//
//    public void inject(BankRVAdapter bankRVAdapter) {
//        this.bankRVAdapterMembersInjector.injectMembers(bankRVAdapter);
//    }
//
//    public void inject(CreditCardRVAdapter creditCardRVAdapter) {
//        this.creditCardRVAdapterMembersInjector.injectMembers(creditCardRVAdapter);
//    }
//
//    public void inject(EwalletRVAdapter ewalletRVAdapter) {
//        this.ewalletRVAdapterMembersInjector.injectMembers(ewalletRVAdapter);
//    }
//
//    public void inject(DebitCardRVAdapter debitCardRVAdapter) {
//        this.debitCardRVAdapterMembersInjector.injectMembers(debitCardRVAdapter);
//    }
//
//    public void inject(NotificationService notificationService) {
//        this.notificationServiceMembersInjector.injectMembers(notificationService);
//    }
//
//    public void inject(NotificationActivity notificationActivity) {
//        this.notificationActivityMembersInjector.injectMembers(notificationActivity);
//    }
//
//    public void inject(NotificationRVAdapter notificationRVAdapter) {
//        this.notificationRVAdapterMembersInjector.injectMembers(notificationRVAdapter);
//    }
//
//    public void inject(SplashLockActivity splashLockActivity) {
//        this.splashLockActivityMembersInjector.injectMembers(splashLockActivity);
//    }
//
//    public void inject(PieChartActivity pieChartActivity) {
//        this.pieChartActivityMembersInjector.injectMembers(pieChartActivity);
//    }
//
//    public void inject(ExpenseCategoryRVAdapter expenseCategoryRVAdapter) {
//        this.expenseCategoryRVAdapterMembersInjector.injectMembers(expenseCategoryRVAdapter);
//    }
//
//    public void inject(IncomeCategoryRVAdapter incomeCategoryRVAdapter) {
//        this.incomeCategoryRVAdapterMembersInjector.injectMembers(incomeCategoryRVAdapter);
//    }
//
//    public void inject(CategoryManagementActivity categoryManagementActivity) {
//        this.categoryManagementActivityMembersInjector.injectMembers(categoryManagementActivity);
//    }
//
//    public void inject(ExpensesRVAdapter expensesRVAdapter) {
//        this.expensesRVAdapterMembersInjector.injectMembers(expensesRVAdapter);
//    }
//
//    public void inject(IncomeRVAdapter incomeRVAdapter) {
//        this.incomeRVAdapterMembersInjector.injectMembers(incomeRVAdapter);
//    }
//
//    public void inject(TransferRVAdapter transferRVAdapter) {
//        this.transferRVAdapterMembersInjector.injectMembers(transferRVAdapter);
//    }
//
//    public Context getContext() {
//        return (Context) this.providesApplicationContextProvider.get();
//    }
//}
