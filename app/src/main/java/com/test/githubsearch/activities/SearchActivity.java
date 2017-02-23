package com.test.githubsearch.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
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

    public static final String KEY_TYPE = "type";
    public static final String KEY_BOOKMARK = "bookmarks";
    public static final String KEY_SEARCH_VIEW = "search_view";
    public static final String KEY_SEARCH = "key_search";

    @BindView(R.id.toolbar_layout) Toolbar toolbar;
    @BindView(R.id.search_view) RecyclerView searchView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private String searchKey = "";
    private String type = KEY_SEARCH;
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
        handleAdapter();
    }

    @Override public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * Reading bundle from the Intent
     * and then Setting values for the search term or
     * type of adapter Eg: either for search or for bookmarks
     */
    private void readBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(KEY_SEARCH)) {
                searchKey = bundle.getString(KEY_SEARCH);
            }
            if (bundle.containsKey(KEY_TYPE)) {
                type = bundle.getString(KEY_TYPE);
            }

            if (searchKey.isEmpty()) {
                searchKey = KEY_BOOKMARK;
            }
        }
    }

    /**
     * Setting custom toolbar
     */
    private void setUpToolBar() {
        TextView toolBarTitle = ButterKnife.findById(toolbar, R.id.toolbar_title);
        toolBarTitle.setText(Utils.firstLetterCapital(searchKey));
        setSupportActionBar(toolbar);
    }

    /**
     * this method handles what type is there
     * if search then make search call
     * else show bookmarks
     */
    private void handleAdapter() {
        if (type.equals(KEY_SEARCH_VIEW)) {
            showProgress();
            makeSearchCall(false);
        } else {
            githubRepos = Utils.getAllBookmarks();
            setUpAdapter();
        }
    }

    /**
     * sets up the adapter
     */
    private void setUpAdapter() {
        repoAdapter = new RepoAdapter(this, githubRepos);
        searchView.setLayoutManager(new LinearLayoutManager(this));
        searchView.setAdapter(repoAdapter);
    }

    /**
     * makes an api call to github server to retrieve repos
     * according to search term
     * This checks if no results are there then shows a toast.
     *
     * @param addToAdapter boolean value representing whether
     *                     add to adapter or not
     *                     True - pagination call, add items to adapter
     *                     False - first search call, set up the adapter
     */
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
                        addInAdapter(response.body().getRepositories());
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

    /**
     * This method makes the api call for pagination
     * called from the adapter
     */
    public void searchNextPage() {
        if (!type.equals(KEY_BOOKMARK)) {
            progressBar.setVisibility(View.VISIBLE);
            pageNo++;
            makeSearchCall(true);
        }
    }

    /**
     * This method adds the repos retrieved from api call
     * from pagination
     *
     * @param repos ArrayList of Github Repositories
     */
    private void addInAdapter(ArrayList<GithubRepo> repos) {
        if (repoAdapter != null) {
            repoAdapter.addRepos(repos);
        }
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Show Progress Dialog
     */
    private void showProgress() {
        progressDialog = ProgressDialog.show(this, null, ResourceUtils.getString(R.string.loading), true, false);
        progressDialog.setCancelable(false);
    }

    /**
     * Hides Progress Dialog
     */
    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
