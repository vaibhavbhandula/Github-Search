package com.test.githubsearch.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

public class ResourceUtils {

    private static WeakReference<Context> weakReference;

    public static void initialize(Context context) {
        weakReference = new WeakReference<>(context);
    }

    private static Context getContext() {
        return weakReference.get();
    }

    @NonNull public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    @NonNull public static String getString(int id, Object... args) {
        return getContext().getResources().getString(id, args);
    }
}
