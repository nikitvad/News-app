package com.example.nikit.news.ui.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikit.news.R;
import com.example.nikit.news.database.DatabaseManager;
import com.example.nikit.news.database.SqLiteDbHelper;
import com.example.nikit.news.entities.News;
import com.example.nikit.news.firebase.FirebaseDbHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nikit on 14.03.2017.
 */

public class NewsRvAdapter extends RecyclerView.Adapter<NewsRvAdapter.ArticleViewHolder> {
    private final ArrayList<News.Article> articles;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    FirebaseDbHelper firebaseDbHelper;

    private SqLiteDbHelper sqLiteDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public NewsRvAdapter() {
        articles = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDbHelper = new FirebaseDbHelper();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        DatabaseManager.getInstance().closeDatabase();
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
            int startPos = getItemCount();
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
            Picasso.with(itemView.getContext()).load(article.getUrlToImage()).into(ivArticleImage);

            if (article.isLikedCurrentUser()) {
                ivLike.setImageResource(R.drawable.like_active);
                Log.d("isLikedNewsContain", "contain" + article.getArticleId());
            } else {
                ivLike.setImageResource(R.drawable.like_not_active);
            }

            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (firebaseUser.getUid() != null) {
                        if (!article.isLikedCurrentUser()) {

                            article.setLikedCurrentUser(true);
                            firebaseDbHelper.pushLikedNews(article);
                            sqLiteDbHelper.addLikedNews(sqLiteDatabase, article.getArticleId());
                            notifyItemChanged(getAdapterPosition());

                        } else {

                            article.setLikedCurrentUser(false);
                            firebaseDbHelper.removeLikedNews(article.getArticleId());
                            sqLiteDbHelper.removeLikedNews(sqLiteDatabase, article.getArticleId());
                            notifyItemChanged(getAdapterPosition());

                        }
                    }
                }
            });
        }

    }

}
