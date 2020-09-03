package com.example.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewAllCustomersInfo extends AppCompatActivity {

    DBHelper DatabaseController = new DBHelper(this);
    SQLiteDatabase db;

    private ArrayList<String> phone = new ArrayList<String>();
    private ArrayList<String> name =new ArrayList<String>();
    private ArrayList<String> email =new ArrayList<String>();

    ListView customerslistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_customers_info);
        customerslistview = (ListView) findViewById(R.id.list);
    }

    @Override
    protected void onResume() {
        displayData();
        super.onResume();

    }

    private void displayData (){
        db = DatabaseController.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from customer",null);
        name.clear();
        phone.clear();
        email.clear();
        if (cursor.moveToFirst()) {// if the cursor points to something

            do {
                name.add(cursor.getString(cursor.getColumnIndex("name")));
                email.add(cursor.getString(cursor.getColumnIndex("email")));
                phone.add(cursor.getString(cursor.getColumnIndex("phone")));

            }while ( cursor.moveToNext() ); // the loop will keep going to next until pointing to null

        }
        CustomAdapter c = new CustomAdapter(ViewAllCustomersInfo.this,name,email,phone);
        customerslistview.setAdapter(c);
        cursor.close();

    }
}