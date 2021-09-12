package com.example.capstoneproject.ui.dashboard.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneproject.databinding.FragmentHistoryTabBinding
import com.example.capstoneproject.model.Booking
import com.example.capstoneproject.viewmodel.BookingViewModel
import com.google.firebase.auth.FirebaseAuth

class HistoryTabFragment : Fragment() {

    private var binding: FragmentHistoryTabBinding? = null
    private val activityAdapter: ActivityAdapter by lazy {
        ActivityAdapter(type = 1)
    }
    private val viewModel = BookingViewModel.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryTabBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObserver()
    }

    private fun initObserver() {
        FirebaseAuth.getInstance().uid?.let { uid ->
            viewModel.getMyBooking(uid, true).observe(viewLifecycleOwner){ bookings ->
                setData(bookings)
            }
        }
    }

    private fun setData(bookings: List<Booking>) {
        binding?.apply {
            activityAdapter.setData(ArrayList(bookings), tvHistoryTabEmpty)
        }
    }

    private fun initUI() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.apply {
            rvHistoryTab.adapter = activityAdapter
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}