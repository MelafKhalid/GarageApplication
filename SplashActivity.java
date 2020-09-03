package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout splashLayout;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        splashLayout = (ConstraintLayout) findViewById(R.id.splashLL);
        splashLayout.setOnClickListener(this);

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                } catch (InterruptedException e) {
                }//end of catch
            }//end of run method
        };
        thread.start();//starting the thread timer
    }//end of on create method


    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }//end of onClick()

}//end of class