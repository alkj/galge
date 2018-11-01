package com.example.admin.galge;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    Home home = new Home();
    Play play = new Play();
    Highscore highscore = new Highscore();
    //TODO: implement singleton ?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case (R.id.menu_home):
                    selectedFragment = home;
                    break;
                case (R.id.menu_play):
                    selectedFragment = play;
                    break;
                case (R.id.menu_highscore):
                    selectedFragment = highscore;
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };
}
