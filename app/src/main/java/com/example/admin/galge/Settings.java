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
import android.widget.Switch;
import android.widget.Toast;


public class Settings extends Fragment implements View.OnTouchListener {

    private Button buttonRestartHighscore;
    private Switch switchSound;
    private Switch switchMultiplayer;

    private SharedPreferences sharedPreferences;


    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        buttonRestartHighscore = v.findViewById(R.id.buttonRestartHighscore);
        switchSound = v.findViewById(R.id.switchSound);
        switchMultiplayer = v.findViewById(R.id.switchMultiplayerMode);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        buttonRestartHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });


        switchMultiplayer.setOnTouchListener(this);
        switchSound.setOnTouchListener(this);



        return v;
    }

    private void restart() {
        sharedPreferences.edit().putString("highscore", "1 Alexander\n2 Laura\n1 Peter\n2 Finn\n1 per\n5 Peter\n1 Finn\n4 per\n2 Peter\n").commit();
        Toast.makeText(getActivity(), "Highscore restartet", Toast.LENGTH_SHORT);
    }

    @Override
    public void onResume() {
        boolean b = sharedPreferences.getBoolean("multiplayer", true);
        switchMultiplayer.setChecked(b);

        super.onResume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v==switchMultiplayer)
            Toast.makeText(getActivity(), "Multiplayer", Toast.LENGTH_SHORT).show();
        if (v==switchSound)
            Toast.makeText(getActivity(), "Sound", Toast.LENGTH_SHORT).show();

        return false;
    }
}