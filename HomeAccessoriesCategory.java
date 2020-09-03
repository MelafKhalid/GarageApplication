package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridView;

import java.util.ArrayList;

public class HomeAccessoriesCategory extends AppCompatActivity {
    Toolbar toolbar;
    GridView gridView;
    ArrayList<Product> list;
    ProductListAdapter adapter = null;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        setToolbar();

        gridView = (GridView) findViewById(R.id.gridViewBook);
        list = new ArrayList<>();
        adapter = new ProductListAdapter(this, R.layout.product_item, list);
        gridView.setAdapter(adapter);

        try {
            //get data from sqlite
            Cursor cursor = db.getAllProduct("home_accessories");
            //list.clear();

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                String price = cursor.getString(3);
                byte[] image = cursor.getBlob(4);
                String categoryName = cursor.getString(5);

                list.add(new Product(id, name, desc, price, image, categoryName));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();


    }//end of onCreate()


    public void setToolbar() {
        toolbar = findViewById(R.id.book_category_toolbar);
        toolbar.setTitle("قسم إكسسوارات المنزل");
        setSupportActionBar(toolbar);

        //setting back button
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//setting the tool bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);//making it showing

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//going automatically to the previous page
            }
        });
    }
}//end of class
