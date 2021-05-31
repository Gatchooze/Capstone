package com.example.capstoneproject.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.capstoneproject.R;
import com.example.capstoneproject.ui.dashboard.HomeFragment;
import com.example.capstoneproject.ui.mall.MallAdapter;
import com.example.capstoneproject.ui.mall.MallModel;

import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {
    private ArrayList<MallModel> mallModels = new ArrayList<>();
    private MallAdapter mMallAdapter;
    private RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        buildRecyclerView();

    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.rv_search_list);
        mMallAdapter = new MallAdapter(SearchListActivity.this);
        mRecyclerView.setAdapter(mMallAdapter);
        mRecyclerView.setHasFixedSize(true);

//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}