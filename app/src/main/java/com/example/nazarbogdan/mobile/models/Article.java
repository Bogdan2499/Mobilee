package com.example.nazarbogdan.mobile.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bogdan2499 on 12.10.2018.
 */
public class Article {

    @SerializedName("author")
    private String author;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("url")
    private String url;
    @SerializedName("urlToImage")
    private String urlToImage;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("content")
    private String content;

    public Article(String title, String description, String author, String urlToImage) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getContent() {
        return content;
    }
}