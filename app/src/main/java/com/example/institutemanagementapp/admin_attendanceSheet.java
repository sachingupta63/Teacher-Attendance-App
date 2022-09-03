package com.example.institutemanagementapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class admin_attendanceSheet extends AppCompatActivity {

    ListView listView;
    Spinner class_name;
    String classes;
   // EditText date;
    ArrayList<String> UserList=new ArrayList<>();
    ArrayList<String> StudentList=new ArrayList<>();
    List<String> batch=new ArrayList<>();

    Bundle bundle;

    DatabaseReference ref;
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent;
    String required_date;
    //Toolbar mToolbar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance_sheet);
       // mToolbar=findViewById(R.id.ftoolbar);
       // mToolbar.setTitle("Attendance Records");
        listView=findViewById(R.id.list);
        class_name=findViewById(R.id.spinner5);
       // date=findViewById(R.id.date);

        bundle=getIntent().getExtras();
        ref=FirebaseDatabase.getInstance().getReference(bundle.getString("College"));

        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,batch);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        class_name.setAdapter(dataAdapter);

        ref.child("Batch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dsp : snapshot.getChildren()){
                    for (DataSnapshot dp : dsp.getChildren()){
                        batch.add(dp.getValue(String.class));
                    }
                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(admin_attendanceSheet.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        class_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classes=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void display_list(final ArrayList userList){
        StudentList.clear();
      //  required_date=date.getText().toString();
        dbAttendance=ref.child("Attendance");

        StudentList.add("  SID     "+"           Subject=P/A   ");

        dbAttendance.child(classes).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String attendance="";

                //traversing all the date
                for (DataSnapshot dsp : snapshot.getChildren()){
                    //adding date to array list
                    StudentList.add(dsp.getKey());
                    for (DataSnapshot dp : dsp.getChildren()){  // getting all student attendance

                        attendance="";

                        for (DataSnapshot d : dp.getChildren()){
                            attendance=attendance + d.getKey() + "=" + d.getValue() + "   ";
                        }

                        //adding 19249  DSA=P   CP=P
                        StudentList.add(dp.getKey() + "      " + attendance);
                    }
                }
                //passing data to ListView Adapter

                list(StudentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(admin_attendanceSheet.this, "something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Submit Button Clicked
    public void viewlist(View view){
        UserList.clear();
        dbStudent=ref.child("Student");
        dbStudent.orderByChild("classes").equalTo(classes).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp: snapshot.getChildren()){
                    UserList.add(dsp.child("sid").getValue(String.class));

                }

                display_list(UserList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(admin_attendanceSheet.this, "Something Went Wrong", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void list(ArrayList<String> studentlist){

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,studentlist);
        listView.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}