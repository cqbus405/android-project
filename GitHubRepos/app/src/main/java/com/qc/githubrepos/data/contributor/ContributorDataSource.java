package com.qc.githubrepos.data.contributor;

import com.qc.githubrepos.data.Owner;

import java.util.ArrayList;

/**
 * Created by qiongchen on 6/3/16.
 */
public interface ContributorDataSource {

    interface GetContributorsCallback {

        void onGetContributorsSuccessful(ArrayList<Owner> owners);

        void onGetContributorsFailed(String errMsg);
    }

    void getContributors (int page, String repo, String owner, GetContributorsCallback callback);
}
