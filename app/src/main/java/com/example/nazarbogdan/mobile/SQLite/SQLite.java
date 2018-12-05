package com.example.nazarbogdan.mobile.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {

    public  SQLite(Context c){
        super(c, "Favourites", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table news( id INTEGER PRIMARY KEY AUTOINCREMENT, title text, " +
                "description text, author text, image_path text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists markers");
    }

    public boolean insert(String title,  String description, String author, String image_path) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("author", author);
        contentValues.put("image_path", image_path);
        long res = db.insert("news", null, contentValues);
        return true;
    }

    public boolean delete(String imageURL) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = String.format("delete from news where image_path = '%s'", imageURL);
        db.execSQL(sql);
        return true;
    }

    public Cursor queueAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select  title,  description,author ,\n" +
                "image_path from news", null);
    }
}
