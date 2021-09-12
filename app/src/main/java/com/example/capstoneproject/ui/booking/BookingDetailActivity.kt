package com.example.capstoneproject.ui.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityBookingDetailBinding
import com.example.capstoneproject.model.Booking
import com.example.capstoneproject.model.BookingQuery
import com.example.capstoneproject.ui.main.MainActivity
import com.example.capstoneproject.ui.ticket.TicketActivity
import com.example.capstoneproject.viewmodel.BookingViewModel
import java.text.SimpleDateFormat
import java.util.*

class BookingDetailActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_UID = "extra_uid"
        const val EXTRA_BOOKING_ID = "extra_booking_id"
        const val EXTRA_ACTIVITY_FROM = "extra_activity_from"

        @JvmStatic
        fun start(context: Context, uid: String, bookingId: String) {
            val activityFrom = context.javaClass.name
            val starter = Intent(context, BookingDetailActivity::class.java).apply {
                putExtras(
                    bundleOf(
                        EXTRA_UID to uid,
                        EXTRA_BOOKING_ID to bookingId,
                        EXTRA_ACTIVITY_FROM to activityFrom
                    )
                )
            }
            context.startActivity(starter)
        }
    }

    private var binding: ActivityBookingDetailBinding? = null
    private val bookingViewModel = BookingViewModel.getInstance()

    private var booking: Booking? = null
    private val uid: String? by lazy {
        intent?.extras?.getString(EXTRA_UID)
    }
    private val bookingId: String? by lazy {
        intent?.extras?.getString(EXTRA_BOOKING_ID)
    }
    private val activityFrom: String? by lazy {
        intent?.extras?.getString(EXTRA_ACTIVITY_FROM)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initUI()
        initAction()
        initObserver()
        initProcess()
    }

    private fun initProcess() {
        uid?.let { lUid ->
            bookingId?.let { bId ->
                bookingViewModel.setBooking(BookingQuery(bId, lUid))
            }
        }
    }

    private fun initObserver() {
        bookingViewModel.getBooking().observe(this) {
            setBooking(it)
            booking = it
        }
    }

    private fun setBooking(booking: Booking) {
        binding?.apply {
            tvBookingDetailPosition.text = booking.position
            val time: String
            val date: String
            if (booking.isDone) {
                val timeIn =
                    SimpleDateFormat("KK:mm a", Locale.getDefault()).format(booking.dateIn)
                val timeOut = SimpleDateFormat(
                    "KK:mm a",
                    Locale.getDefault()
                ).format(booking.dateOut!!)
                val dateIn =
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(booking.dateIn)
                val dateOut =
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(booking.dateOut!!)
                date = if (dateIn != dateOut) "$dateIn - $dateOut" else dateIn
                time = "$timeIn - $timeOut"
            } else {
                date = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(booking.dateIn) + " -"
                time =
                    SimpleDateFormat("KK:mm a", Locale.getDefault()).format(booking.dateIn) + " -"
                btnBookingDetailEndBook.isEnabled = Date().time > booking.dateIn.time
            }
            tvBookingDetailTime.text = time
            tvBookingDetailPlate.text = booking.plateNumber
            tvBookingDetailDate.text = date

            btnBookingDetailEndBook.isGone = booking.isDone
            btnBookingDetailTicket.isGone = booking.isDone

            tvBookingDetailTitle.text = booking.mall.name
            val rating = "%.1f".format(booking.mall.rating)
            tvBookingDetailRating.text = rating
            val distance = "%.1f km".format(booking.mall.distance)
            tvBookingDetailDistance.text = distance
            tvBookingDetailStatus.text = booking.mall.status
        }
    }

    private fun initAction() {
        binding?.apply {
            btnBookingDetailTicket.setOnClickListener {
                booking?.let { b ->
                    TicketActivity.start(this@BookingDetailActivity, b)
                }
            }
            btnBookingDetailEndBook.setOnClickListener {
                booking?.let { b ->
                    EndBookConfirmFragment.newInstance(b).show(
                        supportFragmentManager,
                        EndBookConfirmFragment.END_BOOK_CONFIRM_FRAGMENT
                    )
                }
            }
            btnBookingDetailCancel.setOnClickListener {
                booking?.let { b ->
                    uid?.let { uid ->
                        bookingViewModel.deleteBooking(uid, b.id).observe(this@BookingDetailActivity){ isSuccess ->
                            if(isSuccess){
                                onBackPressed()
                            }else{
                                Toast.makeText(this@BookingDetailActivity, getString(R.string.failed_to_cancel_book), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initUI() {

        binding?.apply {
            setSupportActionBar(mToolbar)
            supportActionBar?.title = ""
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    override fun onBackPressed() {
        val carParkDetailActivityPackage =
            "${this@BookingDetailActivity.packageName}.ui.CarParkDetailActivity"
        activityFrom?.let {
            if (it != carParkDetailActivityPackage) {
                super.onBackPressed()
            } else {
                MainActivity.start(this)
            }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}