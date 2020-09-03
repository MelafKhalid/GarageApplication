package com.example.project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddToCart extends AppCompatActivity {

    ArrayList<Cart> shopping_list;
    DBHelper db;
    ListView grid;
    Button bill;
    String name, price;
    public int userid;
    Toolbar toolbar;

    CartListAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        shopping_list = new ArrayList<>();
        grid = (ListView) findViewById(R.id.grid);
        db = new DBHelper(this);

        Adapter = new CartListAdapter(this, R.layout.cart_item, shopping_list);

        setToolbar();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            name = intent.getStringExtra("name");
            price = intent.getStringExtra("price");
            byte[] pic = intent.getExtras().getByteArray("pic");
            shopping_list.add(new Cart(name, price, pic));
            grid.setAdapter(Adapter);

            userid = db.GetUserID(name);
            Toast.makeText(AddToCart.this, " user id:"+userid, Toast.LENGTH_LONG).show();

            //Adapter.notifyDataSetChanged();


        }//end of id
        bill = (Button) findViewById(R.id.bill);
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddToCart.this, saleactivity.class);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                String date;
                date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                intent.putExtra("date", date);
                SQLiteDatabase database = db.getWritableDatabase();

                database.beginTransaction();

                try {
                    database.execSQL("INSERT INTO sale(user_id, username, price, sale_date) VALUES('" + userid + "','" + name + "','" + price + "','" + date + "')");
                    Toast.makeText(AddToCart.this, " added", Toast.LENGTH_LONG).show();
                    database.setTransactionSuccessful();
                } finally {
                    Toast.makeText(AddToCart.this, " failed", Toast.LENGTH_LONG).show();
                    database.endTransaction();
                }
                startActivity(intent);
            }
        });

    }


    public void setToolbar() {
        toolbar = findViewById(R.id.cart_toolbar);
        toolbar.setTitle("السلة");
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