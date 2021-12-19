package com.example.institutemanagementapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class studentlogin extends AppCompatActivity {

    String sSID,college,sEmail,batch;

    DatabaseReference ref;
    DatabaseReference dbStudent;
    private static long back_pressed;
    Bundle bundle;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);
        
        TextView textView=findViewById(R.id.textView1);

        bundle=getIntent().getExtras();
        college=bundle.getString("College");
        sSID=bundle.getString("sSid");
        sEmail=bundle.getString("sEmail");

        ref=FirebaseDatabase.getInstance().getReference(college);
        ref.child("Student").child(sSID).child("classes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            batch=snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        textView.setText("Welcome");
    }

    public void viewAttendance(View view){
        Bundle basket=new Bundle();
        basket.putString("College",college);
        basket.putString("sSid",sSID);
        basket.putString("Batch",batch);
        Intent intent=new Intent(this,student_attendanceSheet.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void logoutStudent(View view){
        Intent logout=new Intent(this,LoginActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logout);
    }
    public void onBackPressed(){
        if(back_pressed + 2000>System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
        else {
            Toast.makeText(this, "Press Once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed=System.currentTimeMillis();
        }
    }
}