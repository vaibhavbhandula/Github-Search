package com.test.githubsearch.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.test.githubsearch.R;
import com.test.githubsearch.utils.ResourceUtils;
import com.test.githubsearch.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    public static final String KEY_SEARCH = "key_search";

    @BindView(R.id.toolbar_layout) Toolbar toolbar;
    @BindView(R.id.search_view) RecyclerView searchView;

    private String searchKey = "";
    private int pageNo = 1;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        readBundle();
        setUpToolBar();
        showProgress();
        makeSearchCall();
    }

    private void readBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(KEY_SEARCH)) {
            searchKey = bundle.getString(KEY_SEARCH);
        }
    }

    private void setUpToolBar() {
        TextView toolBarTitle = ButterKnife.findById(toolbar, R.id.toolbar_title);
        toolBarTitle.setText(Utils.firstLetterCapital(searchKey));
        setSupportActionBar(toolbar);
    }

    private void makeSearchCall() {

    }

    private void showProgress() {
        progressDialog = ProgressDialog.show(this, null, ResourceUtils.getString(R.string.loading), true, false);
        progressDialog.setCancelable(false);
    }

    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
