package com.qc.githubrepos.contributors;

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
import com.qc.githubrepos.data.Owner;
import com.qc.githubrepos.util.ActivityUtils;
import com.qc.githubrepos.util.Constants;

import java.util.ArrayList;

/**
 * Created by qiongchen on 6/2/16.
 */
public class ContributorsFragment extends Fragment implements ContributorsContract.View{

    private ContributorsContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerView;

    private ContributorAdapter mAdapter;

    private int page;

    private boolean mIsScrolling;

    private int mLastVisibleItem;

    private String owner;

    private String repo;

    public static ContributorsFragment newInstance(String repo, String owner) {

        Bundle args = new Bundle();
        args.putString(Constants.KEY_REPO, repo);
        args.putString(Constants.KEY_OWNER, owner);

        ContributorsFragment fragment = new ContributorsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        owner = getArguments().getString(Constants.KEY_OWNER);
        repo = getArguments().getString(Constants.KEY_REPO);

        page = 1;
        mIsScrolling = false;

        mPresenter.getContributors(1, owner, repo, true);
        mAdapter = new ContributorAdapter(getContext());

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.repolist_swiperefreshlayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                mPresenter.getContributors(1, owner, repo, true);
                page = 1;
            }
        });

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.repolist_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (mAdapter != null && !mIsScrolling && newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem == mAdapter.getItemCount() - 1) {
                    mIsScrolling = true;
                    mPresenter.getContributors(++page, owner, repo, false);
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
    public void onDestroyView() {
        super.onDestroyView();
        clean();
    }

    @Override
    public void setPresenter(ContributorsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateAdapter(ArrayList<Owner> owners, boolean isFirstTimeOrRefresh) {
        if (mAdapter != null) {
            if (isFirstTimeOrRefresh) {
                mAdapter.setContributors(owners);
            } else {
                mAdapter.updateContributors(owners);
            }
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
        ActivityUtils.showToast(msg, getActivity().getApplicationContext());
    }

    private void clean() {
        mRecyclerView.setAdapter(null);
        mSwipeRefreshLayout = null;
        mRecyclerView = null;
    }
}
