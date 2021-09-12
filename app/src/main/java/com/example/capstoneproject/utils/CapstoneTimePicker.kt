package com.example.capstoneproject.utils

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.capstoneproject.R
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class CapstoneTimePicker private constructor(
    private val materialTimePicker: MaterialTimePicker
) {

    companion object {

        private const val EXTRA_TIME_PICKER = "extra_time_picker"

        private var onTimeRetrievedCallBack: OnTimeRetrievedCallBack? = null

        @JvmStatic
        fun getNewInstance(
            context: Context,
            onTimeRetrievedCallBack: OnTimeRetrievedCallBack? = null
        ): CapstoneTimePicker {

            this.onTimeRetrievedCallBack = onTimeRetrievedCallBack

            val timeBuilder = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(0)
                .setMinute(0)
                .setTitleText(context.getString(R.string.label_select_time))

            return CapstoneTimePicker(timeBuilder.build())
        }
    }

    private val onPositiveButtonClickListener = View.OnClickListener {
        onTimeRetrievedCallBack?.onTimeRetrieved(
            Pair(materialTimePicker.hour, materialTimePicker.minute),
            if (materialTimePicker.hour > 11) "PM" else "AM"
        )
    }

    init {
        with(materialTimePicker) {
            addOnPositiveButtonClickListener(onPositiveButtonClickListener)
        }
    }

    fun show(fragmentManager: FragmentManager) {
        if (!materialTimePicker.isAdded && !materialTimePicker.isVisible) {
            materialTimePicker.show(fragmentManager, EXTRA_TIME_PICKER)
        }
    }

    fun removeListener() {
        with(materialTimePicker) {
            removeOnPositiveButtonClickListener(onPositiveButtonClickListener)
        }
    }

    fun interface OnTimeRetrievedCallBack {
        fun onTimeRetrieved(timePair: Pair<Int, Int>, format: String)
    }

}