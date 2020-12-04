package com.example.carrental.ui.lesingInfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carrental.database.RentingDao
import com.example.carrental.ui.addCar.AddCarViewModel

class LeasingInfoViewModelFactory(
    private val CustomerId: Long = 0L, private val CarId: Long = 0L,
    private val dataSource: RentingDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeasingInfoViewModel::class.java)) {
            return LeasingInfoViewModel(CustomerId, CarId, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}