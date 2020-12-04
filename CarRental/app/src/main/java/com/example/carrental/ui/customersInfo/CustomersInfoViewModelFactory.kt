package com.example.carrental.ui.customersInfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carrental.database.CustomerWithRent
import com.example.carrental.database.CustomersDao
import com.example.carrental.database.RentingDao
import com.example.carrental.ui.customers.CustomerViewModel
import java.lang.IllegalArgumentException

data class CustomersInfoViewModelFactory(
    private val database: CustomersDao,
    private val application: Application,
    private val customerId : Long
) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomersInfoViewModel::class.java)) {
            return CustomersInfoViewModel(database,application,customerId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}