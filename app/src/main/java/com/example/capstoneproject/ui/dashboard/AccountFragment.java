package com.example.capstoneproject.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstoneproject.R;
import com.example.capstoneproject.ui.account.SettingAccountActivity;
import com.example.capstoneproject.ui.login.LoginActivity;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {
    ImageButton imageButton;

    Button btnLogout;

    TextView tvFullName, tvPhoneNumber, tvEmail;

    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        imageButton = view.findViewById(R.id.btn_edit);
        btnLogout = view.findViewById(R.id.btn_signout);
        tvEmail = view.findViewById(R.id.tv_email);

        auth = FirebaseAuth.getInstance();

//        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
//        if (signInAccount != null) {
//            tvEmail.setText(signInAccount.getEmail());
//        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingAccountActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
