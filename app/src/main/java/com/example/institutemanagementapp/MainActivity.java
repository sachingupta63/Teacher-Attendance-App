package com.example.institutemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Thread undergo sleep
        // for 2sec
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent=new Intent(MainActivity.this, StartActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();

                }
            }, 2000);
        } catch (Exception e) {
            Toast.makeText(this, "Splash not working", Toast.LENGTH_SHORT).show();
        }
    }
}