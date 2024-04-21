package com.example.dictionaryapp;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelperVA extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "viet_anh.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelperVA(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
