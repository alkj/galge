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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;



public class Highscore extends Fragment {

    /**
     * Det var lidt svært at styre helt præcist hvad der sker med highscoren.
     * men det virker stort set perfekt, hvis man bare ikke skriver mellemrum i sit navn.
     *
     * costum adapter getView implementeret
     *
     * costum list implementeret
     *
     *
     * @alexander_kjeldsen
     */

    SharedPreferences prefs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_highscore, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        String highscores = prefs.getString("highscore", "loader...");
        List<String> arrayListhigh = Arrays.asList(highscores.split("\n"));

        Collections.sort(arrayListhigh);
        String[] stringListHighscore = (String[]) arrayListhigh.toArray();

        final ArrayList<String> arrayListName = new ArrayList<String>();
        final ArrayList<String> arrayListWord = new ArrayList<String>();

        for (int i = 0;i<=5;i++){
            try {
                arrayListName.add(i, arrayListhigh.get(i));

            } catch (Exception e){
                Log.i("TAG", "onCreateView: " + e);
            }

            try {
                arrayListWord.add(i, arrayListhigh.get(i).split(" ")[2]);
            } catch (Exception e){
                Log.i("TAG", "onCreateView: " + e);
                arrayListWord.add(i, " ");
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.my_list, R.id.textViewHighscoreTitle, arrayListName){
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                View view = super.getView(position, cachedView, parent);

                TextView textViewHighscoreTitle = view.findViewById(R.id.textViewHighscoreTitle);
                TextView textViewHighscoreSubtitle = view.findViewById(R.id.textViewHighscoreSubtitle);
                textViewHighscoreTitle.setText(arrayListName.get(position));
                textViewHighscoreSubtitle.setText(arrayListWord.get(position));

                return view;
            }
       };

        ListView listView = rod.findViewById(R.id.listViewHighscore);

        listView.setAdapter(arrayAdapter);






        return rod;
    }

}
