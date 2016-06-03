package com.qc.githubrepos.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by qiongchen on 6/2/16.
 */
public class RepoList {

    @SerializedName("total_count")
    @Expose
    private int totalCount;

    @SerializedName("incomplete_results")
    @Expose
    private boolean incompleteResults;

    @Expose
    private ArrayList<Repo> items;

    public RepoList(int totalCount, boolean incompleteResults, ArrayList<Repo> items) {
        this.totalCount = totalCount;
        this.incompleteResults = incompleteResults;
        this.items = items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public ArrayList<Repo> getItems() {
        return items;
    }

    public void setItems(ArrayList<Repo> items) {
        this.items = items;
    }
}
