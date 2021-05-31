package com.example.capstoneproject.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.ui.main.MainActivity;
import com.example.capstoneproject.ui.mall.MallAdapter;
import com.example.capstoneproject.ui.mall.MallModel;
import com.example.capstoneproject.ui.search.SearchListActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ImageButton imageButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageButton = view.findViewById(R.id.btn_activity);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
