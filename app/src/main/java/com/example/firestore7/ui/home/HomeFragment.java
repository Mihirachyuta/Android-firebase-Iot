package com.example.firestore7.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.firestore7.Main2Activity;
import com.example.firestore7.R;

import java.util.Arrays;
import java.util.List;

import static com.example.firestore7.Main2Activity.fab;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public static ProgressBar pb1,pb2,pb3;
    public static TextView t1,t2,t3,t4;

    public static void setDashboard() {
        for(int i=0;i<100;i++)
        if(Main2Activity.isFireBaseConnected){
            pb1.setProgress(Integer.parseInt(Main2Activity.firebaseConnect.getTemp()),true);
            pb2.setProgress(Integer.parseInt(Main2Activity.firebaseConnect.getHumid()),true);
            try{
                pb3.setProgress(Integer.parseInt(Main2Activity.firebaseConnect.getVib()),true);
                t1.setText(Main2Activity.firebaseConnect.getTemp());
                t2.setText(Main2Activity.firebaseConnect.getHumid());
                t3.setText(Main2Activity.firebaseConnect.getVib());
                String a=Main2Activity.firebaseConnect.getDate();
                List<String> a1= Arrays.asList(a.split(" "));
                t4.setText(a1.get(0)+"\n"+a1.get(1));}
            catch (Exception e){
                e.printStackTrace();
                pb3.setProgress(0,true);
            }
            break;
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        fab.setVisibility(View.GONE);
        pb1 = root.findViewById(R.id.progressBar5);
        pb2 = root.findViewById(R.id.progressBar6);
        pb3 = root.findViewById(R.id.progressBar3);
        t1 = root.findViewById(R.id.textView1);
        t2 = root.findViewById(R.id.programTextView);
        t3 = root.findViewById(R.id.textView3);
        t4 = root.findViewById(R.id.textView5);
        setDashboard();
        /*Timer timer= new Timer();
        TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {
                for(int i=1; i<=10;i++)
                setDashboard();
            }
        };
        timer.scheduleAtFixedRate(timerTask,100,100);*/
        return root;
    }

}