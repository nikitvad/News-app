package com.example.nikit.news.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nikit.news.entities.NewsEntity;
import com.example.nikit.news.entities.Source;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nikit on 12.03.2017.
 */

public class JsonUtil {
    //private static String jsonString;


    public static String loadJsonString(String urlAddress){
        //new LoadJsonAsyncTask().execute(urlAddress);
        String jsonStringResult = "";

        try {
            URL url = new URL(urlAddress);
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
            jsonStringResult = buffer.toString();
            Log.d("jsonString", jsonStringResult);

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return jsonStringResult;
    }


    public static ArrayList<Source> getSourcesFromJson(String jsonString){
        String sourcesJsonString;
        ArrayList<Source> sourcesResult;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            sourcesJsonString = jsonObject.getJSONArray("sources").toString();
            if(sourcesJsonString!=null && sourcesJsonString.length()>0){
                Gson gson = new Gson();
                Type ArrayListSourceType = new TypeToken<ArrayList<Source>>(){}.getType();
                sourcesResult = gson.fromJson(sourcesJsonString, ArrayListSourceType);

                Log.d("jsonString", sourcesResult.size()+"");
                for(Source source: sourcesResult){
                    Log.d("jsonString", source.toString());
                }


                if(sourcesResult.size()>0){
                    return sourcesResult;
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;

    }

    public static NewsEntity getNewsFromJsonString(String jsonString){
        NewsEntity news = new NewsEntity();
        if(jsonString!=null) {
            Gson gson = new Gson();
            news = gson.fromJson(jsonString, NewsEntity.class);
            return news;
        }else return null;
    }


    /*
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
    */
}
