package com.test.githubsearch.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

public class ApiClient {

    public static final int HTTP_OK = 200;

    private static final String BASE_URL = "https://api.github.com/search/";

    private static Retrofit retrofit = null;

    /**
     * Returns Retrofit client for Github URL
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
