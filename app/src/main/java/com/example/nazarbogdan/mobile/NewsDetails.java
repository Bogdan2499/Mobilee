package com.example.nazarbogdan.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nazarbogdan.mobile.models.Article;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetails extends AppCompatActivity {

    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_DESCRIPTION = "description";
    private static final String EXTRA_AUTHOR = "author";
    private static final String EXTRA_IMAGE_PATH = "image_url";

    public static Intent getStartIntentFavourite(Context context, Article article){
        Intent intent = new Intent(context, NewsDetails.class);
        intent.putExtra(EXTRA_TITLE, article.getTitle());
        intent.putExtra(EXTRA_DESCRIPTION, article.getDescription());
        intent.putExtra(EXTRA_AUTHOR, article.getAuthor());
        intent.putExtra(EXTRA_IMAGE_PATH, article.getUrlToImage());
        return intent;
    }

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvAuthor)
    TextView mTvAuthor;
    @BindView(R.id.tvDescription)
    TextView mTvDescription;
    @BindView(R.id.ivPicture)
    ImageView mIvPicture;
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
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mDescription = getIntent().getStringExtra(EXTRA_DESCRIPTION);
        mImageURL = getIntent().getStringExtra(EXTRA_IMAGE_PATH);
        mAuthor = getIntent().getStringExtra(EXTRA_AUTHOR);
    }
    private void setInfo(String title, String description, String author, String imageUrl) {
        mTvTitle.setText(title);
        mTvDescription.setText(description);
        mTvAuthor.setText(author);
        Picasso.get().load(imageUrl).into(mIvPicture);
    }
}
