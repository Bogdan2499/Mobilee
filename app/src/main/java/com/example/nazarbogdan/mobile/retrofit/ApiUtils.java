package com.example.nazarbogdan.mobile.retrofit;

/**
 * Created by Bogdan2499 on 12.10.2018.
 */
public class ApiUtils {
    private static final String BASE_URL = "https://newsapi.org/v2/";

    public static ApiService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}