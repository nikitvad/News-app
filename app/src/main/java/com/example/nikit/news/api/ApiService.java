package com.example.nikit.news.api;

import com.example.nikit.news.entities.Article;
import com.example.nikit.news.entities.NewsEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nikit on 18.03.2017.
 */

public interface ApiService {
    @GET("v1/articles")
    Call<NewsEntity> getNewsEntity(@Query("source") String sourceId, @Query("sortBy") String sortBy,
                                   @Query("apiKey") String apiKey);
}