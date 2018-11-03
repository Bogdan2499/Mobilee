package com.example.nazarbogdan.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private String mTitle;
    private String mDescription;
    private String mImageURL;
    private String mAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        getIncomingIntent();
        setInfo(mTitle, mDescription, mAuthor, mImageURL);
    }

    private void getIncomingIntent() {
        mTitle = getIntent().getStringExtra(MainActivity.EXTRA_TITLE);
        mDescription = getIntent().getStringExtra(MainActivity.EXTRA_DESCRIPTION);
        mImageURL = getIntent().getStringExtra(MainActivity.EXTRA_IMAGE_PATH);
        mAuthor = getIntent().getStringExtra(MainActivity.EXTRA_AUTHOR);
    }

    private void setInfo(String title, String description, String author, String imageUrl) {
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvAuthor.setText(author);
        Picasso.get().load(imageUrl).into(ivPicture);
    }

}
