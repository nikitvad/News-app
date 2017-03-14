package com.example.nikit.news.ui.rvAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikit.news.R;
import com.example.nikit.news.entities.Article;
import com.example.nikit.news.entities.NewsEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nikit on 14.03.2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticleViewHolder> {
    private ArrayList<Article> articles;


    public NewsAdapter(NewsEntity newsEntity) {
        articles = newsEntity.getArticles();
    }

    public NewsAdapter(ArrayList<Article> articles){
        this.articles = articles;
    }

    @Override
    public NewsAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ArticleViewHolder(inflater.inflate(R.layout.article_item_with_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        if(articles!=null) {
            holder.bindArticle(articles.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return  articles.size();
    }


    class ArticleViewHolder extends RecyclerView.ViewHolder{

        private TextView tvArticleTitle;
        private ImageView ivArticleImage;
        private TextView tvArticleDesc;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            tvArticleTitle = (TextView)itemView.findViewById(R.id.tv_article_title);
            tvArticleDesc = (TextView)itemView.findViewById(R.id.tv_article_desc);
            ivArticleImage = (ImageView)itemView.findViewById(R.id.iv_article_image);
        }

        public void bindArticle(Article article){
            tvArticleTitle.setText(article.getTitle());
            tvArticleDesc.setText(article.getTitle());
            Picasso.with(itemView.getContext()).load(article.getUrlToImage()).into(ivArticleImage);
        }
    }
}
