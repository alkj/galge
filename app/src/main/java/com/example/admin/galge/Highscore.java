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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;


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

        highScore1.setText(highscores);

        //HashSet<String> score = new HashSet<>();
        //score = (HashSet<String>) prefs.getStringSet("score", Collections.singleton("loading..."));
        //String q = "";
        //while(score.iterator().hasNext()){
        //    Log.i("TAG", "update: " + score);
        //    q += score;
        //    q += "\n";
        //}

    }
}
