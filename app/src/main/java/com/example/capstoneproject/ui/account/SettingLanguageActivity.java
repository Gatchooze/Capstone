package com.example.capstoneproject.ui.account;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstoneproject.R;

import java.util.Locale;

public class SettingLanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_setting_language);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);


        Button btnChangeLanguage = findViewById(R.id.btn_changeLanguage);

        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });
    }

    private void showChangeLanguageDialog() {
        final String[] listLanguage = {"Indonesia", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingLanguageActivity.this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(listLanguage, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //Indonesia
                    setLocale("id");
                    recreate();
                } else if (which == 1) {
                    //English
                    setLocale("en");
                    recreate();
                }

                //Dismis alert dialog when Language selected
                dialog.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mBuilder.show();

    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        //Save data to share preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Language", language);
        editor.apply();
    }

    //Load Language saved in share preferences
    public void loadLocale() {
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("My_Language", "");
        setLocale(language);
    }

}