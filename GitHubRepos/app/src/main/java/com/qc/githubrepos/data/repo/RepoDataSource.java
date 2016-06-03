package com.qc.githubrepos.data.repo;

import com.qc.githubrepos.data.RepoList;

/**
 * Created by qiongchen on 6/2/16.
 */
public interface RepoDataSource {

    interface GetRepoListCallback {

        void onGetListSuccessful(RepoList repoList);

        void onGetListFailed(String errorMsg);
    }

    void getRepoList(int page, GetRepoListCallback callback);
}
