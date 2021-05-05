//package com.credistudio.dailybudgettracker.Dagger;
//
//import android.content.Context;
//import dagger.internal.Factory;
//import dagger.internal.Preconditions;
//
//public final class AppModule_ProvidesApplicationContextFactory implements Factory<Context> {
//    static final /* synthetic */ boolean $assertionsDisabled = false;
//    private final AppModule module;
//
//    public AppModule_ProvidesApplicationContextFactory(AppModule appModule) {
//        this.module = appModule;
//    }
//
//    public Context get() {
//        return (Context) Preconditions.checkNotNull(this.module.providesApplicationContext(), "Cannot return null from a non-@Nullable @Provides method");
//    }
//
//    public static Factory<Context> create(AppModule appModule) {
//        return new AppModule_ProvidesApplicationContextFactory(appModule);
//    }
//
//    public static Context proxyProvidesApplicationContext(AppModule appModule) {
//        return appModule.providesApplicationContext();
//    }
//}
