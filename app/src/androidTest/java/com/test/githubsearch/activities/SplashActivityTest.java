package com.test.githubsearch.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.test.githubsearch.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> mSplashRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void splashActivityTest() throws Exception {

        Thread.sleep(3000);
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.et_search_key),
                        withParent(allOf(withId(R.id.search_layout),
                                withParent(withId(R.id.activity_splash)))),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Okhttp"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.bt_search), withText("Search"),
                        withParent(allOf(withId(R.id.search_layout),
                                withParent(withId(R.id.activity_splash)))),
                        isDisplayed()));
        appCompatButton.perform(click());

    }

}
