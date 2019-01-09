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

    SharedPreferences prefs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_highscore, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        String highscores = prefs.getString("highscore", "loader...");
        List<String> arrayListhigh = Arrays.asList(highscores.split("\n"));

        for (int i = 0; i<arrayListhigh.size();i++){
            System.out.print("arraylist = " + arrayListhigh.get(i).toString());
            Log.i("TAG", "onCreateView: " + arrayListhigh.get(i).toString());
            if (arrayListhigh.get(i)==""||arrayListhigh.get(i)==" "||arrayListhigh.get(i)=="\n"||arrayListhigh.get(i)=="  "||arrayListhigh.get(i)=="   "||arrayListhigh.get(i)=="    "){
                arrayListhigh.remove(i);
            }
        }

        Collections.sort(arrayListhigh);
        String[] stringListHighscore = (String[]) arrayListhigh.toArray();

        ArrayList<String> arrayList = new ArrayList<String>();

        for (int i = 0;i<=4;i++){
            arrayList.add(i, arrayListhigh.get(i));
        }

        ListAdapter listAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, arrayList);
        ListView listView = rod.findViewById(R.id.listViewHighscore);
        listView.setAdapter(listAdapter);






        return rod;
    }

}
