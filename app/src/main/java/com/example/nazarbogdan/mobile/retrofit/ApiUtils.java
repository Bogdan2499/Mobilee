package com.example.nazarbogdan.mobile.Retrofit;

/**
 * Created by Bogdan2499 on 12.10.2018.
 */
public class ApiUtils {
    public static final String BASE_URL = "https://newsapi.org/v2/";

    public static ApiService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}