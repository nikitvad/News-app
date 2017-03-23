package com.example.nikit.news.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.nikit.news.entities.Source;
import com.example.nikit.news.database.DataBaseContract.SourceTable;
import com.example.nikit.news.util.Util;

import java.util.List;

/**
 * Created by nikit on 13.01.2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context, DataBaseContract.DB_NAME, null, DataBaseContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseContract.SourceTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion != newVersion ){
            db.execSQL(DataBaseContract.SourceTable.DELETE_TABLE);
            db.execSQL(DataBaseContract.SourceTable.CREATE_TABLE);

            Log.d("database", "database upgraded");

        }

    }

    private ContentValues getContentValuesFormSource(Source source){
        ContentValues values = new ContentValues();
        values.put(SourceTable.COLUMN_NAME_SOURCE_ID, source.getId());
        values.put(SourceTable.COLUMN_NAME_NAME, source.getName());
        values.put(SourceTable.COLUMN_NAME_DESCRIPTION, source.getDescription());
        values.put(SourceTable.COLUMN_NAME_LANGUAGE, source.getLanguage());
        values.put(SourceTable.COLUMN_NAME_COUNTRY, source.getCountry());
        values.put(SourceTable.COLUMN_NAME_CATEGORY, source.getCategory());
        values.put(SourceTable.COLUMN_NAME_SORT_BY_AVAILABLE, Util.getStringFromArrayOfString(source.getSortBysAvailable()));
        values.put(SourceTable.COLUMN_NAME_URL, source.getUrl());
        values.put(SourceTable.COLUMN_NAME_URL_LOGOS_SMALL, source.getUrlsToLogos().getSmall());
        return values;

    }

    public void insertSource(SQLiteDatabase db, Source source){

        ContentValues values = getContentValuesFormSource(source);
        db.insert(SourceTable.TABLE_NAME, null, values);
    }

    public void insertSource(SQLiteDatabase db, List<Source> sourceList){
        ContentValues contentValues;
        for(Source source: sourceList){
            contentValues=getContentValuesFormSource(source);
            db.insert(SourceTable.TABLE_NAME, null, contentValues);
        }
    }

    private Source getSourceFromCursor(Cursor cursor){
        Source source = new Source();

        source.setId(cursor.getString(cursor.getColumnIndex(SourceTable.COLUMN_NAME_SOURCE_ID)));
        source.setName(cursor.getString(cursor.getColumnIndex(SourceTable.COLUMN_NAME_NAME)));
        source.setDescription(cursor.getString(cursor.getColumnIndex(SourceTable.COLUMN_NAME_DESCRIPTION)));
        source.setCategory(cursor.getString(cursor.getColumnIndex(SourceTable.COLUMN_NAME_CATEGORY)));
        source.setCountry(cursor.getString(cursor.getColumnIndex(SourceTable.COLUMN_NAME_COUNTRY)));
        source.setLanguage(cursor.getString(cursor.getColumnIndex(SourceTable.COLUMN_NAME_LANGUAGE)));
        source.setUrl(cursor.getString(cursor.getColumnIndex(SourceTable.COLUMN_NAME_URL)));

        Source.UrlToLogos urlToLogos = new Source.UrlToLogos();
        urlToLogos.setMedium(cursor.getString(cursor.getColumnIndex(SourceTable.COLUMN_NAME_URL_LOGOS_SMALL)));
        source.setUrlsToLogos(urlToLogos);

        String[] sortByAvail = Util.getListOfStringsFromString(cursor.getString(
                    cursor.getColumnIndex(SourceTable.COLUMN_NAME_SORT_BY_AVAILABLE)));

        source.setSortBysAvailable(sortByAvail);

        return source;
    }

    public void loadSourceList(SQLiteDatabase db, List<Source> sourceList){
        Cursor cursor = db.query(DataBaseContract.SourceTable.TABLE_NAME,
                DataBaseContract.SourceTable.ARRAY_OF_COLUMN_NAMES,
                null, null, null, null, null);

        if(cursor.moveToFirst()){

            do {
                Source source = getSourceFromCursor(cursor);

                sourceList.add(source);

            }while(cursor.moveToNext());

            cursor.close();

        }

    }

    public Source getWeatherById(SQLiteDatabase db, String sourceId){
        Source source;

        Cursor cursor = db.query(SourceTable.TABLE_NAME, SourceTable.ARRAY_OF_COLUMN_NAMES,
                SourceTable.COLUMN_NAME_SOURCE_ID + "=?",
                new String[]{sourceId},
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            source = getSourceFromCursor(cursor);
            cursor.close();
            return source;

        }else{
            cursor.close();
            return null;
        }
    }

    public void clearWeatherTable(SQLiteDatabase db){
        db.delete(SourceTable.TABLE_NAME, null, null);
    }
}
