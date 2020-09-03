package com.example.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewAllSales extends AppCompatActivity {

    DBHelper DatabaseController = new DBHelper(this);
    SQLiteDatabase db;

    private ArrayList<String> user_id = new ArrayList<String>();
    private ArrayList<String> username =new ArrayList<String>();
    private ArrayList<String> price =new ArrayList<String>();

    ListView customerslistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sales);
        customerslistview = (ListView) findViewById(R.id.list);
    }

    @Override
    protected void onResume() {
        displayData();
        super.onResume();

    }

    private void displayData (){
        db = DatabaseController.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from sale",null);
        user_id.clear();
        username.clear();
        price.clear();
        if (cursor.moveToFirst()) {// if the cursor points to something

            do {
                user_id.add(cursor.getString(cursor.getColumnIndex("user_id")));
                username.add(cursor.getString(cursor.getColumnIndex("username")));
                price.add(cursor.getString(cursor.getColumnIndex("price")));
            }while ( cursor.moveToNext() ); // the loop will keep going to next until pointing to null

        }
        CustomAdapter2 c = new CustomAdapter2(ViewAllSales.this,user_id,username,price);
        customerslistview.setAdapter(c);
        cursor.close();

    }
}