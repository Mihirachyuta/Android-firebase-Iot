package com.example.firestore7.ui.slideshow;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.firestore7.Main2Activity;
import com.example.firestore7.R;

import java.util.Arrays;
import java.util.List;

import static com.example.firestore7.Main2Activity.fab;
import static com.example.firestore7.Main2Activity.firebaseConnect;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    TextView tempText,humidText,vibText,timeText;
    SeekBar seekBar;
    EditText timer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        fab.setVisibility(View.GONE);
        tempText=root.findViewById(R.id.textView1);
        humidText=root.findViewById(R.id.programTextView);
        vibText=root.findViewById(R.id.textView3);
        timeText=root.findViewById(R.id.textView4);
        if(Main2Activity.isFireBaseConnected) {
            tempText.setText("Temp: " + Main2Activity.firebaseConnect.getTemp());
            humidText.setText("Humidity: " + Main2Activity.firebaseConnect.getHumid());
            vibText.setText("Vibrations: " + Main2Activity.firebaseConnect.getVib());
            String a=Main2Activity.firebaseConnect.getDate();
            List<String> a1= Arrays.asList(a.split(" "));
            timeText.setText("Time: "+a1.get(0)+"\n           "+a1.get(1));
        }
        seekBar=root.findViewById(R.id.seekBar);
        timer=root.findViewById(R.id.timer);
        timer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                firebaseConnect.setTemp(seekBar.getProgress(),Integer.parseInt(s.toString()));
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                firebaseConnect.setTemp(seekBar.getProgress());
                Toast.makeText(seekBar.getContext(),"Set Temp:"+seekBar.getProgress(),Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}