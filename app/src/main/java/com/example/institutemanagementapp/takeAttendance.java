package com.example.institutemanagementapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class takeAttendance extends AppCompatActivity {

    String teacher_id;
    String class_selected;
   // Spinner period;
    String periodNo;
    ArrayList<String> selecteditems;
    ArrayList<String> nonselecteditems;

//    ArrayList<String> ul;
//    ListView listView;
    private ArrayAdapter adapter;
    ArrayList<String> Userlist=new ArrayList<>();

    DatabaseReference ref;
    DatabaseReference dbAttendance;
    String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        selecteditems=new ArrayList<>();
        TextView classname=findViewById(R.id.textView);
        classname.setText("CSE");

        Bundle bundle1=getIntent().getExtras();
        class_selected=bundle1.getString("selected_batch");
        periodNo=bundle1.getString("Subject");

        classname.setText(class_selected);

        ref=FirebaseDatabase.getInstance().getReference(bundle1.getString("College"));
        DatabaseReference dbuser=ref.child("Student");

        dbuser.orderByChild("classes").equalTo(class_selected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp: snapshot.getChildren()){
                    Userlist.add(dsp.child("sid").getValue(String.class));
                  //  Usernames.add(dsp.child("sname").getValue().toString());
                }
                onStart(Userlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(takeAttendance.this, "something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void onStart(ArrayList<String> userlist){
        nonselecteditems=userlist;
        ListView ch1=findViewById(R.id.checkable_list);
        ch1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<String> aa=new ArrayAdapter<>(this,R.layout.checkable_list_layout,R.id.txt_title,userlist);
        ch1.setAdapter(aa);
        ch1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem=((TextView) view).getText().toString();
                if (selecteditems.contains(selectedItem)){
                    selecteditems.remove(selectedItem);
                }
                else {
                    selecteditems.add(selectedItem);
                }
            }
        });
    }


    public void showSelectedItems(View view){
       // String selItems="";
       // periodNo=period.getSelectedItem().toString();
            //ref=FirebaseDatabase.getInstance().getReference();
            dbAttendance=ref.child("Attendance").child(class_selected).child(date);

            //Setting attendance
            for (String item: selecteditems){
                Toast.makeText(this, "Attendance Created Successfully", Toast.LENGTH_SHORT).show();
                nonselecteditems.remove(item);
                dbAttendance.child(item).child(periodNo).setValue("P");

            }

            //for making absent
            for (String item: nonselecteditems){

                dbAttendance.child(item).child(periodNo).setValue("A"); // Look the Syntax
            }
            Toast.makeText(this, "Attendance Created Successfully", Toast.LENGTH_SHORT).show();
            finish();

    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}