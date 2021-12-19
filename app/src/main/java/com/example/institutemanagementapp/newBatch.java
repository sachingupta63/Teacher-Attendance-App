package com.example.institutemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class newBatch extends AppCompatActivity {

    EditText batch;
    Button newBatch;
    Spinner class_name;
    DatabaseReference databaseReference;
    Bundle basket;
    String branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batch);

        batch=findViewById(R.id.batch_reg);
        newBatch=findViewById(R.id.newBatchClick);
        class_name=findViewById(R.id.spinner_branch);

        basket=getIntent().getExtras();


        databaseReference= FirebaseDatabase.getInstance().getReference(basket.getString("College"));

        newBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                branch=class_name.getSelectedItem().toString();

                if(!TextUtils.isEmpty(branch) && !TextUtils.isEmpty(batch.getText().toString())) {

                    //  Map<String,String> bh=new HashMap<>();
                    // bh.put(batch.getText().toString()," ");
                    databaseReference.child("Batch").child(branch).push().setValue(batch.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(newBatch.this, "New Batch Added Successfull", Toast.LENGTH_SHORT).show();
                            batch.setText(null);
                            finish();

                        }
                    });

                }
                else {
                    Toast.makeText(newBatch.this, "Fields Cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}