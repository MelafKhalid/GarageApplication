package com.example.project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.DBHelper;
import com.example.project.LoginActivity;
import com.example.project.R;

public class CustomerSignUp extends AppCompatActivity implements View.OnClickListener {
    DBHelper db = new DBHelper(this);

    SQLiteDatabase database;
    EditText name, username, password, phone, address;
    Button createAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_signup);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);
        address = (EditText) findViewById(R.id.email);

        createAccountBtn = (Button) findViewById(R.id.createAccountBtn);
        createAccountBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.createAccountBtn) {
            database = db.getWritableDatabase();
            database.beginTransaction();

            try {
                //database.execSQL("INSERT INTO user(username,password,userType) VALUES('" + username.getText() + "','" + password.getText() + "','" + "customer" + "')");
                database.execSQL("INSERT INTO customer(name, username, password, phone, email) VALUES('" + name.getText() + "','" + username.getText() + "','" + password.getText() + "','" + phone.getText() + "','" + address.getText() + "')");
                database.setTransactionSuccessful();
                Toast.makeText(this, " لقد تم تسجيل حسابك بنجاح", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CustomerSignUp.this, LoginActivity.class);
                startActivity(intent);
            } finally {
                Toast.makeText(this, " failed", Toast.LENGTH_LONG).show();
                database.endTransaction();
            }

        }
    }
}