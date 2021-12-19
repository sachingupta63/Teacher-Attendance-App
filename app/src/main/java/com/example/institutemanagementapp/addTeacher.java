package com.example.institutemanagementapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.institutemanagementapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Models.Teacher;

public class addTeacher extends AppCompatActivity {

    EditText tName;
    EditText tId;
    EditText subject,tPassword,tEmail;
    String tname,tid,sub,classname,tpass,temail;
    Spinner classes;
   // Button addButton;
    DatabaseReference databaseTeacher;
   // Toolbar mToolbar;
    Bundle bundle;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        bundle=getIntent().getExtras();

        databaseTeacher= FirebaseDatabase.getInstance().getReference(bundle.getString("College"));

        tEmail=findViewById(R.id.tEmail);
        tName=findViewById(R.id.editText1);
        tId=findViewById(R.id.editText3);
        subject=findViewById(R.id.editText4);
        classes=findViewById(R.id.spinner3);
        tPassword=findViewById(R.id.editText5);
        //mToolbar=findViewById(R.id.ftoolbar);
       // mToolbar.setTitle("Add/Remove Teacher");

    }

    public void addTeacher(View view){
        temail=tEmail.getText().toString();
        tname=tName.getText().toString();
        tid=tId.getText().toString();
        sub=subject.getText().toString();
        classname=classes.getSelectedItem().toString();
        tpass=tPassword.getText().toString();

        if(!TextUtils.isEmpty(tId.getText().toString()) || !TextUtils.isEmpty(temail)){

            Teacher teacher=new Teacher(tname,tid,sub,classname,tpass,temail);
            databaseTeacher.child("Teacher").child(tid).setValue(teacher);
            Toast.makeText(this, "Teacher Added Successfully", Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            Toast.makeText(this, "Fields Cannot Be Empty", Toast.LENGTH_LONG).show();
        }
    }

    public void removeTeacher(View view){

        if(!TextUtils.isEmpty(tId.getText().toString()) || !TextUtils.isEmpty(tEmail.getText().toString())){
            tid=tId.getText().toString();
            temail=tEmail.getText().toString();
            databaseTeacher.child("Teacher").child(tid).setValue(null);
            Toast.makeText(this, "Teacher Removed Succesfully", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(this, "ID cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}