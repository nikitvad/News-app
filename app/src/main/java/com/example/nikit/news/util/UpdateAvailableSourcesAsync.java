package com.example.nikit.news.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.example.nikit.news.api.ApiClient;
import com.example.nikit.news.api.SourcesResponse;
import com.example.nikit.news.database.DatabaseManager;
import com.example.nikit.news.database.SqLiteDbHelper;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by nikit on 08.04.2017.
 */

public class UpdateAvailableSourcesAsync extends AsyncTask<Void, Void, Void> {

    SQLiteDatabase db;
    SqLiteDbHelper dbHelper;
    private OnUpdateStageListener mListener;
    private boolean updateSuccess;

    public UpdateAvailableSourcesAsync(Context context, @Nullable OnUpdateStageListener listener) {
        dbHelper = new SqLiteDbHelper(context);
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        db = DatabaseManager.getInstance().openDatabase();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mListener != null) {
            if (updateSuccess) {
                mListener.onUpdateSuccess();
            } else {
                mListener.onUpdateFail();
            }
        }
        DatabaseManager.getInstance().closeDatabase();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Response<SourcesResponse> response = ApiClient.getInstance().getSources(null, null, null).execute();
            if (response.isSuccessful() && response.body() != null) {
                dbHelper.insertSource(db, response.body().getSources());
                updateSuccess = true;
            } else {
                updateSuccess = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface OnUpdateStageListener {
        void onUpdateSuccess();

        void onUpdateFail();
    }
}

