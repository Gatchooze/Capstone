package com.example.capstoneproject.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneproject.R;
import com.example.capstoneproject.ui.login.LoginActivity;
import com.example.capstoneproject.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText etFullname, etEmail, etPhoneNumber, etPassword;
    Button btnRegister, btnRegisterGoogle;

    FirebaseAuth auth;

    private void init() {
        etFullname = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_phoneNumber);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        btnRegisterGoogle = findViewById(R.id.btn_register_google);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        LoadingDialog loadingDialog = new LoadingDialog(RegisterActivity.this);

        init();

        //Firebase Instance
        auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener((v) -> {
            valid();

            loadingDialog.startLoadingDialog();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                 loadingDialog.dissmissDialog();
                }
            }, 5000);
        });
    }

    void valid() {
        String fullname = etFullname.getText().toString();
        String email = etEmail.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String password = etPassword.getText().toString();

        if (fullname.equals("")) {
            Toast toast = Toast.makeText(this, "Full Name must be filled", Toast.LENGTH_SHORT);
            toast.show();
        } else if (email.equals("")) {
            Toast toast = Toast.makeText(this, "Email must be filled.", Toast.LENGTH_SHORT);
            toast.show();
        } else if (phoneNumber.equals("")) {
            Toast toast = Toast.makeText(this, "Phone Number must be filled", Toast.LENGTH_SHORT);
            toast.show();
        } else if (phoneNumber.length() > 12) {
            Toast toast = Toast.makeText(this, "Phone Number must be filled", Toast.LENGTH_SHORT);
            toast.show();
        } else if (password.isEmpty()) {
            Toast toast = Toast.makeText(this, "Password must be filled", Toast.LENGTH_SHORT);
            toast.show();
        } else if (checkUpper(password)) {
            Toast toast = Toast.makeText(this, "Password must contains at least 1 uppercase character", Toast.LENGTH_SHORT);
            toast.show();
        } else if (checkSpecial(password)) {
            Toast toast = Toast.makeText(this, "Password must contains at least 1 special character.", Toast.LENGTH_SHORT);
            toast.show();
        } else if (checkAlphaNum(password)) {
            Toast toast = Toast.makeText(this, "Password must contains at least 1 numeric value.", Toast.LENGTH_SHORT);
            toast.show();
        } else if (password.length() > 15) {
            Toast toast = Toast.makeText(this, "Password must be less than 15 characters.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private boolean checkAlphaNum(String password) {
        boolean numberExist = false, charExist = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                numberExist = true;
            }
            if (Character.isAlphabetic(password.charAt(i))) {
                charExist = true;
            }
        }

        if (numberExist && charExist) {
            return false;
        }
        return true;
    }

    private boolean checkSpecial(String password) {
        boolean specialExist = false;
        String special = "!@#$%^&*()+-_=[]{}|:;<>,./?|'";
        for (int i = 0; i < password.length(); i++) {
            char a = password.charAt(i);
            if (special.contains(Character.toString(a))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkUpper(String password) {
        boolean upperExist = false;
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < password.length(); i++) {
            char a = password.charAt(i);
            if (upper.contains(Character.toString(a))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAlpha(String phoneNumber) {
        boolean alphaExist = false;
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < phoneNumber.length(); i++) {
            char a = phoneNumber.charAt(i);
            if (alpha.contains(Character.toString(a))) {
                return false;
            }
        }
        return true;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//            finish();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.side_menu, menu);
        return true;
    }
}