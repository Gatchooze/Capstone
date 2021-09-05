package com.example.capstoneproject.firebase.entity

import com.example.capstoneproject.model.MallSimplified
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class FirebaseMallSimplified(
    val id: Int? = null,
    val name: String? = null,
    val rating: Double? = null,
    val distance: Double? = null,
    val status: String? = null
) {
    fun toMallSimplified(): MallSimplified =
        MallSimplified(
            id ?: -1,
            name ?: "",
            rating ?: -1.0,
            distance ?: -1.0,
            status ?: ""
        )
}