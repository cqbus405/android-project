package com.qc.githubrepos.contributors;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.qc.githubrepos.R;
import com.qc.githubrepos.data.contributor.ContributorRemoteDataSource;
import com.qc.githubrepos.util.ActivityUtils;
import com.qc.githubrepos.util.Constants;

/**
 * Created by qiongchen on 6/2/16.
 */
public class ContributorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.contributor_title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ContributorsFragment fragment = (ContributorsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = ContributorsFragment.newInstance(getIntent().getStringExtra(Constants.KEY_REPO), getIntent().getStringExtra(Constants.KEY_OWNER));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        new ContributorsPresenter(fragment, ContributorRemoteDataSource.getInstance());

        if (savedInstanceState != null) {

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
