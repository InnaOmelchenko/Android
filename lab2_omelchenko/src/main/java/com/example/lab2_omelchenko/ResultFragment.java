package com.example.lab2_omelchenko;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ResultFragment extends DialogFragment implements DialogInterface.OnClickListener {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String str = (String)bundle.get("list");

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Results")
                .setNeutralButton("OK", this)
                .setMessage(str);
        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_NEUTRAL:
                break;
        }
    }
}
