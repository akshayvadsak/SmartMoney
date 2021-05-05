//package com.credistudio.dailybudgettracker.Dagger;
//
//import android.database.sqlite.SQLiteDatabase;
//import dagger.internal.Factory;
//import dagger.internal.Preconditions;
//
//public final class DBModule_ProvideSqliteDatabaseFactory implements Factory<SQLiteDatabase> {
//    static final /* synthetic */ boolean $assertionsDisabled = false;
//    private final DBModule module;
//
//    public DBModule_ProvideSqliteDatabaseFactory(DBModule dBModule) {
//        this.module = dBModule;
//    }
//
//    public SQLiteDatabase get() {
//        return (SQLiteDatabase) Preconditions.checkNotNull(this.module.provideSqliteDatabase(), "Cannot return null from a non-@Nullable @Provides method");
//    }
//
//    public static Factory<SQLiteDatabase> create(DBModule dBModule) {
//        return new DBModule_ProvideSqliteDatabaseFactory(dBModule);
//    }
//
//    public static SQLiteDatabase proxyProvideSqliteDatabase(DBModule dBModule) {
//        return dBModule.provideSqliteDatabase();
//    }
//}
