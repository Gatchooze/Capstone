package com.example.capstoneproject.ui.ticket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityTicketBinding
import com.example.capstoneproject.model.Booking
import com.example.capstoneproject.ui.detail.cardetail.BookingFragment.Companion.EXTRA_BOOKING
import java.text.SimpleDateFormat
import java.util.*

class TicketActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context, booking: Booking) {
            val starter = Intent(context, TicketActivity::class.java)
                .putExtra(EXTRA_BOOKING, booking)
            context.startActivity(starter)
        }
    }

    private var binding: ActivityTicketBinding? = null
    private val booking: Booking? by lazy {
        intent?.getParcelableExtra(EXTRA_BOOKING)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initUI()
    }

    private fun initUI() {
        binding?.apply {
            setSupportActionBar(tbTicket)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            booking?.let { b ->
//                tvTicketMallName.text = b.mall.name
                val time = SimpleDateFormat("KK:mm a", Locale.getDefault()).format(b.dateIn)
                tvTicketCheckInTime.text = time
                val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(b.dateIn)
                tvTicketBookedOn.text = date
                tvTicketLicensePlate.text = b.plateNumber
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}