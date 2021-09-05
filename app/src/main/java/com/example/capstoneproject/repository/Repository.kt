package com.example.capstoneproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.capstoneproject.firebase.FirebaseService
import com.example.capstoneproject.model.Booking
import com.example.capstoneproject.model.Mall

class Repository private constructor(private val firebaseService: FirebaseService) {

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        @JvmStatic
        fun getInstance(): Repository =
            INSTANCE ?: synchronized(this) {
                INSTANCE = Repository(FirebaseService.getInstance())
                INSTANCE!!
            }
    }

    fun getMalls(): LiveData<List<Mall>> =
        Transformations.map(firebaseService.getMalls()) { source ->
            val malls = mutableListOf<Mall>()
            source.forEach {
                malls.add(it.second.toMall(it.first))
            }
            malls
        }

    fun postMall(id: Int, mall: Mall) {
        firebaseService.postMall(id, mall)
    }

    fun getMall(id: Int): LiveData<Mall> =
        Transformations.map(firebaseService.getMall(id)) { source ->
            source.toMall(id)
        }

    fun postBooking(booking: Booking) =
        firebaseService.postBooking(booking)

    fun getBooking(uid: String, id: String): LiveData<Booking> =
        Transformations.map(firebaseService.getBooking(uid, id)){
            it.toBooking(id)
        }

    fun patchBooking(booking: Booking) =
        firebaseService.patchBooking(booking)

    fun getMyBooking(uid: String, isDone: Boolean): LiveData<List<Booking>> =
        Transformations.map(firebaseService.getMyBookings(uid, isDone)){
            val list = mutableListOf<Booking>()
            it.forEach { pair ->
                list.add(pair.second.toBooking(pair.first))
            }
            list
        }

    fun getMyInformation(uid: String) =
        firebaseService.getMyInformation(uid)

    fun searchMalls(query: String): LiveData<List<Mall>> =
        Transformations.map(firebaseService.searchMalls(query)){
            val list = mutableListOf<Mall>()
            it.forEach { pair ->
                list.add(pair.second.toMall(pair.first))
            }
            list
        }

}