package com.test.githubsearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.githubsearch.R;
import com.test.githubsearch.data.GithubRepo;
import com.test.githubsearch.utils.ResourceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<GithubRepo> githubRepos;

    public RepoAdapter(Context context, ArrayList<GithubRepo> githubRepos) {
        this.context = context;
        this.githubRepos = githubRepos;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_layout, parent, false));
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RepoHolder) holder).bind(position);
    }

    @Override public int getItemCount() {
        return githubRepos.size();
    }

    protected class RepoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repo_name) TextView tvRepoName;
        @BindView(R.id.tv_repo_description) TextView tvDescription;
        @BindView(R.id.tv_forks) TextView tvForks;
        @BindView(R.id.tv_watchers) TextView tvWatchers;

        GithubRepo repo;

        RepoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            repo = githubRepos.get(position);
            tvRepoName.setText(repo.getFullName());
            tvDescription.setText(repo.getDescription());
            tvForks.setText(ResourceUtils.getString(R.string.forks, repo.getNumberOfForks()));
            tvWatchers.setText(ResourceUtils.getString(R.string.watchers, repo.getNumberOfWatchers()));
        }
    }
}
