package com.test.githubsearch.utils;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.githubsearch.data.GithubRepo;

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
        SparseArray<GithubRepo> sparseArray = getAllBookmarksSparseArray();
        sparseArray.put(repo.getId(), repo);
        putHashMapInPrefs(sparseArray);
    }

    public static void removeBookmark(int id) {
        SparseArray<GithubRepo> sparseArray = getAllBookmarksSparseArray();
        sparseArray.remove(id);
        putHashMapInPrefs(sparseArray);
    }

    private static SparseArray<GithubRepo> getAllBookmarksSparseArray() {
        SparseArray<GithubRepo> githubRepoSparseArray = new SparseArray<>();
        String sparseArrayString = PreferenceManager.getString(KEY_BOOKMARKS, "");
        if (!sparseArrayString.isEmpty()) {
            Gson gson = new Gson();
            githubRepoSparseArray = gson.fromJson(sparseArrayString,
                    new TypeToken<SparseArray<GithubRepo>>() {
                    }.getType());
        }
        return githubRepoSparseArray;
    }

    private static void putHashMapInPrefs(SparseArray<GithubRepo> sparseArray) {
        Gson gson = new Gson();
        String prefString = gson.toJson(sparseArray);
        PreferenceManager.putString(KEY_BOOKMARKS, prefString);
    }
}
