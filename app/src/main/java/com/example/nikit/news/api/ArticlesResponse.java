package com.example.nikit.news.api;

import com.example.nikit.news.entities.Article;

import java.util.List;

/**
 * Created by nikit on 18.03.2017.
 */

public class ArticlesResponse {
    private List<Article> articles;

    public ArticlesResponse(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
