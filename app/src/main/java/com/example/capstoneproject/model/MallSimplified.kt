package com.example.capstoneproject.model

import android.os.Parcelable
import com.example.capstoneproject.firebase.entity.FirebaseMallSimplified
import kotlinx.parcelize.Parcelize

@Parcelize
data class MallSimplified(
    val id: Int,
    val name: String,
    val rating: Double,
    val distance: Double,
    val status: String
): Parcelable {
    fun toFirebaseMallSimplified(): FirebaseMallSimplified =
        FirebaseMallSimplified(
            id, name, rating, distance, status
        )
}