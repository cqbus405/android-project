package com.qc.githubrepos.retrofit;

import com.qc.githubrepos.data.Owner;
import com.qc.githubrepos.data.RepoList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by qiongchen on 6/2/16.
 */
public interface ServerEndPointInterface {

    @Headers({
            "Accept: application/vnd.github.v3+json",
            "User-Agent: cqbus405/android-project"
    })
    @GET("/search/repositories")
    Call<RepoList> getRepoList(@Query("q") String q, @Query("sort") String sort, @Query("page") int page, @Query("per_page") int perPage, @Query("client_id") String id, @Query("client_secret") String secret);

    @Headers({
            "Accept: application/vnd.github.v3+json",
            "User-Agent: cqbus405/android-project"
    })
    @GET("/repos/{owner}/{repo}/contributors")
    Call<ArrayList<Owner>> getContributors(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page, @Query("per_page") int perPage, @Query("client_id") String id, @Query("client_secret") String secret);
}
