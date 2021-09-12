package com.example.capstoneproject.ui.detail.cardetail

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityCarParkDetailBinding
import com.example.capstoneproject.model.Mall
import com.example.capstoneproject.viewmodel.MallViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CarParkDetailActivity : AppCompatActivity() {

    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_car,
            R.string.tab_motorcycle
        )
    }

    private var binding: ActivityCarParkDetailBinding? = null
    private val mallId: Int? by lazy {
        intent.getIntExtra(SectionsPagerAdapter.EXTRA_MALL, 1)
    }
    private val viewModel = MallViewModel.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarParkDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initUI()
        initProcess()
        initObserver()

//        val button: Button = findViewById(R.id.btn_book_car)

        /*button.setOnClickListener {
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
        }*/

        /*binding.imgBackIcon.setOnClickListener {
            onBackPressed()
        }*/
    }

    private fun initProcess() {
        mallId?.let { id ->
            viewModel.setMall(id)
        }
    }

    private fun initObserver() {
        viewModel.getMall().observe(this) { mall ->
            setData(mall)
        }
    }

    private fun initUI() {
        binding?.apply {
            setSupportActionBar(mToolbar)
            supportActionBar?.title = ""
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setData(mall: Mall) {
        val mName = mall.name
        val mRating = "%.1f".format(mall.rating)
        val mDistance = "%.1f".format(mall.distance) + "km"
        val mStatus = mall.status

        binding?.apply {
            titleParkir.text = mName
            rating.text = mRating
            distance.text = mDistance
            openClose.text = mStatus
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, mall)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs_layout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}