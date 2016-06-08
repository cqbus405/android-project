package com.qc.githubrepos.repolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qc.githubrepos.R;
import com.qc.githubrepos.contributors.ContributorsActivity;
import com.qc.githubrepos.data.Repo;
import com.qc.githubrepos.util.ActivityUtils;
import com.qc.githubrepos.util.Constants;
import com.qc.githubrepos.util.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by qiongchen on 6/2/16.
 */
public class RepoListFragment extends Fragment implements RepoListContract.View {

    private RepoListContract.Presenter mRepoListPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerView;

    private RepoListAdapter mAdapter;

    private int page;

    private boolean mIsScrolling;

    private int mLastVisibleItem;

    public static RepoListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RepoListFragment fragment = new RepoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = 1;
        mIsScrolling = false;

        mRepoListPresenter.getRepoList(1, true);
        mAdapter = new RepoListAdapter(getContext());
        mAdapter.setOnItemClickListener(new RepoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Repo repo) {
                mRepoListPresenter.startContributors(repo.getName(), repo.getOwner().getLogin());
            }
        });

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, container, false);

        // set SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.repolist_swiperefreshlayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                mRepoListPresenter.getRepoList(1, true);
                page = 1;
            }
        });

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        // set RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.repolist_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.divider));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (mAdapter != null && !mIsScrolling && newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem == mAdapter.getItemCount() - 1) {
                    mIsScrolling = true;
                    mRepoListPresenter.getRepoList(++page, false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mIsScrolling = false;
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                mAdapter.setFooterVisible(mIsScrolling);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void setPresenter(RepoListContract.Presenter presenter) {
        mRepoListPresenter = presenter;
    }

    @Override
    public void updateAdapter(ArrayList<Repo> repoList, boolean isFirstTimeOrRefresh) {
        if (mAdapter != null && isFirstTimeOrRefresh) {
            mAdapter.setRepos(repoList);
        } else if (mAdapter != null && !isFirstTimeOrRefresh) {
            mAdapter.upDateRepos(repoList);
        }
    }

    @Override
    public void updateFooterView(boolean isVisible) {
        if (mAdapter != null) {
            if (isVisible) {
                mAdapter.setFooterVisible(true);
            } else {
                mAdapter.setFooterVisible(false);
            }
        }
    }

    @Override
    public void showMessage(String msg) {
        ActivityUtils.showToast(msg, getContext());
    }

    @Override
    public void showContributors(String repo, String owner) {
        Intent intent = new Intent(getActivity(), ContributorsActivity.class);
        intent.putExtra(Constants.KEY_REPO, repo);
        intent.putExtra(Constants.KEY_OWNER, owner);
        getActivity().startActivity(intent);
    }
}
