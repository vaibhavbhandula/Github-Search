package com.test.githubsearch.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

public class GithubApiResponse {

    @SerializedName("items") private ArrayList<GithubRepo> repositories = new ArrayList<>();

    public void setRepositories(ArrayList<GithubRepo> repositories) {
        this.repositories = repositories;
    }

    public ArrayList<GithubRepo> getRepositories() {
        return repositories;
    }
}
