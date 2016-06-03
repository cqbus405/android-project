package com.qc.githubrepos.data.repo;

import com.qc.githubrepos.data.RepoList;
import com.qc.githubrepos.retrofit.ApiClient;
import com.qc.githubrepos.retrofit.ServerEndPointInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by qiongchen on 6/2/16.
 */
public class RepoRemoteDataSource implements RepoDataSource {

    private static RepoRemoteDataSource INSTANCE = null;

    public static RepoRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RepoRemoteDataSource();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getRepoList(int page, final GetRepoListCallback callback) {
        ServerEndPointInterface serverEndPointInterface = ApiClient.getClient().create(ServerEndPointInterface.class);

        Call<RepoList> call = serverEndPointInterface.getRepoList("created:\"2016-05-23 .. 2016-05-29\"", "stars", page, 10);
        call.enqueue(new Callback<RepoList>() {
            @Override
            public void onResponse(Call<RepoList> call, Response<RepoList> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    RepoList repoList = response.body();
                    callback.onGetListSuccessful(repoList);
                } else {
                    callback.onGetListFailed(statusCode + ": " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RepoList> call, Throwable t) {
                callback.onGetListFailed(t.getMessage());
            }
        });
    }
}
