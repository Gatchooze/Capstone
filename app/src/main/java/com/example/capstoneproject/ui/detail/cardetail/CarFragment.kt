package com.example.capstoneproject.ui.detail.cardetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneproject.databinding.FragmentCarBinding
import com.example.capstoneproject.ui.booking.BookingBottomSheetFragment

class CarFragment : Fragment() {

    private var binding: FragmentCarBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCarBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnBookCar?.setOnClickListener {
            BookingBottomSheetFragment.newInstance("car").show(requireFragmentManager(), BookingBottomSheetFragment.EXTRA_BOOKING)
        }
    }
}