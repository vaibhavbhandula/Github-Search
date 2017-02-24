package com.test.githubsearch.utils;

import com.google.gson.Gson;
import com.test.githubsearch.data.GithubApiResponse;
import com.test.githubsearch.data.GithubRepo;

import java.util.ArrayList;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

public class Utils {

    private static final String KEY_BOOKMARKS = "bookmarks";

    /**
     * Capitalise first letter of String
     *
     * @param name String
     * @return string
     */
    public static String firstLetterCapital(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
    }

    /**
     * Adds a Repo to Bookmark ArrayList
     *
     * @param repo Github Repo
     */
    public static void addBookmark(GithubRepo repo) {
        ArrayList<GithubRepo> githubRepos = getAllBookmarks();
        if (!contains(githubRepos, repo)) {
            githubRepos.add(repo);
        }
        putBookmarksInPrefs(githubRepos);
    }

    /**
     * Removes a github repo from the bookmarks
     *
     * @param repo Github Repo
     * @return boolean - true if removed else false
     */
    public static boolean removeBookmark(GithubRepo repo) {
        ArrayList<GithubRepo> githubRepos = getAllBookmarks();
        if (contains(githubRepos, repo)) {
            githubRepos.remove(getIndex(githubRepos, repo));
            putBookmarksInPrefs(githubRepos);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieve String from Prefs. Convert it to JSON using Gson
     * Converts JSON to GithubApiResponse Object
     * Returns arrayList of all Bookmarks stored in Prefs
     *
     * @return ArrayList
     */
    public static ArrayList<GithubRepo> getAllBookmarks() {
        ArrayList<GithubRepo> githubRepos = new ArrayList<>();
        String bookmarkString = PreferenceManager.getString(KEY_BOOKMARKS, "");
        if (!bookmarkString.isEmpty()) {
            Gson gson = new Gson();
            GithubApiResponse githubApiResponse = gson.fromJson(bookmarkString, GithubApiResponse.class);
            githubRepos = githubApiResponse.getRepositories();
        }
        return githubRepos;
    }

    /**
     * Checks if the arraylist item are present in bookmarks stored
     * if yes then enable bookmark in them
     * Called from Adapter
     * Enabling bookmark by default for repos in api call
     *
     * @param githubRepos ArrayList of Github Repos
     */
    public static void markBookmark(ArrayList<GithubRepo> githubRepos) {
        ArrayList<GithubRepo> githubRepoArrayList = getAllBookmarks();
        if (githubRepoArrayList == null || githubRepoArrayList.isEmpty()) {
            return;
        }
        for (GithubRepo githubRepo : githubRepos) {
            if (contains(githubRepoArrayList, githubRepo)) {
                githubRepo.setBookmark(true);
            }
        }
    }

    /**
     * Add ArrayList to GithubApiResponse Object
     * Converts GithubApiResponse object to String Using Gson
     * Store in Prefs
     *
     * @param githubRepos ArrayList
     */
    private static void putBookmarksInPrefs(ArrayList<GithubRepo> githubRepos) {
        Gson gson = new Gson();
        GithubApiResponse githubApiResponse = new GithubApiResponse();
        githubApiResponse.setRepositories(githubRepos);
        String prefString = gson.toJson(githubApiResponse);
        PreferenceManager.putString(KEY_BOOKMARKS, prefString);
    }

    /**
     * Checks if a repo is present in arraylist of repos
     *
     * @param arrayList ArrayList of Repos
     * @param repo      Github Repo
     * @return boolean
     */
    public static boolean contains(ArrayList<GithubRepo> arrayList, GithubRepo repo) {
        for (GithubRepo githubRepo : arrayList) {
            if (githubRepo.getId() == repo.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the index of a repo object in ArrayList.
     * Used for removing bookmark
     *
     * @param arrayList ArrayList
     * @param repo      Github Repo
     * @return int index of Repo if found else 0
     */
    public static int getIndex(ArrayList<GithubRepo> arrayList, GithubRepo repo) {
        if (contains(arrayList, repo)) {
            for (GithubRepo githubRepo : arrayList) {
                if (githubRepo.getId() == repo.getId()) {
                    return arrayList.indexOf(githubRepo);
                }
            }
        }
        return 0;
    }
}
