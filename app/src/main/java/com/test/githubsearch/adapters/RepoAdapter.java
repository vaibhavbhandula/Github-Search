package com.test.githubsearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.githubsearch.R;
import com.test.githubsearch.activities.SearchActivity;
import com.test.githubsearch.data.GithubRepo;
import com.test.githubsearch.utils.ChromeCustomTabUtils;
import com.test.githubsearch.utils.ResourceUtils;
import com.test.githubsearch.utils.Utils;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    public void addRepos(ArrayList<GithubRepo> repos) {
        int pos = this.githubRepos.size();
        this.githubRepos.addAll(repos);
        notifyItemInserted(pos + 1);
    }

    protected class RepoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repo_name) TextView tvRepoName;
        @BindView(R.id.tv_repo_description) TextView tvDescription;
        @BindView(R.id.tv_forks) TextView tvForks;
        @BindView(R.id.tv_watchers) TextView tvWatchers;
        @BindView(R.id.bookmark_icon) SparkButton bookmarkIcon;

        GithubRepo repo;
        View view;

        RepoHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            setWidth();
            repo = githubRepos.get(position);
            tvRepoName.setText(repo.getFullName());
            if (repo.getDescription() == null || repo.getDescription().isEmpty()) {
                tvDescription.setVisibility(View.GONE);
            } else {
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText(repo.getDescription());
            }
            tvForks.setText(ResourceUtils.getString(R.string.forks, repo.getNumberOfForks()));
            tvWatchers.setText(ResourceUtils.getString(R.string.watchers, repo.getNumberOfWatchers()));
            bookmarkIcon.setChecked(repo.isBookmark());
            bookmarkIcon.setEventListener(new SparkEventListener() {
                @Override public void onEvent(ImageView button, boolean buttonState) {
                    repo.setBookmark(buttonState);
                    if (buttonState) {
                        Utils.addBookmark(repo);
                    } else {
                        Utils.removeBookmark(repo.getId());
                    }
                }
            });

            if (position == githubRepos.size() - 1) {
                if (context instanceof SearchActivity) {
                    ((SearchActivity) context).searchNextPage();
                }
            }
        }

        private void setWidth() {
            RelativeLayout layout = ButterKnife.findById(view, R.id.name_layout);
            layout.measure(0, 0);
            int nameLayoutWidth = layout.getMeasuredWidth();
            int likeButtonWidth = ButterKnife.findById(view, R.id.bookmark_icon).getLayoutParams().width;

            tvRepoName.getLayoutParams().width = nameLayoutWidth - likeButtonWidth;
        }

        @OnClick(R.id.repo_layout) void openRepo() {
            ChromeCustomTabUtils.openUrl(context, repo.getUrl());
        }
    }
}
