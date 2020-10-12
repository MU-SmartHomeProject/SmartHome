package com.example.smarthome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserDB.db";
    public static final String USERS_TABLE_NAME = "users_table";
    public static final String USERS_COLUMN_ID = "ID";
    public static final String USERS_COLUMN_USERNAME = "USERNAME";
    public static final String USERS_COLUMN_EMAIL = "EMAIL";
    public static final String USERS_COLUMN_PASSWORD = "PASSWORD";

    public UserDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + USERS_TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, USERNAME TEXT, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldDB, int newDB) {
        database.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        onCreate(database);
    }

    public boolean insertData(String username, String email, String password) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_USERNAME, username);
        contentValues.put(USERS_COLUMN_EMAIL, email);
        contentValues.put(USERS_COLUMN_PASSWORD, password);

        long result = database.insert(USERS_TABLE_NAME, null, contentValues);

        if(result == -1)
            return false; //Data not inserted
        else
            return true; //Data inserted
    }

    public boolean updateData(int id, String username, String email, String password) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_ID, id);
        contentValues.put(USERS_COLUMN_USERNAME, username);
        contentValues.put(USERS_COLUMN_EMAIL, email);
        contentValues.put(USERS_COLUMN_PASSWORD, password);

        database.update(USERS_TABLE_NAME, contentValues, "ID = ?", new String[]{Integer.toString(id)});

        return true;
    }

    public int deleteData(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        //Returns number of deleted rows
        return database.delete(USERS_TABLE_NAME, "ID = ?", new String[]{Integer.toString(id)});
    }

    public Cursor getUser(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor result = database.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_COLUMN_ID + " = '" + Integer.toString(id) + "'", null);
        return result;
    }
}
