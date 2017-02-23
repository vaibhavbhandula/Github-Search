package com.test.githubsearch.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.test.githubsearch.R;
import com.test.githubsearch.data.GithubRepo;
import com.test.githubsearch.utils.PreferenceManager;
import com.test.githubsearch.utils.ResourceUtils;
import com.test.githubsearch.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 3000;

    @BindView(R.id.splash_image) ImageView splashImage;
    @BindView(R.id.search_layout) LinearLayout searchLayout;
    @BindView(R.id.et_search_key) EditText searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // Initialize Preference and Resources
        ResourceUtils.initialize(getApplicationContext());
        PreferenceManager.initialize(getApplicationContext());

        // Show splash at start
        searchLayout.setVisibility(View.GONE);
        splashImage.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                showSearch();
            }
        }, SPLASH_TIME);
    }

    @Override public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * show Search View
     */
    private void showSearch() {
        splashImage.setVisibility(View.GONE);
        searchLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Hide Keyboard
     */
    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ?
                null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Handle click of search button
     * Checks for the search term entered.
     * If empty shows a toast.
     */
    @OnClick(R.id.bt_search) void search() {
        hideKeyboard();
        String searchString = searchKey.getText().toString();
        if (searchString.isEmpty()) {
            Toast.makeText(this, ResourceUtils.getString(R.string.empty_search), Toast.LENGTH_SHORT).show();
        } else {
            searchKey.setText("");
            Intent intent = new Intent(this, SearchActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(SearchActivity.KEY_SEARCH, searchString);
            bundle.putString(SearchActivity.KEY_TYPE, SearchActivity.KEY_SEARCH_VIEW);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    /**
     * Handle click of bookmarks button
     * If no bookmarks, shows a toast.
     */
    @OnClick(R.id.bt_bookmarks) void showBookmarks() {
        ArrayList<GithubRepo> githubRepos = Utils.getAllBookmarks();
        if (githubRepos == null || githubRepos.isEmpty()) {
            Toast.makeText(this, ResourceUtils.getString(R.string.no_bookmark), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, SearchActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(SearchActivity.KEY_TYPE, SearchActivity.KEY_BOOKMARK);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    /**
     * Handling of Enter or Done of Keyboard
     * Calls search method
     */
    @OnEditorAction(R.id.et_search_key) boolean setEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            search();
            return true;
        }
        return false;
    }

}
