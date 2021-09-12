package com.example.capstoneproject.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstoneproject.R;
import com.example.capstoneproject.model.Mall;
import com.example.capstoneproject.ui.detail.cardetail.CarParkDetailActivity;
import com.example.capstoneproject.ui.detail.cardetail.SectionsPagerAdapter;
import com.example.capstoneproject.ui.mall.MallAdapter;
import com.example.capstoneproject.viewmodel.MallViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SearchListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MallAdapter mMallAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MallViewModel viewModel = MallViewModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        initUI();
        initAction();
        initObserver();
        initProcess();
    }

    private void initAction() {
        TextInputEditText edtSearch = findViewById(R.id.edtSearch);
        TextInputLayout tilSearch = findViewById(R.id.tilSearch);

        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Editable query = ((TextInputEditText) v).getText();
                if(query != null){
                    viewModel.searchMalls(query.toString());
                }
            }
            return false;
        });
        tilSearch.setEndIconOnClickListener(v -> {
            edtSearch.setText("");
            viewModel.searchMalls("");
        });

        edtSearch.clearFocus();
        edtSearch.requestFocusFromTouch();
    }

    private void initProcess() {
        viewModel.searchMalls("");
    }

    private void initObserver() {
        viewModel.getMalls().observe(this, malls -> {
            mMallAdapter.setData(malls);
        });
    }

    private void initUI() {

        Toolbar toolbar = findViewById(R.id.tbSearch);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("");
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        initRecylerView();
    }

    public void initRecylerView() {

        mRecyclerView = findViewById(R.id.rv_search_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mMallAdapter = new MallAdapter(new ArrayList<>());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMallAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mMallAdapter.setOnItemClickListener(id -> {
            Intent intent = new Intent(SearchListActivity.this, CarParkDetailActivity.class);
            intent.putExtra(SectionsPagerAdapter.EXTRA_MALL, id);
            startActivity(intent);
        });

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.side_menu3, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mMallAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.nearby:
                Intent intent2 = new Intent(SearchListActivity.this, SearchLocationActivity.class);
                startActivity(intent2);
                return true;*/
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}