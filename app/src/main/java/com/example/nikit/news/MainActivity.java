package com.example.nikit.news;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.nikit.news.json.JsonParser;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private Pager pager;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadAsyncTask().execute();
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.addTab(tabLayout.newTab().setText("News2"));
        tabLayout.addTab(tabLayout.newTab().setText("News4"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.pager);

        pager = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pager);
        tabLayout.setOnTabSelectedListener(this);


    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private class LoadAsyncTask extends AsyncTask<Void, Void, Void>{
        public LoadAsyncTask() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JsonParser parser = new JsonParser();
            parser.loadJsonString("http://newsapi.org/v1/articles?source=techcrunch&apiKey=6398112802324ebe8cf7caaf032514c1");
            return null;
        }
    }
}
