package com.example.nikit.news.ui.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nikit.news.R;
import com.example.nikit.news.entities.NewsEntity;
import com.example.nikit.news.ui.adapters.NewsRvAdapter;
import com.example.nikit.news.util.NetworkUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RetrofitFragment extends Fragment {
    private RecyclerView rvNews;
    private NewsRvAdapter newsRvAdapter;
    private Button btTouch;
    HashMap<String, String> resourceSortBy = new HashMap<>();
    public RetrofitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_retrofit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvNews = (RecyclerView)view.findViewById(R.id.rv_articles);
        newsRvAdapter = new NewsRvAdapter();
        rvNews.setAdapter(newsRvAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(view.getContext()));

        btTouch = (Button)view.findViewById(R.id.bt_touch);

        resourceSortBy.put("abc-news-au", "top");
        resourceSortBy.put("bild", "top");


        btTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new loadNewsAsyncTask().execute();
            }
        });

    }


    class loadNewsAsyncTask extends AsyncTask<Void, NewsEntity, Void>{
        private HashMap<String, NewsEntity> newsEntityHashMap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            resourceSortBy.put("abc-news-au", "top");
            resourceSortBy.put("bild", "top");
            resourceSortBy.put("associated-press", "top");
            resourceSortBy.put("bbc-sport", "top");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Iterator<Map.Entry<String, String>> iterator = resourceSortBy.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, String> pair = iterator.next();
                NewsEntity entity = NetworkUtil.getNewsFromSource(pair.getKey(), pair.getValue());

                Log.d("entity", entity.toString());
                publishProgress(entity);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(NewsEntity... values) {
            newsRvAdapter.addArticles(values[0].getArticles());
        }
    }
}
