package com.example.nikit.news.ui.fragments;


import android.database.sqlite.SQLiteDatabase;
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

import com.example.nikit.news.R;
import com.example.nikit.news.database.DatabaseManager;
import com.example.nikit.news.database.SqLiteDbHelper;
import com.example.nikit.news.entities.News;
import com.example.nikit.news.ui.adapters.NewsRvAdapter;
import com.example.nikit.news.util.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RetrofitFragment extends Fragment {
    private RecyclerView rvNews;
    private NewsRvAdapter newsRvAdapter;
    HashMap<String, String> resourceSortBy = new HashMap<>();

    private SQLiteDatabase database;
    private SqLiteDbHelper sqLiteDbHelper;
    public RetrofitFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sqLiteDbHelper = new SqLiteDbHelper(getContext());
        return inflater.inflate(R.layout.fragment_retrofit, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        DatabaseManager.getInstance().closeDatabase();
    }

    @Override
    public void onStart() {
        super.onStart();
        database = DatabaseManager.getInstance().openDatabase();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvNews = (RecyclerView)view.findViewById(R.id.rv_articles);
        newsRvAdapter = new NewsRvAdapter();
        rvNews.setAdapter(newsRvAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(view.getContext()));

        resourceSortBy.put("abc-news-au", "top");
        resourceSortBy.put("bild", "top");

        new LoadNewsAsyncTask().execute();

    }

    public void updateContent(){
        if(isAdded()) {
            new LoadNewsAsyncTask().execute();
        }
    }

    class LoadNewsAsyncTask extends AsyncTask<Void, News, Void>{
        private HashMap<String, News> newsEntityHashMap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            resourceSortBy.put("abc-news-au", "top");
            resourceSortBy.put("bild", "top");
            resourceSortBy.put("associated-press", "top");
            resourceSortBy.put("bbc-sport", "top");

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new LoadArticlesFromDbAsync().execute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if(NetworkUtil.isNetworkAvailable(getActivity())){
                Iterator<Map.Entry<String, String>> iterator = resourceSortBy.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, String> pair = iterator.next();
                    News news = NetworkUtil.getNewsFromSource(pair.getKey(), pair.getValue());

                    sqLiteDbHelper.insertArticles(database, news.getArticles());
                }

            }else{
                return null;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(News... values) {
            newsRvAdapter.addArticles(values[0].getArticles());
        }
    }

    class LoadArticlesFromDbAsync extends AsyncTask<Void, Void, Void>{
        private ArrayList<News.Article> articles;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(articles.size()>0){
                newsRvAdapter.swapData(articles);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            articles = sqLiteDbHelper.getAllArticles(database);
            return null;
        }
    }
}
