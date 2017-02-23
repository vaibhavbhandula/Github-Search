package com.test.githubsearch.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

/**
 * Class for Github Repo Object
 * with getters and setters
 */
public class GithubRepo {

    @SerializedName("id") private int id;
    @SerializedName("full_name") private String fullName;
    @SerializedName("html_url") private String url;
    @SerializedName("description") private String description;
    @SerializedName("forks") private int numberOfForks;
    @SerializedName("watchers") private int numberOfWatchers;

    private boolean bookmark;

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfForks() {
        return numberOfForks;
    }

    public int getNumberOfWatchers() {
        return numberOfWatchers;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public boolean isBookmark() {
        return bookmark;
    }
}
