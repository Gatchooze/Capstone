package com.example.capstoneproject.ui.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.capstoneproject.R;
import com.example.capstoneproject.databinding.FragmentActivityBinding;
import com.example.capstoneproject.ui.dashboard.viewpager.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ActivityFragment extends Fragment {

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_activity,
            R.string.tab_history
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        ViewPager2 viewPager2 = view.findViewById(R.id.view_pager_activity);
        viewPager2.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabs_layout_activity);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();
    }
}
