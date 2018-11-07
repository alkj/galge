package com.example.admin.galge;


import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeMap;


//TODO: implement logic

public class Highscore extends Fragment {

    TextView highScore1, highScore2, highScore3, highScore4, highScore5;
    SharedPreferences prefs;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_highscore, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        highScore1 = (TextView) rod.findViewById(R.id.textViewHighScore1);
        highScore2 = (TextView) rod.findViewById(R.id.textViewHighScore2);
        highScore3 = (TextView) rod.findViewById(R.id.textViewHighScore3);
        highScore4 = (TextView) rod.findViewById(R.id.textViewHighScore4);
        highScore5 = (TextView) rod.findViewById(R.id.textViewHighScore5);

        update();


        return rod;
    }

    private void update() {

        String highscores = prefs.getString("highscore", "loader...");

        String[] highscore = highscores.split("\n");



        Arrays.sort(highscore);

        String str ="";

        for(int i = 0 ; i<5; i++){
            str += highscore[i];
            str += "\n";
        }

        highScore1.setText(str);

    }
}
