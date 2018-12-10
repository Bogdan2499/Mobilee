

package com.adkdevelopment.rssreader.ui.contracts;

import com.adkdevelopment.rssreader.data.local.NewsObject;
import com.adkdevelopment.rssreader.ui.base.MvpPresenter;
import com.adkdevelopment.rssreader.ui.base.MvpView;

import java.util.List;


public class ListContract {

    public interface Presenter extends MvpPresenter<View> {
        void requestData();

        void requestData(String query);

        void fetchData();
    }

    public interface View extends MvpView {
        void showData(List<NewsObject> itemList);

        void showEmpty();

        void showError();

        void showProgress(boolean isInProgress);
    }
}
