package com.example.nikit.news.ui.activities;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.nikit.news.R;
import com.example.nikit.news.ui.adapters.PagerAdapter;
import com.example.nikit.news.ui.fragments.NewsFragment;
import com.example.nikit.news.ui.fragments.SourcesFragment;

public class MainActivity extends AppCompatActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setMinimumHeight(viewPager.getHeight()+toolbar.getHeight());
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager (ViewPager viewPager){
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new NewsFragment(), "News");
        pagerAdapter.addFragment(new SourcesFragment(), "Sources");
        viewPager.setAdapter(pagerAdapter);
    }

}
