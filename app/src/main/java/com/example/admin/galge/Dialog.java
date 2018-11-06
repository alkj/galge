package com.example.admin.galge;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Dialog extends DialogFragment implements View.OnClickListener {
    private String TAG = "Dialog";

    public OnInputSelected onInputSelected;

    public interface OnInputSelected {
        void sendInput(String input);
    }

    private EditText editTextInputDialog;
    private Button buttonOKDialog, buttonBackDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onInflate(inflater, container, savedInstanceState);

        //View view = inflater.inflate(R.layout.dialog, container, false);

        editTextInputDialog = (EditText) view.findViewById(R.id.editTextDialogName);
        buttonOKDialog = (Button) view.findViewById(R.id.buttonDialogOK);
        buttonBackDialog = (Button) view.findViewById(R.id.buttonDialogBack);

        buttonOKDialog.setOnClickListener(this);
        buttonBackDialog.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {

        if (v == buttonOKDialog) {
            if (!editTextInputDialog.equals("")) {
                Play play = (Play) getActivity().getSupportFragmentManager().findFragmentByTag("play");
                play.setWinnerName(editTextInputDialog.getText().toString());
            }
            getDialog().dismiss();

        }

        if (v == buttonBackDialog) {
            getDialog().dismiss();
        }

    }


}
