package com.credistudio.dailybudgettracker.Fragment;


import com.credistudio.dailybudgettracker.Dagger.DataManager;

import dagger.MembersInjector;
import javax.inject.Provider;

public final class FragmentHomePager_MembersInjector implements MembersInjector<FragmentHomePager> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Provider<DataManager> dataManagerProvider;

    public FragmentHomePager_MembersInjector(Provider<DataManager> provider) {
        this.dataManagerProvider = provider;
    }

    public static MembersInjector<FragmentHomePager> create(Provider<DataManager> provider) {
        return new FragmentHomePager_MembersInjector(provider);
    }

    public void injectMembers(FragmentHomePager fragmentHomePager) {
        if (fragmentHomePager == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        fragmentHomePager.dataManager = (DataManager) this.dataManagerProvider.get();
    }

    public static void injectDataManager(FragmentHomePager fragmentHomePager, Provider<DataManager> provider) {
        fragmentHomePager.dataManager = (DataManager) provider.get();
    }
}
