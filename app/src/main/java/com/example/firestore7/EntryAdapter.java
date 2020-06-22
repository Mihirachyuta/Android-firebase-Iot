package com.example.firestore7;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestore7.ui.gallery.GalleryFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.firestore7.Main2Activity.firebaseConnect;
import static com.example.firestore7.Main2Activity.programsList;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private ArrayList<String> listdata;
        //private ArrayList<ProgramBack> listdata;
    // RecyclerView recyclerView;
    public EntryAdapter (ArrayList<String> listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.program_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String myListData = listdata.get(position);
        holder.textView.setText(listdata.get(position));
        holder.linearLayout.setBackgroundResource(color(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseConnect.startProgram(programsList.get(position));
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                    GalleryFragment.arrayList.remove(position);
                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Programs").child(programsList.get(position).programName).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("Program","deleted");
                        }
                    });
                    programsList.remove(position);
                    GalleryFragment.entryAdapter.notifyDataSetChanged();
                    Toast.makeText(v.getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                    return true;


            }
        });


    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public ImageView imageView;
        public TextView textView;
        public LinearLayout linearLayout;
        public SeekBar seekBar;
        public ImageView imageView;
        public ImageView editText;
        public View relativeLayout;

        //public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            //this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.programTextView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.programLinear);
            //relativeLayout= itemView.findViewById(R.id.relativeLayout);
        }
    }
    public int color(int i){
        if(i%4==1) return R.drawable.bg2;
        if(i%4==2) return R.drawable.bg3;
        if(i%4==3) return R.drawable.bg4;
        else return R.drawable.bg1;
    }
}
