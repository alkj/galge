package com.example.admin.galge;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameWon extends AppCompatActivity implements View.OnClickListener {
    int score;
    TextView textViewpoints;
    EditText editTextName;
    Button buttonOK;
    SharedPreferences Pref = PreferenceManager.getDefaultSharedPreferences(this);

    public GameWon(int score, MainActivity mainActivity){
        this.score=score;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_won);

        textViewpoints = findViewById(R.id.textViewPoints);
        editTextName = findViewById(R.id.editTextName);
        buttonOK = findViewById(R.id.buttonOK);

    }

    @Override
    public void onClick(View v) {

    }
}
