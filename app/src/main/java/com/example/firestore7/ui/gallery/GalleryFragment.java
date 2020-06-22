package com.example.firestore7.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestore7.EntryAdapter;
import com.example.firestore7.ProgramBack;
import com.example.firestore7.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.firestore7.Main2Activity.fab;
import static com.example.firestore7.Main2Activity.programsList;
import static com.example.firestore7.Main2Activity.recyclerView;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    public static Intent intent;
    public static ArrayList<String> arrayList=new ArrayList<String>();
    public static ArrayList<ProgramBack> programArrayList=new ArrayList<ProgramBack>();
    public static  EntryAdapter entryAdapter=new EntryAdapter(arrayList);
    public static int programcount=0;
    public static boolean addprog;
    public static int a;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        intent=new Intent(getContext(), ProgramSet.class);
        fab.setVisibility(View.VISIBLE);
        if(addprog) {
            getPrograms();
            addprog=false;
        }
        recyclerView = (RecyclerView) root.findViewById(R.id.rcview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(entryAdapter);

        return root;
    }
    public static void getPrograms(){
        a=0;
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Programs");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayList.add(dataSnapshot.getKey());
                programsList.add(new ProgramBack(dataSnapshot.getKey()));
                Log.i("programs", String.valueOf(programsList.size()));
                Log.i("steps", String.valueOf(dataSnapshot.getChildrenCount()));
                for(int i=0;i<dataSnapshot.getChildrenCount();i++){
                int t= Integer.parseInt(dataSnapshot.child("Step"+i).child("Temp").getValue().toString());
                int p= Integer.parseInt(dataSnapshot.child("Step"+i).child("Time").getValue().toString());
                programsList.get(a).addStep(t,p);
                }
                for(int i=0;i<programsList.get(a).timeTempArrayList.size();i++){
                    Log.i("Program Step",a+" "+programsList.get(a).timeTempArrayList.get(i).temp);
                }
                Log.i("programcount",String.valueOf(a));
                a++;
                /*for(int i=0;i<dataSnapshot.getChildrenCount();i++){
                    int t= Integer.parseInt(dataSnapshot.child("Step"+i).child("Temp").getValue().toString());
                    int p= Integer.parseInt(dataSnapshot.child("Step"+i).child("Time").getValue().toString());
                    programsList.get(programcount).addStep(t,p);
                    Log.i("ProgramStep",t+" "+p);
                }
                programcount+=1;
                Log.i("programcount", String.valueOf(programcount));*/

                entryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}