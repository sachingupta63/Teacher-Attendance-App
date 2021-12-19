package com.example.institutemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.List;

public class teacherlogin extends AppCompatActivity {

   private String item;
    private String college;
    private String tSubject;
    private String tClass;
    private String tUid;
    //Toolbar mToolbar;
   private final ArrayList<String> categories=new ArrayList<>();
   private String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
   Bundle bundle;
    private static long back_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherlogin);

        bundle=getIntent().getExtras();
        college=bundle.getString("College","Default");
        tSubject=bundle.getString("Subject","Default");
        tUid = bundle.getString("tUid", "Default");
        tClass=bundle.getString("tClass");

        Spinner spinner2=findViewById(R.id.spinner2);

       // mToolbar=findViewById(R.id.takeattendancebar);
       // mToolbar.setTitle("Teacher"+"'s Dashboard  - "+date);

        ArrayAdapter<String> dataAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(college);

       /* ref.child("Teacher").child(tUid).child("classes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tClass=snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(teacherlogin.this, "Sorry Can't get data", Toast.LENGTH_SHORT).show();
            }
        });*/


        ref.child("Batch").child(tClass).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dp : snapshot.getChildren()){
                    categories.add(dp.getValue(String.class));
                }
                dataAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(teacherlogin.this, "Sorry Can't get data", Toast.LENGTH_SHORT).show();
            }
        });

        TextView txtView=findViewById(R.id.welcome);
        txtView.setText("Welcome : Teacher");


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void takeAttendanceButton(View view){
        Bundle basket=new Bundle();
        basket.putString("selected_batch",item);
        basket.putString("College",college);
        basket.putString("Subject",tSubject);

        Intent intent=new Intent(this,takeAttendance.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void previous_records(View view){
        Bundle basket=new Bundle();
        basket.putString("selected_batch",item);
        basket.putString("College",college);
        basket.putString("Subject",tSubject);

        Intent intent=new Intent(this,teacher_attendanceSheet.class);
        intent.putExtras(basket);
        startActivity(intent);

    }
    public void logoutTeacher(View view){
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