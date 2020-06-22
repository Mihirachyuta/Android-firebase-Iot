package com.example.firestore7;


import android.os.AsyncTask;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firestore7.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.firestore7.Main2Activity.recyclerView;

public class FirebaseConnect extends AsyncTask<String,Void,String> {
    DocumentReference db1, db;
    String set, date, temp, vib, humid;
    public Boolean s=false;
    public int i,count;
    //"users/humid","device-configs/sample-device1"


    @Override
    protected String doInBackground(String... strings) {
        db1 = FirebaseFirestore.getInstance().document(strings[0]);
        db = FirebaseFirestore.getInstance().document(strings[1]);
        db1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("db1", "Listen failed.", e);
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Log.i("Receive", documentSnapshot.getData().toString());

                    // Timestamp ts =String.valueOf(documentSnapshot.get("Timestamp"));
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(new Date(Integer.parseInt(String.valueOf(documentSnapshot.get("Timestamp"))) * 1000L));
                    temp = String.valueOf(documentSnapshot.get("Temperature"));
                    vib = String.valueOf(documentSnapshot.get("Vibrations"));
                    humid = String.valueOf(documentSnapshot.get("Humidity"));
                    set = "Last update: " + date + "\n" + "Temperature: " + temp + "\n" + "Humidity: "
                            + humid + "\n" + "Vibrations: " + vib;

                    Log.i("temp", String.valueOf(documentSnapshot.get("Temperature")));
                    Log.i("time", date);
                    Main2Activity.isFireBaseConnected=true;
                    HomeFragment.setDashboard();
                } else {
                    Log.d("db1", "Current data: null");

                }
            }
        });
        return set;
    }


    public String getVib() {
        return vib;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumid() {
        return humid;
    }

    public String getDate() {
        return date;
    }

    public String getSet() {
        return set;
    }


    public void setTemp(int t) {
        Map<String, Object> temp = new HashMap<>();
        temp.put("temp", String.valueOf(t));
        db.set(temp)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("fire", "success ");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("fire", "failure");
            }
        });
    }

    public void startProgram(ProgramBack programBack){
        final int numberOfSteps=programBack.getTimeTempArrayList().size();
        final ArrayList<ProgramBack.ProgramTimeTemp> steps=programBack.getTimeTempArrayList();
        setTemp(steps.get(0).temp);
        Toast.makeText(recyclerView.getContext(),"Started",Toast.LENGTH_SHORT).show();
        int a= steps.get(0).time;
        Log.i("Number of Steps", String.valueOf(numberOfSteps));
        count=1;
        for(i=1;i<=numberOfSteps;i++){
            final Handler handler = new Handler();
            if(i<steps.size()){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("StepEXE","Step Started "+steps.get(count).temp);
                    setTemp(steps.get(count).temp);
                    count++;
                }
            }, a*60*1000);
            a+=steps.get(i).time;}
            else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setTemp(0);
                        Log.i("stepEXE","Program Ended");
                        Toast.makeText(recyclerView.getContext(),"Program Ended",Toast.LENGTH_SHORT).show();
                    }
                },a*60*1000);
            }

        }


    }
    public void setTemp(int t,int b) {
        Map<String, Object> temp = new HashMap<>();
        temp.put("temp", String.valueOf(t));
        setTemp(t);
        new CountDownTimer(b*60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                Log.i("timer","Finished");
                setTemp(0);
                Toast.makeText(recyclerView.getContext(),"Done!",Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

}
