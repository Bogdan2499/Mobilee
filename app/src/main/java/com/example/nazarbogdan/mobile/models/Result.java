package com.example.nazarbogdan.mobile.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vova0199 on 12.10.2018.
 */
public class Result {

    @SerializedName("articles")
    private List<Article> articles = null;

    public List<Article> getArticles() {
        return articles;
    }
}