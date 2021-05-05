package com.credistudio.dailybudgettracker.Dagger;

import android.content.Context;

import com.credistudio.dailybudgettracker.Database.DBHelper;

import dagger.internal.Factory;
import javax.inject.Provider;

public final class DataManager_Factory implements Factory<DataManager> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Provider<Context> contextProvider;
    private final Provider<DBHelper> dbHelperProvider;

    public DataManager_Factory(Provider<Context> provider, Provider<DBHelper> provider2) {
        this.contextProvider = provider;
        this.dbHelperProvider = provider2;
    }

    public DataManager get() {
        return new DataManager((Context) this.contextProvider.get(), (DBHelper) this.dbHelperProvider.get());
    }

    public static Factory<DataManager> create(Provider<Context> provider, Provider<DBHelper> provider2) {
        return new DataManager_Factory(provider, provider2);
    }
}
