//package com.credistudio.dailybudgettracker.Dagger;
//
//
//import com.credistudio.dailybudgettracker.Database.DBHelper;
//
//import dagger.internal.Factory;
//import dagger.internal.Preconditions;
//
//public final class DBModule_ProvideDBHelperFactory implements Factory<DBHelper> {
//    static final /* synthetic */ boolean $assertionsDisabled = false;
//    private final DBModule module;
//
//    public DBModule_ProvideDBHelperFactory(DBModule dBModule) {
//        this.module = dBModule;
//    }
//
//    public DBHelper get() {
//        return (DBHelper) Preconditions.checkNotNull(this.module.provideDBHelper(), "Cannot return null from a non-@Nullable @Provides method");
//    }
//
//    public static Factory<DBHelper> create(DBModule dBModule) {
//        return new DBModule_ProvideDBHelperFactory(dBModule);
//    }
//
//    public static DBHelper proxyProvideDBHelper(DBModule dBModule) {
//        return dBModule.provideDBHelper();
//    }
//}
