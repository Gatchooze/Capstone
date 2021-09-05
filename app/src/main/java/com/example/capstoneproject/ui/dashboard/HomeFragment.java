package com.example.capstoneproject.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.capstoneproject.R;
import com.example.capstoneproject.databinding.FragmentHomeBinding;
import com.example.capstoneproject.model.Booking;
import com.example.capstoneproject.model.MallSimplified;
import com.example.capstoneproject.ui.booking.BookingDetailActivity;
import com.example.capstoneproject.ui.search.SearchListActivity;
import com.example.capstoneproject.viewmodel.BookingViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {
    ImageButton imageButton;
    @Nullable
    FragmentHomeBinding binding = null;
    BookingViewModel bookingViewModel = BookingViewModel.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
        initObserver();
    }

    private void initObserver() {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid != null) {
            bookingViewModel.getMyBooking(uid, false).observe(getViewLifecycleOwner(), this::setBooking);
        }
    }

    private void setBooking(List<Booking> result) {
        if (binding != null) {
            if (result.size() > 0) {
                binding.layoutLatestActivity.setVisibility(View.VISIBLE);
                Collections.reverse(result);
                Booking booking = result.get(0);
                MallSimplified mall = booking.getMall();
                binding.tvHomeLatestMallName.setText(mall.getName());
                String timeIn = new SimpleDateFormat("KK:mm a", Locale.getDefault()).format(booking.getDateIn()) + " -";
                String dateIn = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(booking.getDateIn());
                binding.tvHomeLatestMallTime.setText(timeIn);
                binding.tvHomeLatestMallDate.setText(dateIn);
                String uid = FirebaseAuth.getInstance().getUid();
                if (uid != null) {
                    binding.layoutLatestActivity.setOnClickListener(v -> BookingDetailActivity.start(requireContext(), uid, booking.getId()));
                }
            }
            binding.edtHomeSearch.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), SearchListActivity.class);
                requireContext().startActivity(intent);
            });
        }
    }

    private void initUI() {
        if (binding != null) {

            AppCompatActivity activity = Objects.requireNonNull((AppCompatActivity) getActivity());

            activity.setSupportActionBar(binding.tbHome);
            Objects.requireNonNull(activity.getSupportActionBar()).setTitle("");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.side_menu2, menu);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
