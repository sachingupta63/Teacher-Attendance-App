package com.example.institutemanagementapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.institutemanagementapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Models.Student;

public class addStudent extends AppCompatActivity {

    EditText sName,sEmail;
    EditText sId,sPassword;
    String sname,sid,classname=" ",spass,semail;
    //Spinner classes;
    DatabaseReference databaseStudent;
    Bundle bundle;
    Spinner spn;

    List<String> batches=new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        bundle=getIntent().getExtras();

        databaseStudent=FirebaseDatabase.getInstance().getReference(bundle.getString("College"));
        sEmail=findViewById(R.id.sEmail);
        sName=findViewById(R.id.editText1);
        sId=findViewById(R.id.editText3);
        spn=findViewById(R.id.spinner3);
        sPassword=findViewById(R.id.editText4);

        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,batches);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spn.setAdapter(dataAdapter);

        databaseStudent.child("Batch").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()){
                    for (DataSnapshot dp : dsp.getChildren()) {
                        batches.add(dp.getValue(String.class));
                    }
                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classname=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void addStudent(View view){

        if((!TextUtils.isEmpty(sId.getText().toString()) && !TextUtils.isEmpty(sEmail.getText().toString())) && !TextUtils.isEmpty(classname)){
            semail=sEmail.getText().toString();
            sname=sName.getText().toString();
            sid=sId.getText().toString();
            //classname=classes.getSelectedItem().toString();
            spass=sPassword.getText().toString();

            Student student=new Student(sname,sid,classname,spass,semail);
            databaseStudent.child("Student").child(sid).setValue(student);
            Toast.makeText(this, "Student Added Successfully", Toast.LENGTH_LONG).show();
            finish();

        }
        else {
            Toast.makeText(this, "Email Fields cannot be empty and Batch cannot be empty", Toast.LENGTH_LONG).show();
        }

    }

    public void removeStudent(View view){

        if(!TextUtils.isEmpty(sId.getText().toString()) || !TextUtils.isEmpty(sEmail.getText().toString())){
            semail=sEmail.getText().toString();
            sid=sId.getText().toString();
            databaseStudent.child("Student").child(sid).setValue(null);
            Toast.makeText(this, "Student removed Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this, "Id cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
   public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}