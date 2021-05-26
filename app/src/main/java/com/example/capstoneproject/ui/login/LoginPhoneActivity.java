package com.example.capstoneproject.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstoneproject.R;
import com.example.capstoneproject.ui.main.MainActivity;
import com.example.capstoneproject.ui.register.RegisterActivity;

public class LoginPhoneActivity extends AppCompatActivity {
    Button btnLoginEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        final EditText etPhoneNumber = findViewById(R.id.et_phoneNumber);
        final EditText etPassword = findViewById(R.id.et_password);
        final Button btnLogin = findViewById(R.id.btn_login);

        btnLoginEmail = findViewById(R.id.btn_login_email);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = etPhoneNumber.getText().toString();
                String password = etPassword.getText().toString();

                if (phoneNumber.isEmpty()) {
                    Toast.makeText(LoginPhoneActivity.this, "Phone number must be filled.", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginPhoneActivity.this, "Password must be filled.", Toast.LENGTH_SHORT).show();
                }
//                else if (!checkUserLogin(phoneNumber, password)) {
//                    Toast.makeText(LoginPhoneActivity.this, "Phone number and password must be registered.", Toast.LENGTH_SHORT).show();
//                }
                else {
                    Intent intent = new Intent(LoginPhoneActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPhoneActivity.this, LoginEmailActivity.class));
            }
        });

    }

    private boolean checkUserLogin(String phoneNumber, String password) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.side_menu, menu);
        return true;
    }
}