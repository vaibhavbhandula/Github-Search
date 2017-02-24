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

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumberOfForks(int numberOfForks) {
        this.numberOfForks = numberOfForks;
    }

    public void setNumberOfWatchers(int numberOfWatchers) {
        this.numberOfWatchers = numberOfWatchers;
    }

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
