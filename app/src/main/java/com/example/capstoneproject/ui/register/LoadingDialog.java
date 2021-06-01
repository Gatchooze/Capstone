package com.example.capstoneproject.ui.register;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.capstoneproject.R;

public class LoadingDialog {
    Activity activity;
    AlertDialog dialog;

    LoadingDialog(Activity myActivity) {
        activity = myActivity;
    }

    void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_popup, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    void dissmissDialog() {
        dialog.dismiss();
    }
}
