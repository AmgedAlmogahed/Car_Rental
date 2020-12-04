package com.example.carrental.ui.customers

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carrental.database.CustomersDao
import java.lang.IllegalArgumentException

class CustomerViewModelFactory(
    private val database: CustomersDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerViewModel::class.java)){
            return CustomerViewModel(database,application) as T
        }
        throw IllegalArgumentException("UnKnown ViewModel Class")
    }

}