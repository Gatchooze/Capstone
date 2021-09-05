package com.example.capstoneproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.capstoneproject.repository.Repository

class AccountViewModel private constructor(private val repository: Repository): ViewModel() {

    companion object{
        @Volatile
        private var INSTANCE: AccountViewModel? = null

        @JvmStatic
        fun getInstance(): AccountViewModel =
            INSTANCE ?: synchronized(this){
                INSTANCE = AccountViewModel(Repository.getInstance())
                INSTANCE!!
            }
    }

    fun getMyInformation(uid: String) =
        repository.getMyInformation(uid)

}