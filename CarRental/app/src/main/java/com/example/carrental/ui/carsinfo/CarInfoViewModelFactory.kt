package com.example.carrental.ui.carsinfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carrental.database.CarsDao
import java.lang.IllegalArgumentException

data class CarInfoViewModelFactory(
    private val database: CarsDao,
    private val application: Application,
    private val carId : Long
) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarInfoViewModel::class.java)) {
            return CarInfoViewModel(database,application,carId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}