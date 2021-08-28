package com.example.capstoneproject.ui.detail.cardetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentMotorcycleBinding
import com.example.capstoneproject.ui.booking.BookingBottomSheetFragment

class MotorcycleFragment : Fragment() {

    private var binding: FragmentMotorcycleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMotorcycleBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnBookMotor?.setOnClickListener {
            BookingBottomSheetFragment.newInstance("motorcycle").show(requireFragmentManager(), BookingBottomSheetFragment.EXTRA_BOOKING)
        }
    }
}