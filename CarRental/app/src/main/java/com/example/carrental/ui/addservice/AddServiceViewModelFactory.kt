package com.example.carrental.ui.addservice

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carrental.database.ServicesDao
import java.lang.IllegalArgumentException

class AddServiceViewModelFactory(val database: ServicesDao, val application: Application,val carId : Long): ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddServiceViewModel::class.java)){
            return AddServiceViewModel(database, application,carId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}