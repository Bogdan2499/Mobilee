

package com.adkdevelopment.rssreader.ui.presenters;

import android.content.Intent;
import android.os.Bundle;

import com.adkdevelopment.rssreader.data.local.NewsObject;
import com.adkdevelopment.rssreader.data.local.NewsRealm;
import com.adkdevelopment.rssreader.ui.base.BaseMvpPresenter;
import com.adkdevelopment.rssreader.ui.contracts.DetailFragmentContract;


public class DetailFragmentPresenter
        extends BaseMvpPresenter<DetailFragmentContract.View>
        implements DetailFragmentContract.Presenter {

    private NewsObject mNewsItem;

    @Override
    public void loadData(Intent intent) {
        if (intent == null) {
            getMvpView().showError();
        } else {
            mNewsItem = intent.getParcelableExtra(NewsRealm.NEWS_EXTRA);
            getMvpView().showData(mNewsItem);
        }
    }

    @Override
    public void loadData(Bundle bundle) {
        if (bundle.getParcelable(NewsRealm.NEWS_EXTRA) == null) {
            getMvpView().showError();
        } else {
            mNewsItem = bundle.getParcelable(NewsRealm.NEWS_EXTRA);
            getMvpView().showData(mNewsItem);
        }
    }


    @Override
    public Intent getShareIntent() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, mNewsItem.getTitle() + "\n"
                + mNewsItem.getDescription() + "\n"
                + mNewsItem.getLink());
        return sendIntent;
    }
}
