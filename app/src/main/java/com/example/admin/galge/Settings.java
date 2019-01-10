package com.example.admin.galge;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class Settings extends Fragment {

    private Button buttonRestartHighscore;
    private Switch switchSound;
    private Switch switchMultiplayer;

    private SharedPreferences sharedPreferences;

    private boolean sound;
    private boolean multiplayer;


    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        buttonRestartHighscore = v.findViewById(R.id.buttonRestartHighscore);
        switchSound = v.findViewById(R.id.switchSound);
        switchMultiplayer = v.findViewById(R.id.switchMultiplayerMode);



        buttonRestartHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putString("highscore", "\n2 Alexander rugbrød\n3 Laura peber\n4 Peter høj\n5 Finn i\n6 per ø\n").commit();
                Toast.makeText(getActivity(), "Highscore restartet", Toast.LENGTH_SHORT).show();
            }
        });

        switchMultiplayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //toggle
            }
        });

        switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //toggle
            }
        });
        return v;
    }

    @Override
    public void onStop() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        sharedPreferences.edit().putBoolean("multiplayer", switchMultiplayer.isChecked()).commit();
        sharedPreferences.edit().putBoolean("sound", switchSound.isChecked()).commit();
        super.onStop();
    }

    @Override
    public void onResume() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        if (sharedPreferences.contains("multiplayer")){
            multiplayer = sharedPreferences.getBoolean("multiplayer", false);
            switchMultiplayer.setChecked(multiplayer);
        }
        if (sharedPreferences.contains("sound")){
            sound = sharedPreferences.getBoolean("sound", true);
            switchSound.setChecked(sound);
        }

        super.onResume();
    }
}