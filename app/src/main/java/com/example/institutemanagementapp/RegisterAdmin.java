package com.example.institutemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterAdmin extends AppCompatActivity {
    EditText email_admin,pass_admin,name_admin;
    Button register_admin;
    //FirebaseAuth auth;
    DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);
        email_admin=findViewById(R.id.register_email);
        pass_admin=findViewById(R.id.register_pass);
        name_admin=findViewById(R.id.register_name);
        register_admin=findViewById(R.id.Registerbutton);

        //auth=FirebaseAuth.getInstance();

        register_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name_admin.getText().toString())){
                    Toast.makeText(RegisterAdmin.this, "College Name required", Toast.LENGTH_LONG).show();
                }

               else if(TextUtils.isEmpty(email_admin.getText().toString())||TextUtils.isEmpty(pass_admin.getText().toString())){
                    Toast.makeText(RegisterAdmin.this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    String email=email_admin.getText().toString();
                    String password=pass_admin.getText().toString();

                    HashMap<String,Object> map=new HashMap<>();
                    map.put("Password",password);

                    firebaseDatabase=FirebaseDatabase.getInstance().getReference(name_admin.getText().toString().toUpperCase());
                    firebaseDatabase.child("Admin").child(email).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterAdmin.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterAdmin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    Toast.makeText(RegisterAdmin.this, "Successfully Registered as Admin", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(RegisterAdmin.this,LoginActivity.class);
                    startActivity(intent);

                }
            }
        });


    }
}