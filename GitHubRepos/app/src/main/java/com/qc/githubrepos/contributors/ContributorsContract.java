package com.qc.githubrepos.contributors;

import com.qc.githubrepos.BasePresenter;
import com.qc.githubrepos.BaseView;
import com.qc.githubrepos.data.Owner;

import java.util.ArrayList;

/**
 * Created by qiongchen on 6/2/16.
 */
public class ContributorsContract {

    interface View extends BaseView<Presenter> {

        void updateAdapter(ArrayList<Owner> owners, boolean isFirstTimeOrRefresh);

        void updateFooterView(boolean isVisible);

        void showMessage(String msg);
    }

    interface Presenter extends BasePresenter {

        void getContributors(int page, String owner, String repo, boolean isFirstTimeOrRefresh);
    }

}
