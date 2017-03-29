package com.example.nikit.news.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikit.news.R;
//import com.example.nikit.news.entities.Article;
import com.example.nikit.news.entities.NewsEntity;
import com.example.nikit.news.firebase.FirebaseDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nikit on 14.03.2017.
 */

public class NewsRvAdapter extends RecyclerView.Adapter<NewsRvAdapter.ArticleViewHolder> {
    private final ArrayList<NewsEntity.Article> articles;


    public NewsRvAdapter(){
        articles = new ArrayList<>();
    }

    @Override
    public NewsRvAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ArticleViewHolder(inflater.inflate(R.layout.article_item, parent, false));
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

    public boolean swapData(ArrayList<NewsEntity.Article> newDataList){
        if(newDataList!=null && newDataList.size()>0){
            this.articles.clear();
            this.articles.addAll(newDataList);
            this.notifyDataSetChanged();
            return true;
        } else return false;
    }

    public boolean addArticles(ArrayList<NewsEntity.Article> newArticles){
        if(newArticles.size()>0){
            articles.addAll(newArticles);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{

        private TextView tvArticleTitle;
        private ImageView ivArticleImage;
        private TextView tvArticleDesc;
        private Button tbLike;
        private FirebaseDbHelper dbHelper = new FirebaseDbHelper();

        public ArticleViewHolder(View itemView) {
            super(itemView);

            tvArticleTitle = (TextView)itemView.findViewById(R.id.tv_article_title);
            tvArticleDesc = (TextView)itemView.findViewById(R.id.tv_article_desc);
            ivArticleImage = (ImageView)itemView.findViewById(R.id.iv_article_image);
            tbLike = (Button) itemView.findViewById(R.id.bt_like);

        }

        public void bindArticle(final NewsEntity.Article article){
            tvArticleTitle.setText(article.getTitle());
            tvArticleDesc.setText(article.getDescription());
            Picasso.with(itemView.getContext()).load(article.getUrlToImage()).into(ivArticleImage);
            tbLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbHelper.pushLikedNews(article);
                }
            });
        }

    }
}
