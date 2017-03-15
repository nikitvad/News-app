package com.example.nikit.news.ui.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.nikit.news.HidingScrollListener;
import com.example.nikit.news.R;
import com.example.nikit.news.util.JsonUtil;
import com.example.nikit.news.ui.adapters.NewsAdapter;
import com.example.nikit.news.util.ViewUtil;


public class NewsFragment extends Fragment {
    private RecyclerView rvArticles;
    private final NewsAdapter newsAdapter;

    public NewsFragment() {
       newsAdapter = new NewsAdapter();
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvArticles = (RecyclerView) view.findViewById(R.id.rv_articles_with_image);
        rvArticles.setAdapter(newsAdapter);
        rvArticles.setLayoutManager(new LinearLayoutManager(this.getContext()));

        rvArticles.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                //ViewUtil.hideToolbar(toolbar);
            }

            @Override
            public void onShow() {
                //ViewUtil.showToolbar(toolbar);
            }
        });

        new LoadNewsAsyncTask().execute();
    }

    // Loading news from network
    class LoadNewsAsyncTask extends AsyncTask<Void, Void, Void>{
        private String jsonString;
        private ProgressBar progressBar;
        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) getActivity().findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(jsonString != null && jsonString.length() > 0){
                newsAdapter.swapData(JsonUtil.getNewsFromJsonString(jsonString).getArticles());
            }
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            jsonString = JsonUtil.loadJsonString("https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=6398112802324ebe8cf7caaf032514c1");
            Log.d("jsonString", jsonString);
            return null;
        }
    }
}
