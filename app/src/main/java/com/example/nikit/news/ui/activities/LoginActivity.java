package com.example.nikit.news.ui.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.nikit.news.R;
import com.example.nikit.news.ui.fragments.login.LoginMethodFragment;

public class LoginActivity extends AppCompatActivity implements LoginMethodFragment.OnFragmentInteractionListener {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new LoginMethodFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
