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

    /**
     * connect to SQLiteDatabase
     * @param context
     */
    public UserDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    /**
     * Create user database
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + USERS_TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT UNIQUE, USERNAME TEXT UNIQUE, PASSWORD TEXT)");
    }

    /**
     * handle upgrade database
     * drop tables if already exist
     * calls onCreate methods
      * @param database
     * @param oldDB
     * @param newDB
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldDB, int newDB) {
        database.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        onCreate(database);
    }

    /**
     * insert a user
     * @param username
     * @param email
     * @param password
     * @return false if data not inserted
     * @return true if data inserted
     */
    public boolean insertData(String username, String email, String password) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_USERNAME, username);
        contentValues.put(USERS_COLUMN_EMAIL, email);
        contentValues.put(USERS_COLUMN_PASSWORD, password);

        long result = database.insert(USERS_TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     * update a users details
     * @param id
     * @param username
     * @param email
     * @param password
     * @return true
     */
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

    /**
     * check for a username in the db
     * @param username
     * @return cursor
     */
    public Cursor checkUsername(String username) {
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_COLUMN_USERNAME + " = '" + username + "'", null);
        return cursor;
    }

    /**
     * check for a password in the db
     * @param password
     * @return cursor
     */
    public Cursor checkPassword(String password) {
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_COLUMN_PASSWORD + " = '" + password + "'", null);
        return cursor;
    }

    /**
     * check for an email in the db
     * @param email
     * @return cursor
     */
    public Cursor checkEmail(String email) {
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_COLUMN_EMAIL + " = '" + email + "'", null);
        return cursor;
    }

    /**
     * remove a user from the db
     * @param id
     * @return number of deleted rows
     */
    public int deleteData(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(USERS_TABLE_NAME, "ID = ?", new String[]{Integer.toString(id)});
    }

    /**
     * return all details about a user based on their id
     * @param id
     * @return result
     */
    public Cursor getUser(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor result = database.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_COLUMN_ID + " = '" + Integer.toString(id) + "'", null);
        return result;
    }
}
