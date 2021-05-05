package com.credistudio.dailybudgettracker.Dagger;

import android.database.sqlite.SQLiteDatabase;

import com.credistudio.dailybudgettracker.Database.DBHelper;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class DBModule {
    SQLiteDatabase db;
    DBHelper helper;

    public DBModule(DBHelper dBHelper, SQLiteDatabase sQLiteDatabase) {
        this.helper = dBHelper;
        this.db = sQLiteDatabase;
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public DBHelper provideDBHelper() {
        return this.helper;
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public SQLiteDatabase provideSqliteDatabase() {
        return this.db;
    }
}
