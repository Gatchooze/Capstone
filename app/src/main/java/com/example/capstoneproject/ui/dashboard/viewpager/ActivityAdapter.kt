package com.example.capstoneproject.ui.dashboard.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.databinding.ItemsActivityBinding
import com.example.capstoneproject.model.Booking
import com.example.capstoneproject.ui.booking.BookingDetailActivity
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.*

class ActivityAdapter(
    private val dataList: ArrayList<Booking> = arrayListOf(),
    private val type: Int
) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ItemsActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(booking: Booking) {
            binding?.apply {
                rvItemTitleMall.text = booking.mall.name
                val time: String
                val date: String
                if (type == 0) {
                    date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(booking.dateIn) + " -"
                    time = SimpleDateFormat("KK:mm a", Locale.getDefault()).format(booking.dateIn) + " -"
                } else {
                    val timeIn =
                        SimpleDateFormat("KK:mm a", Locale.getDefault()).format(booking.dateIn)
                    val timeOut = SimpleDateFormat(
                        "KK:mm a",
                        Locale.getDefault()
                    ).format(booking.dateOut!!)
                    val dateIn = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(booking.dateIn)
                    val dateOut = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(booking.dateOut!!)
                    date = if(dateIn != dateOut) "$dateIn - $dateOut" else dateIn
                    time = "$timeIn - $timeOut"
                }
                rvItemTimeMall.text = time
                rvItemDateMall.text = date
            }
        }
    }

    private var binding: ItemsActivityBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemsActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val booking = dataList[position]
            bind(booking)
            itemView.setOnClickListener {
                BookingDetailActivity.start(holder.itemView.context, booking.uid, booking.id)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(dataList: ArrayList<Booking>, tvHistoryTabEmpty: MaterialTextView? = null){
        this.dataList.apply {
            clear()
            dataList.reverse()
            addAll(dataList)
            tvHistoryTabEmpty?.isGone = itemCount > 0
            notifyDataSetChanged()
        }
    }

    fun destroy() {
        binding = null
    }
}