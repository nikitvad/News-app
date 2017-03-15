package com.example.nikit.news.util;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by nikit on 15.03.2017.
 */

public class ViewUtil {

    public static void hideToolbar(Toolbar toolbar) {
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    public static void hideToolbar(int height, View  view) {
            view.animate().translationY(-height).setInterpolator(new AccelerateInterpolator(2)).start();
    }
    public static void showToolbar(View view) {
            view.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public static void hideToolbar(int height, View ... view) {
        for(View viewItem: view ){
            viewItem.animate().translationY(-height).setInterpolator(new AccelerateInterpolator(2)).start();
        }
        //toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }


    public static void showToolbar(View ... view) {
        for(View viewItem: view ){
            viewItem.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        }
        //toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    public static void showToolbar(Toolbar toolbar) {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }
}
