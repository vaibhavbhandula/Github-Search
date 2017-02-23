package com.test.githubsearch.utils;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import com.test.githubsearch.R;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

public class ChromeCustomTabUtils {

    /**
     * Opens the url in chrome custom tab
     *
     * @param context Context reference
     * @param url     Url of WebPage
     */
    public static void openUrl(Context context, String url) {

        if (context == null || url == null || url.isEmpty()) {
            return;
        }

        Uri uri = Uri.parse(url);

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

        intentBuilder.setToolbarColor(ResourceUtils.getColor(R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ResourceUtils.getColor(R.color.colorPrimaryDark));

        intentBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left);
        intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = intentBuilder.build();

        customTabsIntent.launchUrl(context, uri);
    }
}
