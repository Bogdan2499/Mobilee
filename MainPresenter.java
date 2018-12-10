

package com.adkdevelopment.rssreader.ui.presenters;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.util.Log;

import com.adkdevelopment.rssreader.R;
import com.adkdevelopment.rssreader.data.managers.PrefsManager;
import com.adkdevelopment.rssreader.data.services.FetchJobService;
import com.adkdevelopment.rssreader.ui.base.BaseMvpPresenter;
import com.adkdevelopment.rssreader.ui.contracts.MainContract;


public class MainPresenter extends BaseMvpPresenter<MainContract.View>
        implements MainContract.Presenter, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private final Context mContext;

    public MainPresenter(Context context) {
        mContext = context;
    }


    @Override
    public void scheduleJob() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int JOB_ID = 123;
            ComponentName serviceName = new ComponentName(mContext, FetchJobService.class);
            JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceName)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPeriodic(new PrefsManager(mContext).getSyncInterval() * DateUtils.SECOND_IN_MILLIS)
                    .setPersisted(true)
                    .build();
            JobScheduler scheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            int result = scheduler.schedule(jobInfo);
            if (result == JobScheduler.RESULT_SUCCESS) {
                Log.v(TAG, "Job scheduled successfully!");
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(mContext.getString(R.string.sharedprefs_key_syncfrequency))) {
            scheduleJob();
        }
    }

    @Override
    public void attachView(MainContract.View mvpView) {
        super.attachView(mvpView);
        PreferenceManager.getDefaultSharedPreferences(mContext)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void detachView() {
        super.detachView();
        PreferenceManager.getDefaultSharedPreferences(mContext)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
