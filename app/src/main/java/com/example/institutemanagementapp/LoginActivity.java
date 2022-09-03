package com.example.institutemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText username,password,college_name;
    String item,college,userid,pass,fBasepassword,tSubject,sSid,tClass;
    DatabaseReference ref;
   // FirebaseAuth auth;
    //FirebaseUser firebaseUser;
    Bundle basket;
    ProgressDialog mDialog;
    String dbchild = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.editText2);
        college_name=findViewById(R.id.college_name);

        //auth=FirebaseAuth.getInstance();

        mDialog=new ProgressDialog(this);
        mDialog.setMessage("Please wait ...");
        mDialog.setTitle("Loading");

        Spinner spinner=findViewById(R.id.spinner);
        List<String> categories=new ArrayList<>();
        categories.add("Admin");
        categories.add("Teacher");
        categories.add("Student");

        ArrayAdapter<String> dataAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             item=parent.getItemAtPosition(position).toString();
                //Toast.makeText(LoginActivity.this, item + " is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Please Select any Category ...", Toast.LENGTH_LONG).show();
            }
        });

    }

    //Clicking Login Button

    public void onButtonClick(View view){
        college=college_name.getText().toString().toUpperCase();
        userid=username.getText().toString();
        pass=password.getText().toString();

        mDialog.show();

        basket=new Bundle();
        basket.putString("College",college);


        ref= FirebaseDatabase.getInstance().getReference(college);
        DatabaseReference dbuser=ref.child(item).child(userid);


            dbuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (item.equals("Admin")) {
                            fBasepassword = snapshot.child("Password").getValue(String.class);
                            basket.putString("aUid",userid);
                            verify(fBasepassword);
                        }

                        else {
                            if (item.equals("Student")) {
                                sSid=snapshot.child("sid").getValue(String.class);
                                basket.putString("sSid",sSid);
                                basket.putString("sEmail",userid);
                                dbchild = "spass";
                            }
                            if (item.equals("Teacher")) {
                               tSubject=snapshot.child("subject").getValue(String.class);
                               tClass=snapshot.child("classes").getValue(String.class);
                               userid=username.getText().toString();
                               basket.putString("tClass",tClass);
                               basket.putString("Subject",tSubject);
                               basket.putString("tUid",userid);
                               dbchild = "tpass";
                            }
                            fBasepassword = snapshot.child(dbchild).getValue(String.class);
                            verify(fBasepassword);
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });

    }

    //Function To verify password

    public void verify(String dbpassword) {
        mDialog.dismiss();
        if (userid.isEmpty()) {
            Toast.makeText(this, "Username Cannot Be Empty", Toast.LENGTH_SHORT).show();
        } else if (item.equals("Teacher") && pass.equals(dbpassword)) {
           // Toast.makeText(this, dbpassword, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this,teacherlogin.class);
            intent.putExtras(basket);
            startActivity(intent);
        } else if (item.equals("Admin") && pass.equals(dbpassword)) {
            //Toast.makeText(this, basket.getString("aUid"), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this,adminLogin.class);
            intent.putExtras(basket);
            startActivity(intent);
        } else if (item.equals("Student") && pass.equals(dbpassword)) {
            Intent intent = new Intent(LoginActivity.this,studentlogin.class);
            intent.putExtras(basket);
            startActivity(intent);
        }
        else if (!pass.equals(dbpassword)){
            Toast.makeText(this, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "ha ha ha....", Toast.LENGTH_SHORT).show();
        }

    }

}