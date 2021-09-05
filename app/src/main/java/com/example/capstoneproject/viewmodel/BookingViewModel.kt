package com.example.capstoneproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.capstoneproject.model.Booking
import com.example.capstoneproject.model.BookingQuery
import com.example.capstoneproject.model.Mall
import com.example.capstoneproject.repository.Repository

class BookingViewModel private constructor(private val repository: Repository) : ViewModel() {
    companion object {
        @Volatile
        private var INSTANCE: BookingViewModel? = null

        @JvmStatic
        fun getInstance(): BookingViewModel =
            INSTANCE ?: synchronized(this) {
                INSTANCE = BookingViewModel(Repository.getInstance())
                INSTANCE!!
            }
    }

    private val query = MutableLiveData<BookingQuery>()
    private val booking: LiveData<Booking> = Transformations.switchMap(query) {
        repository.getBooking(it.uid, it.id)
    }

    fun postBooking(booking: Booking) =
        repository.postBooking(booking)

    fun getBooking(): LiveData<Booking> =
        booking

    fun setBooking(bookingQuery: BookingQuery) {
        query.postValue(bookingQuery)
    }

    fun patchBooking(booking: Booking) =
        repository.patchBooking(booking)

    fun getMyBooking(uid: String, isDone: Boolean) =
        repository.getMyBooking(uid, isDone)

}