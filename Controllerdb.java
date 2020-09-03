package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Controllerdb extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="data2.db";

    public Controllerdb(Context applicationcontext){
        super(applicationcontext, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        query= "CREATE TABLE IF NOT EXISTS customer(userid INTEGER  PRIMARY KEY AUTOINCREMENT, name VARCHAR,username VARCHAR,password VARCHAR,phone NUMBER, email VARCHAR );";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query;
        query = "DROP TABLE IF EXISTS customer";
        db.execSQL(query);
        onCreate(db);
    }
}