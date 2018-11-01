package com.example.nazarbogdan.mobile.retrofit;

import com.example.nazarbogdan.mobile.models.Result;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Bogdan2499 on 12.10.2018.
 */
public interface ApiService {
    @GET("top-headlines?sources=bbc-sport&apiKey=0dc97a219994467aaa953d8f72d07024")
    Call<Result> getNews();
}