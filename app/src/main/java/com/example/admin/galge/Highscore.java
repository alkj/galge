package com.example.admin.galge;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


//TODO: implement logic

public class Highscore extends Fragment {

    TextView highScore1, highScore2, highScore3, highScore4, highScore5;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_highscore, container, false);

        highScore1 = (TextView) rod.findViewById(R.id.textViewHighScore1);
        highScore2 =  (TextView) rod.findViewById(R.id.textViewHighScore2);
        highScore3 =  (TextView) rod.findViewById(R.id.textViewHighScore3);
        highScore4 =  (TextView) rod.findViewById(R.id.textViewHighScore4);
        highScore5 =  (TextView) rod.findViewById(R.id.textViewHighScore5);

        update();


        return rod;
    }

    private void update() {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        String highscores = pref.getString("highscore", "loader...");
        highscores.split(":");




    }

}
