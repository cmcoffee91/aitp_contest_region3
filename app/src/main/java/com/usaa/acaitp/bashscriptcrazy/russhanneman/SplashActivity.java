package com.usaa.acaitp.bashscriptcrazy.russhanneman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by christophercoffee on 10/20/16.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}