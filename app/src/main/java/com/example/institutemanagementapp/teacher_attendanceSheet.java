package com.example.institutemanagementapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class teacher_attendanceSheet extends AppCompatActivity {

    ListView listView;
    String college,class_selected,tSubject;

    EditText date;
    ArrayList<String> Userlist=new ArrayList<>();
    ArrayList<String> Studentlist=new ArrayList<>();
    DatabaseReference ref;
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent;
    String required_date;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_sheet);

        listView=findViewById(R.id.list);
        date=findViewById(R.id.date);


        Bundle bundle1=getIntent().getExtras();
        class_selected=bundle1.getString("selected_batch");
        college=bundle1.getString("College");
        tSubject=bundle1.getString("Subject");

        ref=FirebaseDatabase.getInstance().getReference(college);


    }

    public void viewlist(View view){
        Userlist.clear();
        dbStudent=ref.child("Student");
        dbStudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dsp: snapshot.getChildren()){
                    Userlist.add(dsp.child("sid").getValue(String.class));
                }
                display_list(Userlist);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(teacher_attendanceSheet.this, "something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void display_list(final ArrayList<String> userlist){
        Studentlist.clear();
        required_date=date.getText().toString();
        dbAttendance=ref.child("Attendance").child(class_selected);
        Studentlist.add("SID      "+"     Status ");
        for (Object sid: userlist){
            dbAttendance.child(required_date).child(sid.toString()).child(tSubject).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String p1=snapshot.getValue(String.class);
                    if ((p1.equals("A")) || (p1.equals("P"))) {
                        Studentlist.add(sid.toString() + "              " + p1);
                    }

                    list(Studentlist);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(teacher_attendanceSheet.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

    public void list(ArrayList<String> studentlist){
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,studentlist);
        listView.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}