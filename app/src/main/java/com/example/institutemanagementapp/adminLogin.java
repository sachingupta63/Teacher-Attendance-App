package com.example.institutemanagementapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Models.Attendance_sheet;

public class adminLogin extends AppCompatActivity {

    String college_name,aUid;
    Bundle basket;
    private static long back_pressed;

   // ArrayList Studentlist=new ArrayList<>();
    String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        Bundle bundle=getIntent().getExtras();
        college_name=bundle.getString("College","Wrong Value");
        aUid=bundle.getString("aUid","Wrong value");


        basket=new Bundle();
        basket.putString("College",college_name);

        /*ref= FirebaseDatabase.getInstance().getReference(college_name);
        dbStudent=ref.child("Student");
        dbAttendance=ref.child("Attendance");*/

    }

    public void AddTeacherButton(View view){
        Intent intent=new Intent(this,addTeacher.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void AddStudentButton(View view){
        Intent intent=new Intent(this, addStudent.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void attendanceRecord(View view){
        Intent intent=new Intent(this,admin_attendanceSheet.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void newBatch(View view){
        Intent intent=new Intent(this,newBatch.class);
        intent.putExtras(basket);
        startActivity(intent);
    }



    public void logout(View view){
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