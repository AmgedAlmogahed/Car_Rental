package com.example.carrental.ui.addcustomer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carrental.database.CustomersDao
import java.lang.IllegalArgumentException

class AddCustomerViewModelFactory(
    private val database: CustomersDao,
    private val application: Application
) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCustomerViewModel::class.java)){
            return AddCustomerViewModel(database,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}