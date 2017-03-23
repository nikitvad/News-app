package com.example.nikit.news.util;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.nikit.news.Constants;
import com.example.nikit.news.api.ApiClient;
import com.example.nikit.news.entities.NewsEntity;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by nikit on 20.03.2017.
 */

public class NetworkUtil {
    private Context context;

    public NetworkUtil(Context context){
        this.context = context;
    }

    public static NewsEntity getNewsFromSource(String sourceId, @Nullable String sortBy){
        try {
            Response<NewsEntity> response = ApiClient.getInstance()
                    .getNewsEntity(sourceId, sortBy, Constants.API_KEY).execute();

            if(response.isSuccessful() && response.body()!=null){
                for(NewsEntity.Article article: response.body().getArticles()){
                    article.setSourceId(sourceId);
                }
                return response.body();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

/*
    public HashMap<String, NewsEntity> getArticlesByCategory(String category){
        ArrayList<Source> sources = new ArrayList<>();
                try {
                    Response<Source> response = ApiClient.getInstance().getSources(category, null, null).execute();
                    if(response.isSuccessful() && response!=null){
                        sources.add(response.body());
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

        return null;
    }
    */




}
