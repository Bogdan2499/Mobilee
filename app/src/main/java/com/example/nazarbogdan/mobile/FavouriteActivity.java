package com.example.nazarbogdan.mobile;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nazarbogdan.mobile.Adapter.NewsAdapter;
import com.example.nazarbogdan.mobile.Models.Article;
import com.example.nazarbogdan.mobile.SQLite.SQLite;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteActivity extends AppCompatActivity implements NewsAdapter.Callback {

    private NewsAdapter adapter = new NewsAdapter(this, this);
    private Cursor c;
    private ArrayList<Article> articles = new ArrayList<>();
    @BindView(R.id.rvFavourite)
    RecyclerView rvGames;
    @BindView(R.id.swipeContainerFav)
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        init();
    }

    private void getNews() {
        articles.clear();
        String title, description, imageURL, author;
        SQLite db = new SQLite(this);
        c = db.queueAll();
        while (c.moveToNext()) {
            title = c.getString(0);
            description = c.getString(1);
            imageURL = c.getString(3);
            author = c.getString(2);
            Article film = new Article(title, description, author, imageURL);
            articles.add(film);
        }

        adapter.replaceAll(articles);

        if (!(articles.size() < 1)) {
            rvGames.setAdapter(adapter);
        }
    }

    private void init() {
        ButterKnife.bind(this);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNews();
                adapter.replaceAll(articles);
                pullToRefresh.setRefreshing(false);
            }
        });
        getNews();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvGames.setLayoutManager(layoutManager);
        rvGames.setAdapter(adapter);
        rvGames.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
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
