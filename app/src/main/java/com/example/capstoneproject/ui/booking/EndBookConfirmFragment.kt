package com.example.capstoneproject.ui.booking

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import com.example.capstoneproject.databinding.FragmentEndBookConfirmBinding
import com.example.capstoneproject.model.Booking
import com.example.capstoneproject.ui.detail.cardetail.BookingFragment.Companion.EXTRA_BOOKING
import com.example.capstoneproject.ui.main.MainActivity
import com.example.capstoneproject.utils.createMaterialShapeDrawable
import com.example.capstoneproject.viewmodel.BookingViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class EndBookConfirmFragment : BottomSheetDialogFragment() {

    private var binding: FragmentEndBookConfirmBinding? = null
    private val booking: Booking? by lazy {
        arguments?.takeIf { it.containsKey(EXTRA_BOOKING) }?.getParcelable(EXTRA_BOOKING)
    }
    private val bookingViewModel = BookingViewModel.getInstance()
    private var dateOut: Date = Date()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        (dialog as? BottomSheetDialog)?.behavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    val materialShapeDrawable = createMaterialShapeDrawable(bottomSheet, requireContext())
                    ViewCompat.setBackground(bottomSheet, materialShapeDrawable)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

        })

        (dialog as? BottomSheetDialog)?.setOnShowListener {
            val d = it as BottomSheetDialog
            d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                ?.let { bottomSheet ->
                    BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
                }
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEndBookConfirmBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initAction()
    }

    private fun initAction() {
        binding?.apply {
            btnEndBookConfirmConfirm.setOnClickListener {
                booking?.let{ b ->
                    bookingViewModel.patchBooking(b.apply {
                        isDone = true
                        dateOut = this@EndBookConfirmFragment.dateOut
                    })
                    MainActivity.start(requireContext())
                }
            }
        }
    }

    private fun initUI() {
        binding?.apply {
            booking?.let { b ->
                dateOut = Date()
                val timeIn = SimpleDateFormat("KK:mm a", Locale.getDefault()).format(b.dateIn) + " in"
                tvEndBookConfirmTimeIn.text = timeIn
                val timeOut = SimpleDateFormat("KK:mm a", Locale.getDefault()).format(dateOut) + " out"
                tvEndBookConfirmTimeOut.text = timeOut
                tvEndBookConfirmLicensePlate.text = b.plateNumber
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {

        const val END_BOOK_CONFIRM_FRAGMENT = "enb_book_confirm_fragment"

        @JvmStatic
        fun newInstance(booking: Booking) =
            EndBookConfirmFragment().apply {
                arguments = bundleOf(EXTRA_BOOKING to booking)
            }
    }
}