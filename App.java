

package com.adkdevelopment.rssreader;

import android.app.Application;

import com.adkdevelopment.rssreader.data.managers.ApiManager;
import com.adkdevelopment.rssreader.data.managers.DataManager;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends Application {

    private static DataManager sDataManager;
    private static ApiManager sApiManager;

    @Override
    public void onCreate() {
        super.onCreate();

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }


        setupRealmDefaultInstance();
    }


    public static ApiManager getApiManager() {
        if (sApiManager == null) {
            sApiManager = new ApiManager();
            sApiManager.init();
        }
        return sApiManager;
    }


    public static DataManager getDataManager() {
        if (sDataManager == null) {
            sDataManager = new DataManager();
        }
        return sDataManager;
    }


    private void setupRealmDefaultInstance() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }


    public void clear() {
        sApiManager.clear();
        sDataManager.clear();
    }
}
