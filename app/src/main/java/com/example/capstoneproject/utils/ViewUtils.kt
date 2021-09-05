package com.example.capstoneproject.utils

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.capstoneproject.R
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

fun createMaterialShapeDrawable(bottomSheet: View, context: Context): MaterialShapeDrawable {
    val shapeAppearanceModel =
        //Create a ShapeAppearanceModel with the same shapeAppearanceOverlay used in the style
        ShapeAppearanceModel.builder(context, 0, R.style.CapstoneBottomSheetShape)
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

fun getSpannableString(
    text: String,
    context: Context,
    color: Int
): SpannableStringBuilder {
    val spannableString = SpannableStringBuilder(text)
    val mColor = ForegroundColorSpan(ResourcesCompat.getColor(context.resources, color, null))
    spannableString.setSpan(mColor, 0, text.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    return spannableString
}