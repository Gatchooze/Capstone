package com.example.capstoneproject.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.ui.mall.MallAdapter;
import com.example.capstoneproject.ui.mall.MallModel;

import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {
    private ArrayList<MallModel> dataList;

    private RecyclerView mRecyclerView;
    private MallAdapter mMallAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        createMallList();
        buildRecylerView();
    }

    public void createMallList(){
        dataList = new ArrayList<>();
        dataList.add(new MallModel("Mall 1", "Rating mall 1", "Car capacity 1", "Motor capacity 1"));
        dataList.add(new MallModel("Mall 2", "Rating mall 2", "Car capacity 2", "Motor capacity 2"));
        dataList.add(new MallModel("Mall 3", "Rating mall 3", "Car capacity 3", "Motor capacity 3"));
        dataList.add(new MallModel("Mall 4", "Rating mall 4", "Car capacity 4", "Motor capacity 4"));
        dataList.add(new MallModel("Mall 5", "Rating mall 5", "Car capacity 5", "Motor capacity 5"));
        dataList.add(new MallModel("Mall 6", "Rating mall 6", "Car capacity 6", "Motor capacity 6"));
        dataList.add(new MallModel("Mall 7", "Rating mall 7", "Car capacity 7", "Motor capacity 7"));
        dataList.add(new MallModel("Mall 8", "Rating mall 8", "Car capacity 8", "Motor capacity 8"));
        dataList.add(new MallModel("Mall 9", "Rating mall 9", "Car capacity 9", "Motor capacity 9"));
        dataList.add(new MallModel("Mall 10", "Rating mall 10", "Car capacity 10", "Motor capacity 10"));
    }

    public void buildRecylerView() {
        mRecyclerView = findViewById(R.id.rv_search_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mMallAdapter = new MallAdapter(dataList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMallAdapter);

        mMallAdapter.setOnItemClickListener(new MallAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchListActivity.this, SearchLocationActivity.class);
                intent.putExtra("Mall Item", dataList.get(position));
                startActivity(intent);
            }
        });

    }
}