package com.example.nikit.news.firebase.models;

import java.util.ArrayList;

/**
 * Created by nikit on 28.03.2017.
 */

public class User {
    private String uId;
    private ArrayList<LikedNews> likedNewses;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public ArrayList<LikedNews> getLikedNewses() {
        return likedNewses;
    }

    public void setLikedNewses(ArrayList<LikedNews> likedNewses) {
        this.likedNewses = likedNewses;
    }
}
