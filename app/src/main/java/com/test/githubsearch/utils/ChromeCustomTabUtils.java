package com.test.githubsearch.utils;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import com.test.githubsearch.R;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

public class ChromeCustomTabUtils {

    public static void openUrl(Context context, String url) {

        if (context == null || url == null) {
            return;
        }

        Uri uri = Uri.parse(url);

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

        intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        intentBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left);
        intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = intentBuilder.build();

        customTabsIntent.launchUrl(context, uri);
    }
}
