package com.example.capstoneproject.ui.detail.cardetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneproject.databinding.FragmentCarBinding
import com.example.capstoneproject.model.ItemParkingPosition
import com.example.capstoneproject.model.MallSimplified
import com.example.capstoneproject.ui.detail.cardetail.BookingFragment.Companion.EXTRA_MALL_SIMPLIFIED

class CarFragment : Fragment() {

    companion object {
        const val EXTRA_CAR_LOCATION = "extra_car_location"
    }

    private var binding: FragmentCarBinding? = null
    private val locations: ArrayList<ItemParkingPosition>? by lazy {
        arguments?.getParcelableArrayList(EXTRA_CAR_LOCATION)
    }
    private val mall: MallSimplified? by lazy {
        arguments?.getParcelable(EXTRA_MALL_SIMPLIFIED)
    }

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
            locations?.let { loc ->
                mall?.let { mall ->
                    BookingFragment.newInstance("car", mall, loc)
                        .show(requireFragmentManager(), BookingFragment.EXTRA_BOOKING)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}