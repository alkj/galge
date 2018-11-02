package com.example.admin.galge;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Play extends Fragment implements View.OnClickListener {

    //TODO: highscore

    private final String TAG = "PlayFragment";

    EditText editTextGuess;
    Button buttonGuess;
    TextView textViewWord, textViewErrors, textViewWrongLetters, textViewTitle;
    ImageView imageViewHangingMan;
    GalgeLogik galgeLogik;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_play, container, false);

        buttonGuess = (Button) rod.findViewById(R.id.buttonGuess);
        editTextGuess = (EditText) rod.findViewById(R.id.editTextGuess);
        textViewWord = (TextView) rod.findViewById(R.id.textViewWord);
        textViewErrors = (TextView) rod.findViewById(R.id.textViewErrorsNumber);
        textViewWrongLetters = (TextView) rod.findViewById(R.id.textViewWrongLetters);
        imageViewHangingMan = (ImageView) rod.findViewById(R.id.imageViewGalge);

        buttonGuess.setOnClickListener(this);
        galgeLogik = GalgeLogik.getInstance();
        return rod;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonGuess) {
            if (galgeLogik.erSpilletSlut()) {
                nulstil();
            }
            String letterGuessed = editTextGuess.getText().toString();
            letterGuessed = letterGuessed.toLowerCase();
            editTextGuess.getText().clear();
            guessLetter(letterGuessed);
        }
    }

    private void guessLetter(String letterGuessed) {
        galgeLogik.gætBogstav(letterGuessed);
        updateUI();

    }

    private void changeImage(int antalForkerteBogstaver) {
        switch (antalForkerteBogstaver) {
            case 0:
                imageViewHangingMan.setImageResource(R.drawable.wrong1);
                break;
            case 1:
                imageViewHangingMan.setImageResource(R.drawable.wrong2);
                break;
            case 2:
                imageViewHangingMan.setImageResource(R.drawable.wrong3);
                break;
            case 3:
                imageViewHangingMan.setImageResource(R.drawable.wrong4);
                break;
            case 4:
                imageViewHangingMan.setImageResource(R.drawable.wrong5);
                break;
            case 5:
                imageViewHangingMan.setImageResource(R.drawable.wrong6);
                break;
            case 6:
                imageViewHangingMan.setImageResource(R.drawable.wrong7);
                startAnimationAlmostLost();
                break;
            case 7:
                imageViewHangingMan.setImageResource(R.drawable.wrong7);
                startAnimationLost();
                break;
            default:
                imageViewHangingMan.setImageResource(R.drawable.wrong1);
        }
    }

    private void nulstil() {
        Log.i(TAG, "nulstil: ");
        imageViewHangingMan.clearAnimation();
        buttonGuess.clearAnimation();
        buttonGuess.setText("gæt");
        galgeLogik.nulstil();
    }

    private void startAnimationAlmostLost() {
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation r = new RotateAnimation(-1f, 1f, 0.5f, 0.5f);
        r.setDuration(30);
        r.setRepeatCount(-1);
        r.setRepeatMode(RotateAnimation.REVERSE);
        animationSet.addAnimation(r);
        imageViewHangingMan.startAnimation(animationSet);

    }

    private void startAnimationWon() {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(2000);
        animationSet.setFillAfter(true);
        animationSet.setFillEnabled(true);
        buttonGuess.startAnimation(animationSet);
    }

    private void startAnimationLost() {
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation t = new TranslateAnimation(0, 0, 0, 200);
        t.setDuration(500);
        t.setFillAfter(true);
        t.setRepeatCount(-1);
        t.setRepeatMode(TranslateAnimation.REVERSE);
        animationSet.addAnimation(t);

        RotateAnimation r = new RotateAnimation(0f, 15f, 0, 0);
        r.setDuration(300);
        r.setRepeatCount(-1);
        r.setRepeatMode(RotateAnimation.REVERSE);
        animationSet.addAnimation(r);
        imageViewHangingMan.startAnimation(animationSet);

    }


    private void updateUI() {
        Log.i(TAG, "updateUI: ");
        textViewWord.setText("" + galgeLogik.getSynligtOrd() + "");
        textViewErrors.setText("" + galgeLogik.getAntalForkerteBogstaver() + " antal forkerte");
        textViewWrongLetters.setText(" forkerte bogstaver: " + galgeLogik.getBrugteForkerteBogstaver().toString() + "");
        changeImage(galgeLogik.getAntalForkerteBogstaver());

        if (galgeLogik.erSpilletSlut()) {
            if (galgeLogik.erSpilletVundet()) {
                startAnimationWon();
                textViewErrors.setText("Du vandt! ");
            } else {
                textViewWord.setText("Du tabte! ordet var: " + galgeLogik.getOrdet());
                textViewErrors.setText("Spillet er slut");
            }
            buttonGuess.setText("Prøv igen?");
        }
        galgeLogik.logStatus();
    }


    @Override
    public void onResume() {
        if (galgeLogik != null) {
            updateUI();
        }
        super.onResume();
    }
}
