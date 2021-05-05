package com.credistudio.dailybudgettracker.Dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppModule {
    private final DaggerApplication dagApplication;

    /* access modifiers changed from: 0000 */
    @Provides
    public String provideDatabaseName() {
        return "MyExpenseDB.db";
    }

    public AppModule(DaggerApplication daggerApplication) {
        this.dagApplication = daggerApplication;
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public Context providesApplicationContext() {
        return this.dagApplication;
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public SharedPreferences providesSharedPrefrences(Context context) {
        Log.e("AppModule", "SharedPreferences");
        return context.getSharedPreferences("MyExpense", 0);
    }

    /* access modifiers changed from: 0000 */
    @Provides
    public Resources providesResources() {
        Log.e("AppModule", "Resources");
        return this.dagApplication.getResources();
    }

    /* access modifiers changed from: 0000 */
    @Provides
    public Application provideApplication() {
        return this.dagApplication;
    }

    /* access modifiers changed from: 0000 */
    @Provides
    public Integer provideDatabaseVersion() {
        return Integer.valueOf(1);
    }
}
