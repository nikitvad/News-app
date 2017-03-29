package com.example.nikit.news.database;

import android.provider.BaseColumns;

/**
 * Created by nikit on 13.01.2017.
 */

public final class DataBaseContract {

    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "news_db.db";
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String REAL_TYPE = " REAL";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String NUMERIC_TYPE = " BIGINT";


    public DataBaseContract(){
    }


    public static abstract class SourceTable implements BaseColumns {

        public static final String TABLE_NAME = "SOURCES";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_CATEGORY ="category";
        public static final String COLUMN_NAME_LANGUAGE = "language";
        public static final String COLUMN_NAME_COUNTRY = "country";
        public static final String COLUMN_NAME_URL_LOGOS_SMALL = "url_logos_small";
        public static final String COLUMN_NAME_URL_LOGOS_MEDIUM = "url_logos_medium";
        public static final String COLUMN_NAME_URL_LOGOS_LARGE = "url_logos_large";
        public static final String COLUMN_NAME_SORT_BY_AVAILABLE = "sort_by_available";

        public static final String[] ARRAY_OF_COLUMN_NAMES = {_ID, COLUMN_NAME_NAME,
                                                                COLUMN_NAME_DESCRIPTION, COLUMN_NAME_URL,
                                                                COLUMN_NAME_CATEGORY, COLUMN_NAME_LANGUAGE,
                                                                COLUMN_NAME_COUNTRY, COLUMN_NAME_URL_LOGOS_SMALL,
                                                                COLUMN_NAME_URL_LOGOS_MEDIUM, COLUMN_NAME_URL_LOGOS_LARGE,
                                                                COLUMN_NAME_SORT_BY_AVAILABLE};


        public static final String DELETE_TABLE = "DROP TABLE " + TABLE_NAME;
        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " ("+
                _ID + " TEXT PRIMARY KEY, " +
                COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_LANGUAGE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_URL_LOGOS_SMALL + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_URL_LOGOS_MEDIUM + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_URL_LOGOS_LARGE + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SORT_BY_AVAILABLE + TEXT_TYPE +" )";
    }
}
