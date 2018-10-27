package com.example.nazarbogdan.mobile;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetails extends AppCompatActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvAuthor)
    TextView tvAuthor;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.ivPicture)
    ImageView ivPicture;
    String title, description, imageURL, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        getIncomingIntent();
        setInfo(title, description, author, imageURL);
    }

    private void getIncomingIntent() {
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        imageURL = getIntent().getStringExtra("image_url");
        author = getIntent().getStringExtra("author");
    }

    private void setInfo(String title, String description, String author, String imageUrl) {
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvAuthor.setText(author);
        Picasso.get().load(imageUrl).into(ivPicture);
    }

}
