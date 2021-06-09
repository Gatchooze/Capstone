package com.example.capstoneproject.ui.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capstoneproject.R;

public class SettingHowToUseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_how_to_use);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

    }
}