package com.example.dictionaryapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessVA {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccessVA instance;
    private DatabaseAccessVA(Context context) {
        this.openHelper = new DatabaseOpenHelperVA(context);
    }
    public static DatabaseAccessVA getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccessVA(context);
        }
        return instance;
    }
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }
    public List<String> getWords() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM viet_anh", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public ArrayList<String> getWords(String filter) {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM viet_anh where word like '"+ filter +"%' limit 10", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public String getDefinition(String word) {
        String definition = "";
        Cursor cursor = database.rawQuery("SELECT * FROM anh_viet where word='"+ word +"'", null);
        cursor.moveToFirst();

        definition  = cursor.getString(2);

        cursor.close();
        return definition;
    }
}
