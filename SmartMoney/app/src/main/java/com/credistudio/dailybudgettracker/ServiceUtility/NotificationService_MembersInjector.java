package com.credistudio.dailybudgettracker.ServiceUtility;


import com.credistudio.dailybudgettracker.Dagger.DataManager;

import dagger.MembersInjector;
import javax.inject.Provider;

public final class NotificationService_MembersInjector implements MembersInjector<NotificationService> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Provider<DataManager> dataManagerProvider;

    public NotificationService_MembersInjector(Provider<DataManager> provider) {
        this.dataManagerProvider = provider;
    }

    public static MembersInjector<NotificationService> create(Provider<DataManager> provider) {
        return new NotificationService_MembersInjector(provider);
    }

    public void injectMembers(NotificationService notificationService) {
        if (notificationService == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        notificationService.dataManager = (DataManager) this.dataManagerProvider.get();
    }

    public static void injectDataManager(NotificationService notificationService, Provider<DataManager> provider) {
        notificationService.dataManager = (DataManager) provider.get();
    }
}
