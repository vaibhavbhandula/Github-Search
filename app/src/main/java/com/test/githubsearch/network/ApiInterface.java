package com.test.githubsearch.network;

import com.test.githubsearch.data.GithubApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

public interface ApiInterface {

    @GET("repositories")
    Call<GithubApiResponse> searchRepos(@Query("q") String searchKey, @Query("page") int pageNo);
}
