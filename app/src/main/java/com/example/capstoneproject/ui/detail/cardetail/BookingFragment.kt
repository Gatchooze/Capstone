package com.example.capstoneproject.ui.detail.cardetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentBookingBinding
import com.example.capstoneproject.model.Booking
import com.example.capstoneproject.model.ItemParkingPosition
import com.example.capstoneproject.model.MallSimplified
import com.example.capstoneproject.ui.booking.BookingDetailActivity
import com.example.capstoneproject.utils.*
import com.example.capstoneproject.viewmodel.BookingViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class BookingFragment : BottomSheetDialogFragment() {

    companion object {

        private const val VEHICLE_TYPE_KEY = "vehicle_type"
        const val EXTRA_BOOKING = "extra_booking"
        const val EXTRA_MALL_SIMPLIFIED = "extra_mall_simplified"
        const val EXTRA_LOCATIONS = "extra_locations"

        @JvmStatic
        fun newInstance(vehicleType: String, mall: MallSimplified, locations: List<ItemParkingPosition>) =
            BookingFragment().apply {
                arguments = bundleOf(
                    VEHICLE_TYPE_KEY to vehicleType,
                    EXTRA_MALL_SIMPLIFIED to mall,
                    EXTRA_LOCATIONS to locations
                )
            }
    }

    private val vehicleType: String? by lazy {
        arguments?.getString(VEHICLE_TYPE_KEY)
    }
    private val mall: MallSimplified? by lazy {
        arguments?.getParcelable(EXTRA_MALL_SIMPLIFIED)
    }
    private val locations: ArrayList<ItemParkingPosition>? by lazy {
        arguments?.getParcelableArrayList(EXTRA_LOCATIONS)
    }
    private val flows: ArrayList<Flow<Boolean>> = ArrayList()
    private var binding: FragmentBookingBinding? = null
    private val parkingPositionAdapter: ParkingPositionAdapter = ParkingPositionAdapter()
    private var bookingTime: Pair<Int, Int>? = null
    private var bookingDate: Long? = null
    private val viewModel = BookingViewModel.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        (dialog as? BottomSheetDialog)?.behavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    val materialShapeDrawable =
                        createMaterialShapeDrawable(bottomSheet, requireContext())
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
        binding = FragmentBookingBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initAction()
        initFlow()
    }

    private fun initFlow() {
        binding?.let { b ->
            b.tilBookingLicensePlate.editText?.let { edt ->
                flows.add(edt.textChanges().debounce(100).map {
                    it.isNotEmpty() && isValidLicensePlate(it)
                }.onEach { isValid ->
                    b.tilBookingLicensePlate.error =
                        if (isValid) null else getString(R.string.error_license_plate)
                }.also {
                    it.launchIn(lifecycleScope)
                })
            }
            b.tilBookingHour.editText?.initNotEmptyTextField(flows, lifecycleScope)
            b.tilBookingMinute.editText?.initNotEmptyTextField(flows, lifecycleScope)
            b.tilBookingAmPm.editText?.initNotEmptyTextField(flows, lifecycleScope)
            b.tilBookingArrivalDate.editText?.initNotEmptyTextField(flows, lifecycleScope)
            flows.add(
                parkingPositionAdapter.positionChanges().debounce(100).map {
                    it >= 0
                }.also {
                    it.launchIn(lifecycleScope)
                }
            )

            combine(flows) { conditions ->
                conditions.all { it }
            }.debounce(100).onEach { isValid ->
                b.btnBookingNext.isEnabled = isValid
            }.also {
                it.launchIn(lifecycleScope)
            }
        }
    }

    private fun isValidLicensePlate(charSequence: CharSequence): Boolean {
        val pattern =
            "^([A-Z]{1,3})(\\s|-)*([1-9][0-9]{0,3})(\\s|-)*([A-Z]{0,3}|[1-9][0-9]{1,2})\$".toRegex(
                RegexOption.IGNORE_CASE
            )

        return pattern.matches(charSequence)
    }

    private fun initAction() {
        binding?.apply{
            tilBookingHour.editText?.setOnClickListener {
                showTimePicker()
            }
            tilBookingMinute.editText?.setOnClickListener {
                showTimePicker()
            }
            tilBookingAmPm.editText?.setOnClickListener {
                showTimePicker()
            }
            tilBookingArrivalDate.editText?.setOnClickListener { edtBookingArrivalDate ->
                CapstoneDatePicker.getNewInstance(requireContext()) {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = it
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    val resultDate =
                        SimpleDateFormat(
                            "dd/MM/yyyy, EEEE",
                            Locale.getDefault()
                        ).format(calendar.time)
                    bookingDate = calendar.timeInMillis
                    (edtBookingArrivalDate as EditText).setText(resultDate)
                }.show(requireFragmentManager())
            }
            btnBookingNext.setOnClickListener {
                val calendar = Calendar.getInstance()
                bookingDate?.let { date ->
                    bookingTime?.let { time ->
                        calendar.apply {
                            timeInMillis = date
                            add(Calendar.HOUR_OF_DAY, time.first)
                            add(Calendar.MINUTE, time.second)
                        }
                    }
                }

                if(calendar.timeInMillis < Date().time){
                    Toast.makeText(requireContext(), getString(R.string.you_cant_order_in_past), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                vehicleType?.let { type ->
                    mall?.let { mall ->
                        val booking = Booking(
                            id = "${System.currentTimeMillis()}",
                            uid = "${FirebaseAuth.getInstance().uid}",
                            plateNumber = tilBookingLicensePlate.editText?.text?.toString()
                                ?.uppercase()!!,
                            dateIn = calendar.time,
                            position = parkingPositionAdapter.getCurrentItemLocation(),
                            isDone = false,
                            vehicleType = type,
                            mall = mall
                        )
                        viewModel.postBooking(booking).observe(viewLifecycleOwner){ isSuccess ->
                            if(isSuccess){
                                BookingDetailActivity.start(
                                    requireContext(),
                                    booking.uid,
                                    booking.id
                                )
                                activity?.finish()
                            }else{
                                Toast.makeText(requireContext(), getString(R.string.failed_to_booking), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showTimePicker(){
        CapstoneTimePicker.getNewInstance(requireContext()){ time, format ->
            binding?.apply {
                val formatNumber = DecimalFormat("00")
                bookingTime = time
                val hour = if (time.first > 12) time.first - 12 else time.first
                tilBookingHour.editText?.setText(formatNumber.format(hour))
                tilBookingMinute.editText?.setText(formatNumber.format(time.second))
                tilBookingAmPm.editText?.setText(format)
            }
        }.show(requireFragmentManager())
    }

    private fun initUI() {
        locations?.let { loc ->
            parkingPositionAdapter.setDataList(loc)
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.apply {
            rvBookingPosition.apply {
                adapter = this@BookingFragment.parkingPositionAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        parkingPositionAdapter.destroy()
        super.onDestroyView()
    }
}