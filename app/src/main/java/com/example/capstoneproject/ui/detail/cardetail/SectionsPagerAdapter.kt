package com.example.capstoneproject.ui.detail.cardetail

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.capstoneproject.model.Mall
import com.example.capstoneproject.ui.detail.cardetail.BookingFragment.Companion.EXTRA_MALL_SIMPLIFIED

class SectionsPagerAdapter(activity: AppCompatActivity, private val mall: Mall) :
    FragmentStateAdapter(activity) {

    companion object {
        const val EXTRA_MALL = "extra_mall"
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = CarFragment().apply {
                arguments = bundleOf(CarFragment.EXTRA_CAR_LOCATION to ArrayList(mall.carLocation),
                EXTRA_MALL_SIMPLIFIED to mall.toMallSimplified())
            }
            1 -> fragment = MotorcycleFragment().apply {
                arguments =
                    bundleOf(MotorcycleFragment.EXTRA_MOTORCYCLE_LOCATION to ArrayList(mall.motorcycleLocation),
                        EXTRA_MALL_SIMPLIFIED to mall.toMallSimplified())
            }
        }
        return (fragment as Fragment)
    }
}