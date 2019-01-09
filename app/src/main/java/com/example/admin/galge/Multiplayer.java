package com.example.admin.galge;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;




public class Multiplayer extends Fragment {

    private String string;
    private ListView listView;
    private SharedPreferences sharedPreferences;

    public Multiplayer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View r = inflater.inflate(R.layout.fragment_multiplayer, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        listView = r.findViewById(R.id.listViewChooseWord);

        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("multiplayer mode off");
        arrayList.add("this");
        arrayList.add("is");
        arrayList.add("a");
        arrayList.add("list");
        arrayList.add("hi");
        arrayList.add("this");
        arrayList.add("is");
        arrayList.add("a");
        arrayList.add("list");
        arrayList.add("hi");
        arrayList.add("this");
        arrayList.add("is");
        arrayList.add("a");
        arrayList.add("list");


        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){
                    sharedPreferences.edit().putBoolean("multiplayer", false).commit();
                    Toast.makeText(getActivity(),  " multiplayer mode off ", Toast.LENGTH_SHORT).show();

                } else if (position > 0){
                    sharedPreferences.edit().putBoolean("multiplayer", true).commit();
                    sharedPreferences.edit().putString("multiplayerWord", arrayList.get(position)).commit();
                    Toast.makeText(getActivity(), "clicked : " + position + " multiplayer mode on. the word is" + arrayList.get(position), Toast.LENGTH_SHORT).show();

                } else {
                    throw new RuntimeException(getContext().toString());
                }
            }
        });
        return r;
    }
}
