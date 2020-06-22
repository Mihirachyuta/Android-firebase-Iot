package com.example.firestore7.ui.gallery;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firestore7.ProgramBack;
import com.example.firestore7.R;

import java.util.ArrayList;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {
    ArrayList<ProgramBack.ProgramTimeTemp> listdata;
    ArrayList<String> list;
    //static ArrayList<EditModel> editModelArrayList;
    ProgramBack programBack;
    public int a=0,b=0;


    public ProgramAdapter(ArrayList<String> list) {
        //this.editModelArrayList=editModelArrayList;

        this.list=list;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.program_set_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String myListData = list.get(position);
        holder.textView.setText(myListData);
        holder.editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                a=Integer.parseInt(s.toString());
                if(ProgramSet.steps.size()>=position+1){
                    ProgramSet.steps.get(position).time=a;
                }else{
                    ProgramSet.steps.add(new ProgramBack.ProgramTimeTemp(b,a));}
                Log.i("Steps","Step added: "+a+" "+b);
                for(int i=0;i<ProgramSet.steps.size();i++)
                    Log.i("Array",ProgramSet.steps.get(i).getTemp()+" "+ProgramSet.steps.get(i).getTime());

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b=Integer.parseInt(s.toString());
                if(ProgramSet.steps.size()>=position+1){
                    ProgramSet.steps.get(position).temp=b;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });
        //holder.editText.setText(editModelArrayList.get(position).getEditTextValue());
        //holder.editText1.setText("hi");


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public ImageView imageView;
        public TextView textView;
        public LinearLayout linearLayout;
        public EditText editText;
        public EditText editText2;
        //public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            //this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear);
            this.editText=(EditText) itemView.findViewById(R.id.editText3);
            this.editText2=(EditText) itemView.findViewById(R.id.editText2);
            this.textView=itemView.findViewById(R.id.set_text);
            //relativeLayout= itemView.findViewById(R.id.relativeLayout);

        }
    }
}
