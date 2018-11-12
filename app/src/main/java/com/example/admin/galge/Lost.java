package com.example.admin.galge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Lost extends AppCompatActivity {

    String theWord;
    TextView textViewLost;
    Button OK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        Intent intent = getIntent();
        String theWord = intent.getStringExtra("theWord");

        textViewLost = findViewById(R.id.textViewLost);
        OK = findViewById(R.id.buttonLost);

        textViewLost.setText("du tabte desværre! \nOrdet var: " + theWord + "\ntryk OK for at prøve igen");

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



}
