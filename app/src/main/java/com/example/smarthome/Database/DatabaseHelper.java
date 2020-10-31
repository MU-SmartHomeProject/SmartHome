package com.example.smarthome.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smartHomeManager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_TAG = "SQL_ERROR";

    //String to create a customer table
    private static final String CREATE_DEVICE_TABLE =
            "CREATE TABLE " + Constants.DEVICE_TABLE + "("
                    + Constants.DEVICE_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + Constants.USER_NAME          + " TEXT, "
                    + Constants.DEVICE_NAME        + " TEXT, "
                    + Constants.DEVICE_TYPE        + " TEXT, "
                    + Constants.DEVICE_STATUS      + " INTEGER, "
                    + Constants.DEVICE_SEEK_BAR1   + " INTEGER, "
                    + Constants.DEVICE_SEEK_BAR2   + " INTEGER "+ ")";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try{
            // we run the query to create the database
            sqLiteDatabase.execSQL(CREATE_DEVICE_TABLE);
        }catch (Exception e){
            Log.d(SQL_TAG, e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
