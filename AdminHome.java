package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHome extends AppCompatActivity {
    TextView helloUser;
    Button salesBtn, customerBtn, AdminLogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        Bundle extras = getIntent().getExtras();

        salesBtn = (Button) findViewById(R.id.admin_salesBtn);
        customerBtn = (Button) findViewById(R.id.admin_customerBtn);
        AdminLogoutBtn = (Button) findViewById(R.id.Admin_logoutBtn);

        String username = null;
        if (extras != null) {
            username = extras.getString("username");
            helloUser.setText(" .." + username + "مرحبا بالمشرف ");
        }
        salesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllSales();
            }
        });
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllCustomer();
            }
        });
        AdminLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, SplashActivity.class));
            }
        });

    }


    public void viewAllCustomer() {
        Intent intent = new Intent(AdminHome.this, ViewAllCustomersInfo.class);
        startActivity(intent);

    }

    public void viewAllSales() {
        Intent intent = new Intent(AdminHome.this, ViewAllSales.class);
        startActivity(intent);

    }
}//end of class