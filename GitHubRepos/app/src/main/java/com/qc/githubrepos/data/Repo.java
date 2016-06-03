package com.qc.githubrepos.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by qiongchen on 6/2/16.
 */
public class Repo {

    @Expose
    private String name;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @Expose
    private Owner owner;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("stargazers_count")
    @Expose
    private int star;

    @Expose
    private String description;

    public String getName() {
        return name;
    }

    public Repo(String name, String fullName, Owner owner, String createdAt, int star, String description) {
        this.name = name;
        this.fullName = fullName;
        this.owner = owner;
        this.createdAt = createdAt;
        this.star = star;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
