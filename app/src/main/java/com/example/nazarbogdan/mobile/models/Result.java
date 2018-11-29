package com.example.nazarbogdan.mobile.models;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * Created by Bogdan2499 on 12.10.2018.
 */
public class Result {

    @SerializedName("articles")
    private List<Article> mArticles = Collections.emptyList();

    public List<Article> getArticles() {
        return mArticles;
    }
}