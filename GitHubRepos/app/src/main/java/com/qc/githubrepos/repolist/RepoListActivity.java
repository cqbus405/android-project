package com.qc.githubrepos.repolist;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.qc.githubrepos.R;
import com.qc.githubrepos.data.repo.RepoRemoteDataSource;
import com.qc.githubrepos.util.ActivityUtils;

public class RepoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.repolist_title);

        RepoListFragment fragment = (RepoListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = RepoListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        new RepoListPresenter(fragment, RepoRemoteDataSource.getInstance());

        if (savedInstanceState != null) {

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}