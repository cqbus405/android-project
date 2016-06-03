package com.qc.githubrepos.contributors;

import com.qc.githubrepos.data.Owner;
import com.qc.githubrepos.data.contributor.ContributorDataSource;
import com.qc.githubrepos.data.contributor.ContributorRemoteDataSource;

import java.util.ArrayList;

/**
 * Created by qiongchen on 6/2/16.
 */
public class ContributorsPresenter implements ContributorsContract.Presenter {

    private final ContributorsContract.View mView;

    private final ContributorRemoteDataSource mSource;

    public ContributorsPresenter(ContributorsContract.View mView, ContributorRemoteDataSource mSource) {
        this.mView = mView;
        this.mSource = mSource;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getContributors(int page, String owner, String repo, final boolean isFirstTimeOrRefresh) {
        if (!isFirstTimeOrRefresh) {
            mView.updateFooterView(true);
        }

        mSource.getContributors(page, repo, owner, new ContributorDataSource.GetContributorsCallback() {
            @Override
            public void onGetContributorsSuccessful(ArrayList<Owner> owners) {
                mView.updateFooterView(false);
                mView.updateAdapter(owners, isFirstTimeOrRefresh);
            }

            @Override
            public void onGetContributorsFailed(String errMsg) {
                mView.updateFooterView(false);
                mView.showMessage(errMsg);
            }
        });
    }
}
