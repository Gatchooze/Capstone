package com.example.capstoneproject.ui.detail.cardetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityCarParkDetailBinding
import com.example.capstoneproject.ui.mall.MallModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CarParkDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarParkDetailBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_car,
            R.string.tab_motorcycle
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarParkDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val mallModel: MallModel? = intent.getParcelableExtra("Mall Item")

        val mName = mallModel!!.getmName()
        val mRating = mallModel!!.getmRating()
        val mDistance = mallModel!!.getmDistance()
        val mStatus = mallModel!!.getmStatus()

        val name = findViewById<TextView>(R.id.title_parkir)
        name.text = mName

        val rating = findViewById<TextView>(R.id.rating)
        rating.text = mRating

        val distance = findViewById<TextView>(R.id.distance)
        distance.text = mDistance

        val status = findViewById<TextView>(R.id.open_close)
        status.text = mStatus


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs_layout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val button: Button = findViewById(R.id.btn_book_car)

        button.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@CarParkDetailActivity, R.style.BottomSheetDialogTheme
            )

            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.bottom_sheet,
                findViewById<LinearLayout>(R.id.bottom_sheet)
            )

            bottomSheetView.findViewById<View>(R.id.btn_book_detail).setOnClickListener {
                Toast.makeText(this@CarParkDetailActivity, "Next", Toast.LENGTH_SHORT).show()
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        binding.imgBackIcon.setOnClickListener {
            onBackPressed()
        }
    }
}