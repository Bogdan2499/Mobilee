
package com.adkdevelopment.rssreader.ui.contracts;

import android.content.Intent;
import android.os.Bundle;

import com.adkdevelopment.rssreader.data.local.NewsObject;
import com.adkdevelopment.rssreader.ui.base.MvpPresenter;
import com.adkdevelopment.rssreader.ui.base.MvpView;


public class DetailFragmentContract {

    public interface Presenter extends MvpPresenter<View> {
        void loadData(Intent intent);

        void loadData(Bundle bundle);

        Intent getShareIntent();
    }

    public interface View extends MvpView {
        void showData(NewsObject newsItem);

        void showError();
    }

}
