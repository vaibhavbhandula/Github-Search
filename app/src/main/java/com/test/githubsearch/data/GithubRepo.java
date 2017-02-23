package com.test.githubsearch.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

public class GithubRepo {

    @SerializedName("id") @Expose private int id;
    @SerializedName("full_name") @Expose private String fullName;
    @SerializedName("html_url") @Expose private String url;
    @SerializedName("description") @Expose private String description;
    @SerializedName("forks") @Expose private int numberOfForks;
    @SerializedName("watchers") @Expose private int numberOfWatchers;

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
