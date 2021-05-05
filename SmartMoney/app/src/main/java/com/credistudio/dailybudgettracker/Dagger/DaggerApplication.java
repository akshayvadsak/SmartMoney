package com.credistudio.dailybudgettracker.Dagger;

import android.app.Application;
import android.content.Context;

import android.util.Log;

import androidx.multidex.MultiDex;

import com.credistudio.dailybudgettracker.Database.DBHelper;


public class DaggerApplication extends Application {
    DaggerComponent component;

    public static DaggerApplication get(Context context) {
        return (DaggerApplication) context.getApplicationContext();
    }

    public void onCreate() {
        super.onCreate();
        this.component = DaggerDaggerComponent.builder().appModule(new AppModule(this)).dBModule(new DBModule(DBHelper.getInstance(getApplicationContext()), DBHelper.getInstanceDB())).build();
        Log.e("DraggerApplication.....", "onCreate");
        this.component.inject(this);
    }

    public DaggerComponent getComponent() {
        return this.component;
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
