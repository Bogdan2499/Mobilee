

package com.adkdevelopment.rssreader.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.adkdevelopment.rssreader.R;
import com.adkdevelopment.rssreader.data.local.NewsObject;
import com.adkdevelopment.rssreader.data.local.NewsRealm;
import com.adkdevelopment.rssreader.ui.adapters.PagerAdapter;
import com.adkdevelopment.rssreader.ui.base.BaseActivity;
import com.adkdevelopment.rssreader.ui.behavior.ZoomOutPageTransformer;
import com.adkdevelopment.rssreader.ui.contracts.DetailActivityContract;
import com.adkdevelopment.rssreader.ui.presenters.DetailActivityPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends BaseActivity implements DetailActivityContract.View {

    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private DetailActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        mPresenter = new DetailActivityPresenter();
        mPresenter.attachView(this);

        mPresenter.loadData(getIntent());

        initActionBar();

    }


    private void initActionBar() {

        // Initialize a custom Toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        // Add back button to the actionbar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setLogo(R.drawable.logo_title);
            actionBar.setTitle(R.string.title_main);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // copy the behavior of the hardware back button
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showData(List<NewsObject> itemList) {
        PagerAdapter mAdapter = new PagerAdapter(getSupportFragmentManager());
        mAdapter.setNewsItems(itemList);
        mPager.setAdapter(mAdapter);
        int position = getIntent().getIntExtra(NewsRealm.NEWS_POSITION, 0);

        // zoom effect on swipe
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setCurrentItem(position);
    }

    @Override
    public void showError(Throwable e) {
        Log.e(TAG, "e:" + e);
    }
}
