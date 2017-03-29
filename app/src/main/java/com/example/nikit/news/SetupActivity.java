package com.example.nikit.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.nikit.news.ui.activities.LoginActivity;
import com.example.nikit.news.ui.activities.LoginActivityV2;

/**
 * Created by nikit on 26.03.2017.
 */

public class SetupActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_login:{
                Intent intent = new Intent(getApplicationContext(), LoginActivityV2.class);
                startActivity(intent);
            }
        }
        return false;
    }
}
