package p8project.sw801.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.AppDataManager;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.local.db.AppDbHelper;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.local.prefs.AppPreferencesHelper;
import p8project.sw801.data.local.prefs.PreferencesHelper;
import p8project.sw801.di.DatabaseInfo;
import p8project.sw801.di.PreferenceInfo;
import p8project.sw801.utils.AppConstants;
import p8project.sw801.utils.rx.AppSchedulerProvider;
import p8project.sw801.utils.rx.SchedulerProvider;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
@Module
public class AppModule {
    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                dbName
        ).fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
