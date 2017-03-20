package com.example.nikit.news.ui.fragments;


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
import android.widget.Toast;

import com.example.nikit.news.Constants;
import com.example.nikit.news.R;
import com.example.nikit.news.api.ApiClient;
import com.example.nikit.news.entities.Article;
import com.example.nikit.news.entities.NewsEntity;
import com.example.nikit.news.ui.adapters.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetrofitFragment extends Fragment {
    private RecyclerView rvNews;
    private NewsAdapter newsAdapter;
    private Button btTouch;
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
        newsAdapter = new NewsAdapter();
        rvNews.setAdapter(newsAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(view.getContext()));

        btTouch = (Button)view.findViewById(R.id.bt_touch);

        btTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiClient.getInstance().getNewsEntity("the-next-web", "latest", Constants.API_KEY).enqueue(new Callback<NewsEntity>() {
                    @Override
                    public void onResponse(Call<NewsEntity> call, Response<NewsEntity> response) {
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                newsAdapter.swapData(response.body().getArticles());
                            }else{
                                Toast.makeText(getContext(), "wrong request", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsEntity> call, Throwable t) {
                        Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
