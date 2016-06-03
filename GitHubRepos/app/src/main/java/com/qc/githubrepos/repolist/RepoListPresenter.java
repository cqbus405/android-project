package com.qc.githubrepos.repolist;

import com.qc.githubrepos.data.RepoList;
import com.qc.githubrepos.data.repo.RepoDataSource;
import com.qc.githubrepos.data.repo.RepoRemoteDataSource;

/**
 * Created by qiongchen on 6/2/16.
 */
public class RepoListPresenter implements RepoListContract.Presenter {

    private final RepoListContract.View mRepoListView;

    private final RepoRemoteDataSource mRepoRepository;

    public RepoListPresenter(RepoListContract.View view, RepoRemoteDataSource repository) {
        this.mRepoListView = view;
        this.mRepoRepository = repository;

        mRepoListView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getRepoList(int page, final boolean isFirstTimeOrRefresh) {
        if (!isFirstTimeOrRefresh) {
            mRepoListView.updateFooterView(true);
        }

        mRepoRepository.getRepoList(page, new RepoDataSource.GetRepoListCallback() {
            @Override
            public void onGetListSuccessful(RepoList repoList) {
                mRepoListView.updateFooterView(false);
                mRepoListView.updateAdapter(repoList.getItems(), isFirstTimeOrRefresh);
            }

            @Override
            public void onGetListFailed(String errorMsg) {
                mRepoListView.updateFooterView(false);
                mRepoListView.showMessage(errorMsg);
            }
        });
    }

    @Override
    public void startContributors(String repo, String owner) {
        mRepoListView.showContributors(repo, owner);
    }
}
