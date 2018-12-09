

package com.adkdevelopment.rssreader.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.adkdevelopment.rssreader.R;
import com.adkdevelopment.rssreader.data.local.NewsObject;
import com.adkdevelopment.rssreader.ui.base.BaseFragment;
import com.adkdevelopment.rssreader.ui.contracts.DetailFragmentContract;
import com.adkdevelopment.rssreader.ui.presenters.DetailFragmentPresenter;
import com.adkdevelopment.rssreader.utils.Utilities;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DetailFragment extends BaseFragment implements DetailFragmentContract.View {

    private static final String TAG = DetailFragment.class.getSimpleName();

    private DetailFragmentPresenter mPresenter;

    @BindView(R.id.detail_title)
    TextView mTextTitle;
    @BindView(R.id.detail_description)
    TextView mTextDescription;
    @BindView(R.id.detail_link)
    TextView mTextLink;
    @BindView(R.id.detail_date)
    TextView mTextDate;
    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.nested_scrollview)
    NestedScrollView mNestedScroll;
    private Unbinder mUnbinder;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(NewsObject item) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(NewsObject.NEWS_EXTRA, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        setHasOptionsMenu(true);

        mUnbinder = ButterKnife.bind(this, rootView);

        mPresenter = new DetailFragmentPresenter();
        mPresenter.attachView(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mPresenter.loadData(getArguments());
        } else {
            mPresenter.loadData(getActivity().getIntent());
        }
    }

    @Override
    public void showData(NewsObject newsItem) {

        //ImageView backdrop = ButterKnife.findById(getActivity(), R.id.backdrop);
        if (mBackdrop != null) {
            Picasso.with(getContext()).load(newsItem.getThumbnail()).into(mBackdrop, new Callback() {
                @Override
                public void onSuccess() {
                    // makes detail view colored according to the image palette
                    if (mBackdrop != null) {
                        BitmapDrawable drawable = (BitmapDrawable) mBackdrop.getDrawable();

                        int[] palette = Utilities.getPalette(drawable);
                        mNestedScroll.setBackgroundColor(palette[0]);
                        CollapsingToolbarLayout toolbarLayout =
                                ButterKnife.findById(getActivity(), R.id.collapsing_toolbar);
                        if (toolbarLayout != null) {
                            toolbarLayout.setContentScrimColor(palette[1]);
                        }

                        Toolbar toolbar = ButterKnife.findById(getActivity(), R.id.toolbar);
                        toolbar.setBackgroundColor(palette[2]);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getActivity().getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            window.setStatusBarColor(palette[2]);
                        }
                    }
                }

                @Override
                public void onError() {
                    Log.e(TAG, "onError: no image");
                }
            });
        }

        mTextDate.setText(Utilities.getFormattedDate(newsItem.getPubDate()));
        mTextTitle.setText(newsItem.getTitle());
        mTextDescription.setText(newsItem.getDescription());
        String learMore = getString(R.string.learn_more) + " " + newsItem.getLink();
        mTextLink.setText(learMore);
    }

    @Override
    public void showError() {
        Log.d(TAG, "Error");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        // Retrieve the share menu item
        MenuItem item = menu.findItem(R.id.share);

        // Get the provider and hold onto it to set/change the share intent.
        ShareActionProvider mShareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(item);

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(mPresenter.getShareIntent());
        } else {
            Log.e(TAG, "fail to set a share intent");
        }
    }
}

