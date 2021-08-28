package com.example.capstoneproject.ui.booking

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.BookingBottomsheetFragmentBinding
import com.example.capstoneproject.model.ItemParkingPosition
import com.example.capstoneproject.ui.detail.cardetail.ParkingPositionAdapter
import com.example.capstoneproject.utils.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.timepicker.MaterialTimePicker
import kotlinx.coroutines.flow.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class BookingBottomSheetFragment : BottomSheetDialogFragment() {

    private val vehicleType: String? by lazy {
        arguments?.takeIf { it.containsKey(VEHICLE_TYPE_KEY) }?.let {
            return@lazy it.getString(VEHICLE_TYPE_KEY)
        }
    }
    private val flows: ArrayList<Flow<Boolean>> = ArrayList()
    private var binding: BookingBottomsheetFragmentBinding? = null
    private val parkingPositionAdapter: ParkingPositionAdapter by lazy {
        ParkingPositionAdapter(ItemParkingPosition.generateParkingPosition())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        (dialog as? BottomSheetDialog)?.behavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    val materialShapeDrawable = createMaterialShapeDrawable(bottomSheet)
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

    private fun createMaterialShapeDrawable(bottomSheet: View): MaterialShapeDrawable {
        val shapeAppearanceModel =
            //Create a ShapeAppearanceModel with the same shapeAppearanceOverlay used in the style
            ShapeAppearanceModel.builder(requireContext(), 0, R.style.CapstoneBottomSheetShape)
                .build()

        //Create a new MaterialShapeDrawable (you can't use the original MaterialShapeDrawable in the BottoSheet)
        val currentMaterialShapeDrawable = bottomSheet.background as MaterialShapeDrawable
        return MaterialShapeDrawable(shapeAppearanceModel).apply {
            //Copy the attributes in the new MaterialShapeDrawable
            initializeElevationOverlay(context)
            fillColor = currentMaterialShapeDrawable.fillColor
            tintList = currentMaterialShapeDrawable.tintList
            elevation = currentMaterialShapeDrawable.elevation
            strokeWidth = currentMaterialShapeDrawable.strokeWidth
            strokeColor = currentMaterialShapeDrawable.strokeColor
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BookingBottomsheetFragmentBinding.inflate(inflater)
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
            b.tilBookingHour.editText?.let { edt ->
                edt.initNotEmptyTextField(flows, lifecycleScope)
            }
            b.tilBookingMinute.editText?.let { edt ->
                edt.initNotEmptyTextField(flows, lifecycleScope)
            }
            b.tilBookingAmPm.editText?.let { edt ->
                edt.initNotEmptyTextField(flows, lifecycleScope)
            }
            b.tilBookingArrivalDate.editText?.let { edt ->
                edt.initNotEmptyTextField(flows, lifecycleScope)
            }
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

        return pattern.matches(charSequence).also {
            Log.d("Test", "word: $charSequence result: $it")
        }
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
                    val resultDate =
                        SimpleDateFormat("MM/dd/yyyy, EEEE", Locale.getDefault()).format(Date(it))
                    (edtBookingArrivalDate as EditText).setText(resultDate)
                }.show(requireFragmentManager())
            }
        }
    }

    private fun showTimePicker(){
        CapstoneTimePicker.getNewInstance(requireContext()){ time, format ->
            binding?.apply {
                val formatNumber = DecimalFormat("00")
                tilBookingHour.editText?.setText(formatNumber.format(time.first))
                tilBookingMinute.editText?.setText(formatNumber.format(time.second))
                tilBookingAmPm.editText?.setText(format)
            }
        }.show(requireFragmentManager())
    }

    private fun initUI() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.apply {
            rvBookingPosition.apply {
                adapter = this@BookingBottomSheetFragment.parkingPositionAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        parkingPositionAdapter.destroy()
        super.onDestroyView()
    }

    companion object {

        private const val VEHICLE_TYPE_KEY = "vehicle_type"
        const val EXTRA_BOOKING = "extra_booking"

        @JvmStatic
        fun newInstance(vehicleType: String) =
            BookingBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(VEHICLE_TYPE_KEY, vehicleType)
                }
            }
    }
}