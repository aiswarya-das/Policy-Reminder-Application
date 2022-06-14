package com.example.reminderapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
public class dbManager extends SQLiteOpenHelper {
    private static String dbname = "reminder";                                                      //Table  name to store reminders in sqllite
    private static final String TABLE_Users = "tbl_reminder";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    public dbManager(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_TIME + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS tbl_reminder";                                         //sql query to check table with the same name or not
        sqLiteDatabase.execSQL(query);                                                              //executes the sql command
        onCreate(sqLiteDatabase);
    }

    public String addreminder(String title, String date, String time) {
        SQLiteDatabase database = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, title);                                                          //Inserts  data into sqllite database
        contentValues.put(KEY_DATE, date);
        contentValues.put(KEY_TIME, time);

        float result = database.insert("tbl_reminder", null, contentValues);    //returns -1 if data successfully inserts into database

        if (result == -1) {
            return "Failed";
        } else {
            return "Successfully inserted";
        }
    }
    public Cursor readallreminders() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "select * from tbl_reminder order by id desc";                               //Sql query to  retrieve  data from the database
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }
    public void deleteTask(String name){
        SQLiteDatabase database = this.getWritableDatabase();
        Log.e("delete","in deleteTask");
       database.delete("tbl_reminder", KEY_TITLE + "=?", new String[]{name}) ;
    }
}