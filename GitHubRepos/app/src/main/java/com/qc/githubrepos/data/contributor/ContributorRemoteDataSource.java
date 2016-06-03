package com.qc.githubrepos.data.contributor;

import com.qc.githubrepos.data.Owner;
import com.qc.githubrepos.retrofit.ApiClient;
import com.qc.githubrepos.retrofit.ServerEndPointInterface;
import com.qc.githubrepos.util.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by qiongchen on 6/3/16.
 */
public class ContributorRemoteDataSource implements ContributorDataSource {

    private static ContributorRemoteDataSource INSTANCE = null;

    public static ContributorRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ContributorRemoteDataSource();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getContributors(int page, String repo, String owner, final GetContributorsCallback callback) {
        ServerEndPointInterface serverEndPointInterface = ApiClient.getClient().create(ServerEndPointInterface.class);

        Call<ArrayList<Owner>> call = serverEndPointInterface.getContributors(owner, repo, page, 10, Constants.CLIENT_ID, Constants.CLINET_SECRET);
        call.enqueue(new Callback<ArrayList<Owner>>() {
            @Override
            public void onResponse(Call<ArrayList<Owner>> call, Response<ArrayList<Owner>> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    callback.onGetContributorsSuccessful(response.body());
                } else {
                    callback.onGetContributorsFailed(statusCode + ": " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Owner>> call, Throwable t) {
                callback.onGetContributorsFailed(t.getMessage());
            }
        });
    }
}
