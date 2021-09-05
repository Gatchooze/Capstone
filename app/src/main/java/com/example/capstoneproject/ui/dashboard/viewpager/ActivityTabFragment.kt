package com.example.capstoneproject.ui.dashboard.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneproject.databinding.FragmentActivityTabBinding
import com.example.capstoneproject.model.Booking
import com.example.capstoneproject.viewmodel.BookingViewModel
import com.google.firebase.auth.FirebaseAuth

class ActivityTabFragment : Fragment() {

    private var binding: FragmentActivityTabBinding? = null
    private val bookingViewModel = BookingViewModel.getInstance()
    private val activityAdapter by lazy {
        ActivityAdapter(type = 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActivityTabBinding.inflate(layoutInflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObserver()
    }

    private fun initObserver() {
        FirebaseAuth.getInstance().uid?.let { uid ->
            bookingViewModel.getMyBooking(uid, false).observe(viewLifecycleOwner){ bookings ->
                setData(bookings)
            }
        }
    }

    private fun initUI() {
        initRecyclerView()
    }

    private fun setData(bookings: List<Booking>){
        activityAdapter.setData(ArrayList(bookings))
    }

    private fun initRecyclerView() {
        binding?.apply {
            with(rvActivity) {
                adapter = activityAdapter
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}