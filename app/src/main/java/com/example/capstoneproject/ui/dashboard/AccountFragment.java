package com.example.capstoneproject.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstoneproject.R;
import com.example.capstoneproject.ui.account.SettingAccountActivity;

public class AccountFragment extends Fragment {
    ImageButton imageButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        imageButton = view.findViewById(R.id.btn_edit);

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
