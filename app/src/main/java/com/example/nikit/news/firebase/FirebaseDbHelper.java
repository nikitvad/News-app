package com.example.nikit.news.firebase;

import android.util.Log;

import com.example.nikit.news.entities.NewsEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by nikit on 28.03.2017.
 */

public class FirebaseDbHelper {
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    public FirebaseDbHelper(){
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
/*
        reference = database.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.setValue("Hello world");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("Firebase", "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });
        */
    }


    public boolean pushLikedNews(NewsEntity.Article article){
        HashMap<String, Object> hashMap = new HashMap<>();
        String newsId = article.getUrl().replaceAll("[\\.#\\$\\[\\]/]" ,"");
        DatabaseReference reference = database.getReference("liked-newses")
                .child(newsId);

        hashMap.put("title", article.getTitle());
        hashMap.put("description", article.getDescription());
        hashMap.put("url", article.getUrl());
        hashMap.put("urlToImage", article.getUrlToImage());
        hashMap.put("publishedAt", article.getPublishedAt());
        reference.setValue(hashMap);
        reference.child("users").child(auth.getCurrentUser().getUid()).setValue("true");
        return true;
    }
}
