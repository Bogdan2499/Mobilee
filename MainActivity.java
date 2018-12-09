
package com.adkdevelopment.rssreader.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.adkdevelopment.license.ui.LicenseActivity;
import com.adkdevelopment.rssreader.R;
import com.adkdevelopment.rssreader.data.local.NewsObject;
import com.adkdevelopment.rssreader.data.local.NewsRealm;
import com.adkdevelopment.rssreader.ui.base.BaseActivity;
import com.adkdevelopment.rssreader.ui.contracts.MainContract;
import com.adkdevelopment.rssreader.ui.interfaces.OnFragmentListener;
import com.adkdevelopment.rssreader.ui.presenters.MainPresenter;
import com.adkdevelopment.rssreader.ui.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity
        implements MainContract.View, OnFragmentListener {

    private MainPresenter mPresenter;

    // Whether or not the activity is in two-pane mode, i.e. running on a tablet device
    private boolean mTwoPane;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new MainPresenter(this);
        mPresenter.attachView(this);
        mPresenter.scheduleJob();

        initActionBar();

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            mTwoPane = true;
        }
    }

    private boolean findViewById(int item_detail_container) {
    }


    private void initActionBar() {

        // Set up ActionBar and corresponding icons
        mToolbar.setOnClickListener(view -> {
            Fragment fragment = getSupportFragmentManager()
                    .findFragmentById(R.id.fragment);
            if (fragment != null && fragment instanceof ListFragment)
                ((ListFragment) fragment).scrollToTop();
        });
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.logo_title);
            actionBar.setTitle(R.string.title_main);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onFragmentInteraction(Integer position, View view, List<NewsObject> item) {
        if (!mTwoPane) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(NewsRealm.NEWS_POSITION, position);
            intent.putExtra(NewsRealm.NEWS_EXTRA, (ArrayList) item);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                @SuppressWarnings("unchecked")
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
                        .toBundle();
                startActivity(intent, bundle);
            } else {
                startActivity(intent);
            }
        } else {
            Bundle args = new Bundle();
            args.putParcelable(NewsRealm.NEWS_EXTRA, item.get(position));
            DetailFragment fragment = DetailFragment.newInstance(item.get(position));
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_license:
                startActivity(new Intent(this, LicenseActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
