package com.example.institutemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class student_attendanceSheet extends AppCompatActivity {

   // public static int count=1,P=1,A=1;
   // float average=(float) 0.0;
    TextView t;
   // String p1,p2,p3,p4,p5,p6;
    String student_id,batch;
    ArrayList<String> dates=new ArrayList<>();
    DatabaseReference ref;
    DatabaseReference dbAttendance;
    ListView listView;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_sheet);

        t=findViewById(R.id.textView3);

        listView=findViewById(R.id.list);
        bundle=getIntent().getExtras();
        student_id=bundle.getString("sSid");
        batch=bundle.getString("Batch");
        t.setText(student_id);

        ref=FirebaseDatabase.getInstance().getReference(bundle.getString("College"));


        dates.clear();
        dates.add("  Date             "+ "Subjects=P/A  ");
        dbAttendance=ref.child("Attendance").child(batch);
        dbAttendance.addListenerForSingleValueEvent(new ValueEventListener() {
            String attendance="";
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp: snapshot.getChildren()){

                    attendance="";

                     for (DataSnapshot sid : dsp.child(student_id).getChildren()) {
                         attendance=attendance + sid.getKey() + "=" + sid.getValue(String.class)+ "||";

                     }

                    dates.add(dsp.getKey() + "  " + attendance);

                    list(dates);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(student_attendanceSheet.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void list(ArrayList<String> studentlist){


        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,studentlist);
        listView.setAdapter(adapter);

        /*try {
            average=(float)(P*100)/count;
            String avg=Float.toString(average);
            t.setText("Your Attendance is : " +avg+"%");
            if (average>=75){
                t.setTextColor(Color.GREEN);
            }
            else {
                t.setTextColor(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}