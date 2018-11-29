package com.example.nazarbogdan.mobile.retrofit;

import com.example.nazarbogdan.mobile.models.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Bogdan2499 on 12.10.2018.
 */
public interface ApiService {
    @GET("top-headlines?sources=bbc-sport")
    Call<Result> getNews(@Query("apikey") String apikey);
}