

package com.adkdevelopment.rssreader.ui.presenters;

import android.content.Intent;

import com.adkdevelopment.rssreader.App;
import com.adkdevelopment.rssreader.data.local.NewsObject;
import com.adkdevelopment.rssreader.data.local.NewsRealm;
import com.adkdevelopment.rssreader.ui.base.BaseMvpPresenter;
import com.adkdevelopment.rssreader.ui.contracts.DetailActivityContract;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DetailActivityPresenter
        extends BaseMvpPresenter<DetailActivityContract.View>
        implements DetailActivityContract.Presenter {

    private Subscription mSubscription;

    @Override
    public void loadData(Intent intent) {
        checkViewAttached();

        if (intent != null) {
            if (intent.hasExtra(NewsRealm.NEWS_EXTRA)
                    && intent.hasExtra(NewsRealm.NEWS_POSITION)) {
                getMvpView().showData(intent.getParcelableArrayListExtra(NewsRealm.NEWS_EXTRA));
            }
        } else {
            mSubscription = App.getDataManager().findAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<List<NewsObject>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().showError(e);
                        }

                        @Override
                        public void onNext(List<NewsObject> itemList) {
                            getMvpView().showData(itemList);
                        }
                    });
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
