

package com.adkdevelopment.rssreader.ui.contracts;

import com.adkdevelopment.rssreader.ui.base.MvpPresenter;
import com.adkdevelopment.rssreader.ui.base.MvpView;


public class MainContract {

    public interface Presenter extends MvpPresenter<View> {
        void scheduleJob();
    }

    public interface View extends MvpView {

    }
}
