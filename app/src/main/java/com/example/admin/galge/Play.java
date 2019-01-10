package com.example.admin.galge;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jinatonic.confetti.CommonConfetti;


public class Play extends Fragment implements View.OnClickListener, Dialog.OnInputSelected {

    /**
     * Jeg programmerer på engelsk, men objektet er på dansk.
     * nogle interfaces implementeret.
     *
     * onDestroy onResume implementeret for at styre/rydde op i media player.
     * indstillinger styre om der er lyd og om det er multiplayer.
     *
     * @Alexander_kjeldsen
     * @s165477
     **/


    private final String TAG = "PlayFragment";

    EditText editTextGuess;
    Button buttonGuess, buttonRestart;
    TextView textViewWord, textViewErrors, textViewWrongLetters, textViewTitle, textViewTimer;
    ImageView imageViewHangingMan;
    GalgeLogik galgeLogik;
    CountDownTimer countDownTimer;
    int timer;
    boolean gameIsRunning;
    SharedPreferences prefs;
    int errors;
    boolean multiplayermode;
    boolean sound;
    MediaPlayer mediaPlayerWon;
    ViewGroup containerA;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_play, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        sound = prefs.getBoolean("sound", true);

        mediaPlayerWon = MediaPlayer.create(getActivity(), R.raw.trumpet_won);

        buttonGuess = (Button) rod.findViewById(R.id.buttonGuess);
        buttonRestart = (Button) rod.findViewById(R.id.buttonRestart);
        editTextGuess = (EditText) rod.findViewById(R.id.editTextGuess);
        textViewWord = (TextView) rod.findViewById(R.id.textViewWord);
        textViewErrors = (TextView) rod.findViewById(R.id.textViewErrorsNumber);
        textViewWrongLetters = (TextView) rod.findViewById(R.id.textViewWrongLetters);
        textViewTimer = (TextView) rod.findViewById(R.id.textViewTimer);
        imageViewHangingMan = (ImageView) rod.findViewById(R.id.imageViewGalge);
        containerA = (ViewGroup) rod.findViewById(R.id.relativeLayoutPlay);

        buttonGuess.setOnClickListener(this);
        buttonRestart.setOnClickListener(this);

        galgeLogik = GalgeLogik.getInstance();

        restart();
        isMultiplayer();

        return rod;
    }

    private void isMultiplayer() {

        multiplayermode = prefs.getBoolean("multiplayer", false);
        String theWord = prefs.getString("multiplayerWord", "-1");

        if ( theWord == "-1"){
            Toast.makeText(this.getActivity(), "multiplayer mode is " + multiplayermode + " no word. \nGame restarted", Toast.LENGTH_SHORT).show();
            restart();
        } else if (multiplayermode==true){
            Toast.makeText(this.getActivity(), "multiplayer mode is " + multiplayermode + " the word is " + theWord, Toast.LENGTH_SHORT).show();
            galgeLogik.setOrdet(theWord);
            updateUI();
        }  else if (multiplayermode==false){
            Toast.makeText(this.getActivity(), "multiplayer mode is " + multiplayermode, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonGuess) {

            if (gameIsRunning) {
                galgeLogik.gætBogstav(editTextGuess.getText().toString().toLowerCase());
                editTextGuess.getText().clear();
                updateUI();
            }

        } else if (v.getId() == R.id.buttonRestart) {

            restart();
        }
    }


    private void updateUI() {
        Log.i(TAG, "updateUI: ");
        textViewWord.setText("" + galgeLogik.getSynligtOrd() + "");
        textViewErrors.setText("" + galgeLogik.getAntalForkerteBogstaver() + " antal forkerte");
        textViewWrongLetters.setText(" forkerte bogstaver: " + galgeLogik.getBrugteForkerteBogstaver().toString() + "");
        changeImage(galgeLogik.getAntalForkerteBogstaver());


        if (galgeLogik.erSpilletSlut() || !gameIsRunning) {
            gameIsRunning = false;

            if (galgeLogik.erSpilletVundet()) {
                startDialogWon();
                startAnimationWon();
                this.errors = galgeLogik.getAntalForkerteBogstaver();
                CommonConfetti.rainingConfetti(containerA, new int[] {Color.RED, Color.GREEN, Color.WHITE, Color.BLACK, Color.YELLOW}).infinite();
                if (sound){
                    mediaPlayerWon.start();
                }
            } else {

                Intent intent = new Intent(getActivity(), Lost.class);
                intent.putExtra("theWord", galgeLogik.getOrdet());
                startActivity(intent);

                textViewWord.setText("Du tabte! ordet var: \n" + galgeLogik.getOrdet());
                textViewErrors.setText("Spillet er slut");
                startAnimationLost();
            }
            countDownTimer.cancel();
        }
        galgeLogik.logStatus();
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

    private void restart() {
        Log.i(TAG, "restart: ");
        gameIsRunning = true;
        try {
            galgeLogik.erstatMuligeOrd(prefs.getString("titler", "hest løb høj politikere sandhed pervers"));
            Log.i(TAG, "restart: galgelogik.erstatmuligeord");
        } catch (Exception e) {
            System.out.print("no words loaded \n" + e);
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        startTimer(60000);
        imageViewHangingMan.clearAnimation();
        buttonGuess.clearAnimation();
        try {
            loadWordsFromInternet();
        } catch (Exception e) {
            Log.e(TAG, "restart: ", e);
        }
        galgeLogik.nulstil();
        updateUI();
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


    private void startTimer(int time) {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Long tim = millisUntilFinished;
                timer = tim.intValue() / 1000;
                textViewTimer.setText("" + millisUntilFinished / 1000 + "");
                Log.i(TAG, "startTimer: " + timer);
            }

            @Override
            public void onFinish() {
                gameIsRunning = false;
                try {
                    countDownTimer.cancel();
                } catch (Exception e){
                    Log.d(TAG, "onFinish: timer can not cancel");
                }

                try{
                    textViewTimer.setText("" + 0 + "");
                    timer = 0;
                    updateUI();
                } catch (Exception e){
                    Log.d(TAG, "onFinish: timer done, but window not in use");

                }
            }
        };

        countDownTimer.start();
    }


    private void loadWordsFromInternet() {
        Log.i(TAG, "loadWordsFromInternet: ");

        new AsyncTask() {
            String newWords;

            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    newWords = galgeLogik.hentOrdFraDr();
                    return newWords;
                } catch (Exception e) {
                    e.printStackTrace();
                    return e;
                }
            }

            @Override
            protected void onPostExecute(Object titler) {
                Log.i(TAG, "onPostExecute: -------------------------------------------comitted new words-----------------------------------");
                prefs.edit().putString("titler", newWords).commit();
                //updateUI();
            }


        }.execute();
    }

    @Override
    public void sendInput(String input) {
        Log.i(TAG, "sendInput: " + input);
        String winnerName = input;
        String highscores = prefs.getString("highscore", "none");
        int point = errors;
        Log.i(TAG, "updateUI: " + winnerName + " " + point);
        textViewErrors.setText("Du vandt " + winnerName + "!\nDu fik\n" + point + " points!");
        highscores += "" + galgeLogik.getAntalForkerteBogstaver() + " " + winnerName + " " + galgeLogik.getOrdet() + "\n";
        prefs.edit().putString("highscore", highscores).commit();

    }

    private void startDialogWon() {

        Dialog dialog = new Dialog();
        dialog.setTargetFragment(Play.this,1);
        dialog.show(getFragmentManager(), "Dialog");

    }

    @Override
    public void onDestroy() {
        if (mediaPlayerWon!=null){
            mediaPlayerWon.release();
            mediaPlayerWon = null;
        }

        super.onDestroy();
    }

    @Override
    public void onResume() {

        if (mediaPlayerWon==null){
            mediaPlayerWon = MediaPlayer.create(getActivity(), R.raw.trumpet_won);
        }

        super.onResume();
    }
}