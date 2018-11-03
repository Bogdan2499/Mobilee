package com.example.nazarbogdan.mobile;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.nazarbogdan.mobile.adapter.NewsAdapter;
import com.example.nazarbogdan.mobile.models.Article;
import com.example.nazarbogdan.mobile.models.Result;
import com.example.nazarbogdan.mobile.retrofit.ApiService;
import com.example.nazarbogdan.mobile.retrofit.ApiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.nazarbogdan.mobile.NewsDetails.getStartIntentFavourite;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnItemCLickListener {

    private ApiService mApiService;
    private NewsAdapter mAdapter = new NewsAdapter(this);
    private static final String APIKEY = "0dc97a219994467aaa953d8f72d07024";
    @BindView(R.id.rvNews)
    RecyclerView mRvGames;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout sPullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mApiService = ApiUtils.getSOService();
        ButterKnife.bind(this);
        getNews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRvGames.setLayoutManager(layoutManager);
        mRvGames.setAdapter(mAdapter);
        sPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNews();
                sPullToRefresh.setRefreshing(false);
            }
        });
    }

    public void getNews() {
        mApiService.getNews(APIKEY).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    mAdapter.replaceAll(response.body().getArticles());
                } else {
                    Log.e("Error", "News don't load");
                    Toast.makeText(getApplicationContext(), "error loading from API",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
                Toast.makeText(getApplicationContext(), "error loading from API",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onGameClick(Article article) {
        startActivity(getStartIntentFavourite(this, article));
    }

}