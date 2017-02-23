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
import com.test.githubsearch.utils.ResourceUtils;

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
        ResourceUtils.initialize(getApplicationContext());
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

    private void showSearch() {
        splashImage.setVisibility(View.GONE);
        searchLayout.setVisibility(View.VISIBLE);
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ?
                null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick(R.id.bt_search) void search() {
        hideKeyboard();
        String searchString = searchKey.getText().toString();
        if (searchString.isEmpty()) {
            Toast.makeText(this, ResourceUtils.getString(R.string.empty_search), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, SearchActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(SearchActivity.KEY_SEARCH, searchString);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }

    @OnEditorAction(R.id.et_search_key) boolean setEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            search();
            return true;
        }
        return false;
    }

}
