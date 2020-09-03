package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SalesTableDatabaseController  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="data2.db";

    public SalesTableDatabaseController(Context applicationcontext){
        super(applicationcontext, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        query="CREATE TABLE IF NOT EXISTS sale(sale_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, username VARCHAR, price DOUBLE, sale_date VARCHAR);";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query;
        query = "DROP TABLE IF EXISTS sale";
        db.execSQL(query);
        onCreate(db);
    }
}