package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


public class saleactivity extends AppCompatActivity {
    TextView usernameact;
    TextView productnameact;
    TextView salepriceact;
    TextView saledateact;
    TextView totalprice;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale);
        usernameact = findViewById(R.id.username);
        productnameact = findViewById(R.id.productname);
        salepriceact = findViewById(R.id.saleprice);
        saledateact = findViewById(R.id.saledate);
        totalprice = findViewById(R.id.totalprice);

        //read user name
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = sharedPreferences.getString("username", "default");

        String name = "";
        String price = "";
        String date = "";
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            name = intent.getStringExtra("name");
            price = intent.getStringExtra("price");
            date = intent.getStringExtra("date");

            //date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        }

        usernameact.setText(username);
        productnameact.setText(name);
        salepriceact.setText(price + "SR");
        totalprice.setText(price + "SR");
        saledateact.setText(date);



    }
    public void setToolbar() {
        toolbar = findViewById(R.id.cart_toolbar);
        toolbar.setTitle("الفاتورة");
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
}
