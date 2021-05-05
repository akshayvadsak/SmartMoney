//package com.credistudio.dailybudgettracker.Dagger;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import dagger.internal.Factory;
//import dagger.internal.Preconditions;
//import javax.inject.Provider;
//
//public final class AppModule_ProvidesSharedPrefrencesFactory implements Factory<SharedPreferences> {
//    static final /* synthetic */ boolean $assertionsDisabled = false;
//    private final Provider<Context> appProvider;
//    private final AppModule module;
//
//    public AppModule_ProvidesSharedPrefrencesFactory(AppModule appModule, Provider<Context> provider) {
//        this.module = appModule;
//        this.appProvider = provider;
//    }
//
//    public SharedPreferences get() {
//        return (SharedPreferences) Preconditions.checkNotNull(this.module.providesSharedPrefrences((Context) this.appProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
//    }
//
//    public static Factory<SharedPreferences> create(AppModule appModule, Provider<Context> provider) {
//        return new AppModule_ProvidesSharedPrefrencesFactory(appModule, provider);
//    }
//
//    public static SharedPreferences proxyProvidesSharedPrefrences(AppModule appModule, Context context) {
//        return appModule.providesSharedPrefrences(context);
//    }
//}
