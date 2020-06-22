package com.example.firestore7.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firestore7.ProgramBack;
import com.example.firestore7.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.firestore7.Main2Activity.programsList;

public class ProgramSet extends AppCompatActivity {
    public static ArrayList<ProgramBack.ProgramTimeTemp> steps;

    public static ArrayList<String> stepView;
    public static ProgramAdapter programAdapter;
    public static EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_set);
        setTitle("New program");
        steps= new ArrayList<ProgramBack.ProgramTimeTemp>();
        stepView=new ArrayList<String>();
        Button add= findViewById(R.id.button);
        Button addPro= findViewById(R.id.button2);
        text=findViewById(R.id.timer);
        //stepView.add("step");

        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);
        programAdapter = new ProgramAdapter(stepView);
        recyclerView1.setHasFixedSize(false);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(programAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepView.add("step");
                programAdapter.notifyDataSetChanged();
            }
        });
        addPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MainActivity.arrayList.add(text.getText().toString());
                //MainActivity.adapter.notifyDataSetChanged();
                programsList.add(new ProgramBack(text.getText().toString()));
                Map<String,Integer> pro= new HashMap<>();
                for(int i=0;i<steps.size();i++){
                    programsList.get(programsList.size()-1).addStep(steps.get(i).temp,steps.get(i).time);
                    Log.i("program","Step added: "+steps.get(i).time);
                    pro.put("Temp",steps.get(i).temp);
                    pro.put("Time",steps.get(i).time);
                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Programs")
                            .child(text.getText().toString()).child("Step"+i).setValue(pro);
                }
                finish();
            }
        });

    }
}
