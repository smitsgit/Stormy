package com.example.smitald.stormy.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.example.smitald.stormy.R;

/**
 * Created by smitald on 5/1/2015.
 */
public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.sorry_message))
                            .setMessage("There was an error , Please try again")
                            .setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
