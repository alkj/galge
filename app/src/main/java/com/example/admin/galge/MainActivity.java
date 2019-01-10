package com.example.admin.galge;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    /**
     * Galgespil med en menu af fem fagmenter.
     *
     * Indeholder en metode til at skjule tastatur, for den virker ikke i fragmenter.
     * Ellers bruges metoden bottomnavigation til at udskifte fragmenter.
     *
     * Der kunne godt implementeres flere ting og finpudses lidt.
     *
     * @Alexander.Kjeldsen
     * @s165477@student.dtu.dk
     */

    Home home = new Home();
    Play play = new Play();
    Multiplayer multiplayer = new Multiplayer();
    Highscore highscore = new Highscore();
    Settings settings = new Settings();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home).commit();

    }



    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = home;
            switch (menuItem.getItemId()) {
                case (R.id.menu_home):
                    selectedFragment = home;
                    break;
                case (R.id.menu_play):
                    selectedFragment = play;
                    break;
                case (R.id.menu_multiplayer):
                    selectedFragment = multiplayer;
                    break;
                case (R.id.menu_highscore):
                    selectedFragment = highscore;
                    break;
                case (R.id.menu_settings):
                    selectedFragment = settings;
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };


    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}