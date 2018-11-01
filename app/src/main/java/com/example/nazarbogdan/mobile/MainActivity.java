package com.example.nazarbogdan.mobile;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnItemCLickListener {

    private ApiService apiService;
    private NewsAdapter adapter = new NewsAdapter(this);
    @BindView(R.id.rvNews)
    RecyclerView rvGames;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        apiService = ApiUtils.getSOService();
        ButterKnife.bind(this);
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

    public void getNews() {
        apiService.getNews().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    adapter.replaceAll(response.body().getArticles());
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
        Intent intent = new Intent(this, NewsDetails.class);
        intent.putExtra("title", article.getTitle());
        intent.putExtra("description", article.getDescription());
        intent.putExtra("author", article.getAuthor());
        intent.putExtra("image_url", article.getUrlToImage());
        startActivity(intent);
    }
}