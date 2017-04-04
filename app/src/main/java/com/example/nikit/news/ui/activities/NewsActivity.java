package com.example.nikit.news.ui.activities;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nikit.news.Constants;
import com.example.nikit.news.R;
import com.example.nikit.news.SetupActivity;
import com.example.nikit.news.api.ApiClient;
//import com.example.nikit.news.entities.Article;
import com.example.nikit.news.entities.News;
import com.example.nikit.news.firebase.FirebaseDbHelper;
import com.example.nikit.news.ui.adapters.PagerAdapter;
import com.example.nikit.news.ui.fragments.RetrofitFragment;
import com.example.nikit.news.ui.fragments.SourcesFragment;
import com.example.nikit.news.ui.fragments.SourcesFromDb;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends SetupActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
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
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Fragment fragment = ((PagerAdapter)viewPager.getAdapter()).getFragmentByPos(0);
                if(fragment instanceof RetrofitFragment){
                    ((RetrofitFragment) fragment).updateContent();
                }
            }
        });
    }

    private void setupViewPager (ViewPager viewPager){
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new RetrofitFragment(), "Retrofit");
        pagerAdapter.addFragment(new SourcesFragment(), "Sources");

        viewPager.setAdapter(pagerAdapter);
    }
}
