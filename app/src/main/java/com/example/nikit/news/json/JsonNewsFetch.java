package com.example.nikit.news.json;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nikit.news.entities.NewsEntity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nikit on 12.03.2017.
 */

public class JsonNewsFetch {
    private String jsonString;


    public void loadJsonString(String urlAddress){
        new LoadJsonAsyncTask().execute(urlAddress);
    }

    public NewsEntity getNews(){
        NewsEntity news = new NewsEntity();
        if(jsonString!=null){
            Gson gson = new Gson();
            news =  gson.fromJson(jsonString, NewsEntity.class);
        }
        return news;
    }


    private class LoadJsonAsyncTask extends AsyncTask<String, Void, Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            NewsEntity news = getNews();
           Log.d("jsonString", news.toString());
        }

        @Override
        protected Void doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                StringBuffer buffer = new StringBuffer();
                String line;

                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }
                jsonString = buffer.toString();
                Log.d("jsonString", jsonString);

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }
    }
}
