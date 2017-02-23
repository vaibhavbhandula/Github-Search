package com.test.githubsearch.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.test.githubsearch.R;
import com.test.githubsearch.adapters.RepoAdapter;
import com.test.githubsearch.data.GithubApiResponse;
import com.test.githubsearch.data.GithubRepo;
import com.test.githubsearch.network.ApiClient;
import com.test.githubsearch.network.ApiInterface;
import com.test.githubsearch.utils.ResourceUtils;
import com.test.githubsearch.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    public static final String KEY_SEARCH = "key_search";

    @BindView(R.id.toolbar_layout) Toolbar toolbar;
    @BindView(R.id.search_view) RecyclerView searchView;

    private String searchKey = "";
    private int pageNo = 1;
    private ProgressDialog progressDialog;

    private RepoAdapter repoAdapter;
    private ArrayList<GithubRepo> githubRepos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        readBundle();
        setUpToolBar();
        showProgress();
        makeSearchCall(false);
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

    private void setUpAdapter() {
        repoAdapter = new RepoAdapter(this, githubRepos);
        searchView.setLayoutManager(new LinearLayoutManager(this));
        searchView.setAdapter(repoAdapter);
    }

    private void makeSearchCall(final boolean addToAdapter) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GithubApiResponse> call = apiInterface.searchRepos(searchKey, pageNo);
        call.enqueue(new Callback<GithubApiResponse>() {
            @Override public void onResponse(Call<GithubApiResponse> call, Response<GithubApiResponse> response) {
                hideProgress();
                if (response.code() == ApiClient.HTTP_OK && response.body() != null) {
                    if (response.body().getRepositories() == null || response.body().getRepositories().isEmpty()) {
                        Toast.makeText(SearchActivity.this, ResourceUtils.getString(R.string.empty_results),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        githubRepos.addAll(response.body().getRepositories());
                    }
                    if (addToAdapter) {
                        // notify in adapter of items added
                    } else {
                        setUpAdapter();
                    }
                } else {
                    onFailure(call, new Throwable());
                }
            }

            @Override public void onFailure(Call<GithubApiResponse> call, Throwable t) {
                hideProgress();
                Toast.makeText(SearchActivity.this, ResourceUtils.getString(R.string.search_fail),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchNextPage() {
        pageNo++;
        makeSearchCall(true);
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
