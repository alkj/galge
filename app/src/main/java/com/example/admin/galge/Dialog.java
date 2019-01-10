package com.example.admin.galge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Dialog extends DialogFragment implements View.OnClickListener {



    private static final String TAG = "Dialog";

    public OnInputSelected onInputSelected;

    public interface OnInputSelected {
        void sendInput(String input);
    }

    public EditText editTextInputDialog;
    private Button buttonOKDialog, buttonBackDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_won, container, false);

        editTextInputDialog = view.findViewById(R.id.editTextDialogName);
        buttonOKDialog = view.findViewById(R.id.buttonDialogOK);
        buttonBackDialog = view.findViewById(R.id.buttonDialogBack);

        buttonOKDialog.setOnClickListener(this);
        buttonBackDialog.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        if (v == buttonOKDialog) {
            if (!editTextInputDialog.equals("")) {
                Play play = (Play) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                String input = editTextInputDialog.getText().toString();
                play.sendInput(input);
                getDialog().dismiss();
            }

        }

        if (v == buttonBackDialog) {
            getDialog().dismiss();
        }

    }

}
