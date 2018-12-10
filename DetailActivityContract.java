

package com.adkdevelopment.rssreader.ui.contracts;

import android.content.Intent;

import com.adkdevelopment.rssreader.data.local.NewsObject;
import com.adkdevelopment.rssreader.ui.base.MvpPresenter;
import com.adkdevelopment.rssreader.ui.base.MvpView;

import java.util.List;


public class DetailActivityContract {

    public interface Presenter extends MvpPresenter<View> {
        void loadData(Intent intent);
    }

    public interface View extends MvpView {
        void showData(List<NewsObject> itemList);

        void showError(Throwable e);
    }

}
