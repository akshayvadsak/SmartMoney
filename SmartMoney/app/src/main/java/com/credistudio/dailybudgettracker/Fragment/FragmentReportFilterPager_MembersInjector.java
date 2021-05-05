package com.credistudio.dailybudgettracker.Fragment;

import com.credistudio.dailybudgettracker.Dagger.DataManager;

import dagger.MembersInjector;
import javax.inject.Provider;

public final class FragmentReportFilterPager_MembersInjector implements MembersInjector<FragmentReportFilterPager> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Provider<DataManager> dataManagerProvider;

    public FragmentReportFilterPager_MembersInjector(Provider<DataManager> provider) {
        this.dataManagerProvider = provider;
    }

    public static MembersInjector<FragmentReportFilterPager> create(Provider<DataManager> provider) {
        return new FragmentReportFilterPager_MembersInjector(provider);
    }

    public void injectMembers(FragmentReportFilterPager fragmentReportFilterPager) {
        if (fragmentReportFilterPager == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        fragmentReportFilterPager.dataManager = (DataManager) this.dataManagerProvider.get();
    }

    public static void injectDataManager(FragmentReportFilterPager fragmentReportFilterPager, Provider<DataManager> provider) {
        fragmentReportFilterPager.dataManager = (DataManager) provider.get();
    }
}
