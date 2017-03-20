package com.example.nikit.news.util;

import android.content.Context;
import android.widget.Toast;

import com.example.nikit.news.Constants;
import com.example.nikit.news.api.ApiClient;
import com.example.nikit.news.api.ApiService;
import com.example.nikit.news.entities.Article;
import com.example.nikit.news.entities.NewsEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nikit on 20.03.2017.
 */

public class NetworkUtil {
    private Context context;

    public NetworkUtil(Context context){
        this.context = context;
    }

    public HashMap<String, NewsEntity> getArticlesFromResources(final HashMap<String, String> resourceSortBy){
        final HashMap<String, NewsEntity> result;

        ApiService client = ApiClient.getInstance();

        if(resourceSortBy.size()>0){
            result = new HashMap<>();

            Iterator<Map.Entry<String, String>> iterator = resourceSortBy.entrySet().iterator();
            while (iterator.hasNext()){
                final Map.Entry<String, String> pair = iterator.next();
                client.getNewsEntity(pair.getKey(), pair.getValue(), Constants.API_KEY)
                        .enqueue(new Callback<NewsEntity>() {
                            @Override
                            public void onResponse(Call<NewsEntity> call, Response<NewsEntity> response) {
                                if(response.isSuccessful()){
                                    if(response.body()!=null){
                                        result.put(pair.getKey(), response.body());
                                    }
                                }else{
                                    Toast.makeText(context, "Request error", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<NewsEntity> call, Throwable t) {
                                Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }

        return null;
    }
}
