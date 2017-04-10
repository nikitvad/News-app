package com.example.nikit.news.ui.adapter;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nikit.news.R;
import com.example.nikit.news.database.DatabaseManager;
import com.example.nikit.news.database.SqLiteDbHelper;
import com.example.nikit.news.entities.News;
import com.example.nikit.news.firebase.FirebaseDbHelper;
import com.example.nikit.news.ui.activity.WebViewActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nikit on 14.03.2017.
 */

public class NewsRvAdapter extends RecyclerView.Adapter<NewsRvAdapter.ArticleViewHolder> {
    private final ArrayList<News.Article> articles;
    FirebaseDbHelper firebaseDbHelper;
    //private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private SqLiteDbHelper sqLiteDbHelper;
    private SQLiteDatabase sqLiteDatabase;


    private int imageHeight = 720;
    private int imageWidth = 480;


    public NewsRvAdapter() {
        firebaseAuth = FirebaseAuth.getInstance();
        articles = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDbHelper = new FirebaseDbHelper();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        DatabaseManager.getInstance().closeDatabase();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Resources r = recyclerView.getResources();

        imageWidth = r.getDisplayMetrics().widthPixels;
        imageHeight = (int)(imageWidth/1.77);
    }

    @Override
    public NewsRvAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        sqLiteDbHelper = new SqLiteDbHelper(parent.getContext());
        sqLiteDatabase = DatabaseManager.getInstance().openDatabase();
        return new ArticleViewHolder(inflater.inflate(R.layout.article_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        if (articles != null) {
            holder.bindArticle(articles.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public boolean swapData(ArrayList<News.Article> newDataList) {
        if (newDataList != null && newDataList.size() > 0) {
            this.articles.clear();
            this.articles.addAll(newDataList);
            this.notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    public boolean addArticles(ArrayList<News.Article> newArticles) {
        if (newArticles.size() > 0) {
            articles.addAll(newArticles);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvArticleTitle;
        private ImageView ivArticleImage;
        private TextView tvArticleDesc;
        private ImageView ivLike;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            tvArticleTitle = (TextView) itemView.findViewById(R.id.tv_article_title);
            tvArticleDesc = (TextView) itemView.findViewById(R.id.tv_article_desc);
            ivArticleImage = (ImageView) itemView.findViewById(R.id.iv_article_image);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);

        }

        public void bindArticle(final News.Article article) {

            tvArticleTitle.setText(article.getTitle());
            tvArticleDesc.setText(article.getDescription());

            Glide.with(itemView.getContext()).load(article.getUrlToImage()).dontAnimate()
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .override(imageWidth, imageHeight).centerCrop().into(ivArticleImage);

            if (!article.isLiked()) {
                ivLike.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            } else {
                ivLike.setImageResource(R.drawable.ic_favorite_black_24dp);
            }

            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (firebaseAuth.getCurrentUser()!=null) {
                        if (!article.isLiked()) {
                            databaseReference = firebaseDatabase.getReference("news");
                            databaseReference.child(article.getArticleId()).setValue(article.toMap());

                            databaseReference = firebaseDatabase.getReference("users");
                            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("liked-news")
                                    .child(article.getArticleId()).setValue("true");

                            article.setLiked(true);
                            sqLiteDbHelper.addLikedNews(sqLiteDatabase, article.getArticleId());
                            notifyItemChanged(getAdapterPosition());

                        } else {

                            article.setLiked(false);
                            databaseReference = FirebaseDatabase.getInstance()
                                    .getReference("users/" + firebaseAuth.getCurrentUser().getUid() + "/liked-news");
                            databaseReference.child(article.getArticleId()).removeValue();
                            sqLiteDbHelper.removeLikedNews(sqLiteDatabase, article.getArticleId());
                            notifyItemChanged(getAdapterPosition());

                        }
                    }
                }
            });

            tvArticleTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), WebViewActivity.class);
                    intent.putExtra(WebViewActivity.KEY_NEWS_URL, article.getUrl());
                    itemView.getContext().startActivity(intent);
                }
            });
        }

    }

}
