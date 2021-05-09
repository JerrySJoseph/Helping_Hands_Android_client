package com.jstechnologies.helpinghands.ui.views.splashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jstechnologies.helpinghands.R;
import com.jstechnologies.helpinghands.ui.views.dashBoard.DashBoardActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(SplashScreenActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}