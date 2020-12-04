package com.example.carrental.ui.customersInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.carrental.database.*
import kotlinx.coroutines.launch

class CustomersInfoViewModel(
    val database: CustomersDao,
    application: Application,
    val customerId: Long
) :
    AndroidViewModel(application) {
    val rentedCars: LiveData<List<CarsWithRent>> = database.getRentingWithCars(customerId)


    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _passport = MutableLiveData<String>()
    val passport: LiveData<String>
        get() = _passport

    init {
        sendNameAndPassportNumber()
    }

    private fun sendNameAndPassportNumber() {
        viewModelScope.launch {
            val customer = database.get(customerId) ?: return@launch
            _name.value = customer.name
            _passport.value = customer.passportNumber
        }
    }


}