package com.example.nazarbogdan.mobile;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nazarbogdan.mobile.Adapter.NewsAdapter;
import com.example.nazarbogdan.mobile.Models.Article;
import com.example.nazarbogdan.mobile.Models.Result;
import com.example.nazarbogdan.mobile.Retrofit.ApiService;
import com.example.nazarbogdan.mobile.Retrofit.ApiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NewsAdapter.Callback {

    private ApiService apiService;
    private NewsAdapter adapter = new NewsAdapter(this, this);
    Button btnFavourite;
    @BindView(R.id.rvNews)
    RecyclerView rvGames;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout pullToRefresh;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbarText;
    @BindView(R.id.include_main)
    View myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = ApiUtils.getSOService();
        ButterKnife.bind(this);
        btnFavourite = ButterKnife.findById(myLayout, R.id.btn_fav);
        getNews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvGames.setLayoutManager(layoutManager);
        rvGames.setAdapter(adapter);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNews();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @OnClick(R.id.btn_fav)
    void submitButton(View view) {
        Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
        startActivity(intent);
    }

    public void getNews() {
        apiService.getNews().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    adapter.replaceAll(response.body().getArticles());
                } else {
                    Log.e("Error", "News dont load");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }
        });
    }

    @Override
    public void onGameClick(Article article) {
        Intent intent = new Intent(this, NewsDetails.class);
        intent.putExtra("title", article.getTitle());
        intent.putExtra("description", article.getDescription());
        intent.putExtra("author", article.getAuthor());
        intent.putExtra("image_url", article.getUrlToImage());
        startActivity(intent);
    }
}