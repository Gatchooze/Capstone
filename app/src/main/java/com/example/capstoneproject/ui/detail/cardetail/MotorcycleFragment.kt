package com.example.capstoneproject.ui.detail.cardetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstoneproject.databinding.FragmentMotorcycleBinding
import com.example.capstoneproject.model.ItemParkingPosition
import com.example.capstoneproject.model.MallSimplified
import com.example.capstoneproject.ui.detail.cardetail.BookingFragment.Companion.EXTRA_MALL_SIMPLIFIED

class MotorcycleFragment : Fragment() {

    companion object {
        const val EXTRA_MOTORCYCLE_LOCATION = "extra_motorcycle_location"
    }

    private var binding: FragmentMotorcycleBinding? = null
    private val locations: ArrayList<ItemParkingPosition>? by lazy {
        arguments?.getParcelableArrayList(EXTRA_MOTORCYCLE_LOCATION)
    }
    private val mall: MallSimplified? by lazy {
        arguments?.getParcelable(EXTRA_MALL_SIMPLIFIED)
    }

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
            locations?.let { loc ->
                mall?.let { mall ->
                    BookingFragment.newInstance("motorcycle", mall, loc)
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