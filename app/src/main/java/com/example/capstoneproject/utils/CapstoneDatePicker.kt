package com.example.capstoneproject.utils

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.capstoneproject.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.util.*

class CapstoneDatePicker private constructor(
    private val materialDatePicker: MaterialDatePicker<Long>
) {

    companion object {

        private const val EXTRA_DATE_PICKER = "extra_date_picker"

        private var onPositiveButtonClickListener: MaterialPickerOnPositiveButtonClickListener<Long>? =
            null

        @JvmStatic
        fun getNewInstance(
            context: Context,
            positiveButtonClickListener: MaterialPickerOnPositiveButtonClickListener<Long>? = null
        ): CapstoneDatePicker {

            onPositiveButtonClickListener = positiveButtonClickListener

            val calendar = Calendar.getInstance()
            val today = MaterialDatePicker.todayInUtcMilliseconds()
            calendar.timeInMillis = today
            val start = calendar.timeInMillis
            calendar.add(Calendar.YEAR, 1)
            val end = calendar.timeInMillis

            val calendarConstraints = CalendarConstraints.Builder().apply {
                setValidator(DateValidatorPointForward.now())
                setStart(start)
                setEnd(end)
            }
            val dateBuilder = MaterialDatePicker.Builder.datePicker().apply {
                setTitleText(context.getString(R.string.label_select_date))
                setCalendarConstraints(calendarConstraints.build())
            }

            return CapstoneDatePicker(dateBuilder.build())
        }
    }

    init {
        with(materialDatePicker) {
            if (onPositiveButtonClickListener != null) {
                addOnPositiveButtonClickListener(onPositiveButtonClickListener)
            }
        }
    }

    fun show(fragmentManager: FragmentManager) {
        if (!materialDatePicker.isAdded && !materialDatePicker.isVisible) {
            materialDatePicker.show(fragmentManager, EXTRA_DATE_PICKER)
        }
    }

    fun removeListener() {
        with(materialDatePicker) {
            removeOnPositiveButtonClickListener(onPositiveButtonClickListener)
        }
    }

}