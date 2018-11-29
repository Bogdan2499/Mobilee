package com.example.nazarbogdan.mobile.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bogdan2499 on 12.10.2018.
 */
public class Article {

    @SerializedName("author")
    private String mAuthor;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("urlToImage")
    private String mUrlToImage;
    @SerializedName("content")
    private String mContent;

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public String getContent() {
        return mContent;
    }


}