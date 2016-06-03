package com.qc.githubrepos.repolist;

import com.qc.githubrepos.BasePresenter;
import com.qc.githubrepos.BaseView;
import com.qc.githubrepos.data.Repo;

import java.util.ArrayList;

/**
 * Created by qiongchen on 6/2/16.
 */
public class RepoListContract {

    interface View extends BaseView<Presenter> {

        void updateAdapter(ArrayList<Repo> repoList, boolean isFirstTimeOrRefresh);

        void updateFooterView(boolean isVisible);

        void showMessage(String msg);

        void showContributors(String repo, String owner);
    }

    interface Presenter extends BasePresenter {

        void getRepoList(int page, boolean isFirstTimeOrRefresh);

        void startContributors(String repo, String owner);
    }
}
