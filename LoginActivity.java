package com.example.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    Button btnLogin, signupBtn;
    EditText edtUsernme, edtPassword;
    DBHelper databaseHelper;
    // DBHelper db;
    boolean isExist;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.loginBtn);
        signupBtn = (Button) findViewById(R.id.signupBtn);
        edtUsernme = (EditText) findViewById(R.id.username);
        edtPassword = (EditText) findViewById(R.id.pass);

        databaseHelper = new DBHelper(LoginActivity.this);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("جاري تسجيل الدخول...");
        mProgress.setMessage("فضلًا انتظر...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        mProgress.hide();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.show();
                isExist = databaseHelper.checkUserExist(edtUsernme.getText().toString(), edtPassword.getText().toString());
                if (isExist) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", edtUsernme.getText().toString());
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", edtUsernme.getText().toString());
                    editor.commit();
                    startActivity(intent);
                } else if (edtUsernme.getText().toString().equals("admin") && edtPassword.getText().toString().equals("123")) {
                    startActivity(new Intent(LoginActivity.this, AdminHome.class));
                } else {
                    mProgress.hide();
                    edtUsernme.setError("فشل تسجيل الدخول. اسم المستخدم أو كلمة المرور خاطئة!");
                }
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CustomerSignUp.class));
            }
        });

    }//end of onCreate()

}//end of class
