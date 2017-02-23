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

    public static String firstLetterCapital(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
    }

    public static void addBookmark(GithubRepo repo) {
        ArrayList<GithubRepo> githubRepos = getAllBookmarks();
        if (!contains(githubRepos, repo)) {
            githubRepos.add(repo);
        }
        putBookmarksInPrefs(githubRepos);
    }

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

    private static void putBookmarksInPrefs(ArrayList<GithubRepo> githubRepos) {
        Gson gson = new Gson();
        GithubApiResponse githubApiResponse = new GithubApiResponse();
        githubApiResponse.setRepositories(githubRepos);
        String prefString = gson.toJson(githubApiResponse);
        PreferenceManager.putString(KEY_BOOKMARKS, prefString);
    }

    private static boolean contains(ArrayList<GithubRepo> arrayList, GithubRepo repo) {
        for (GithubRepo githubRepo : arrayList) {
            if (githubRepo.getId() == repo.getId()) {
                return true;
            }
        }
        return false;
    }

    private static int getIndex(ArrayList<GithubRepo> arrayList, GithubRepo repo) {
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
