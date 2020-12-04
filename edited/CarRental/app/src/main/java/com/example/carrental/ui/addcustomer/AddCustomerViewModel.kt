package com.example.carrental.ui.addcustomer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.carrental.database.Customers
import com.example.carrental.database.CustomersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCustomerViewModel(
    val database: CustomersDao,
    application: Application
) : AndroidViewModel(application) {

    // Two-way databinding, exposing MutableLiveData
    val customerName = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val passportNumber = MutableLiveData<String>()

//    // Two-way databinding, exposing MutableLiveData
//    val passport = MutableLiveData<String>()
//
//    // Two-way databinding, exposing MutableLiveData
//    val license = MutableLiveData<String>()
//
//    // Two-way databinding, exposing MutableLiveData
//    val image = MutableLiveData<String>()

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> get() = _snackBarText


    /**
     * Variable that tells the fragment whether it should navigate to [CustomerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToCustomers = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [Customers]
     */
    val navigateToCustomers: LiveData<Boolean?>
        get() = _navigateToCustomers


    /**
     * Call this immediately after navigating to [CustomerInfo]
     */
    fun doneNavigating() {
        _navigateToCustomers.value = null
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun update(customer: Customers) {
        withContext(Dispatchers.IO) {
            database.update(customer)
        }
    }

    private suspend fun insert(customer: Customers) {
        withContext(Dispatchers.IO) {
            database.insert(customer)
        }
    }

    fun addCustomerToTheDatabase() {

        val currentName = customerName.value
        val currentPassportNumber = passportNumber.value


        if (checkIfTextNull(
                currentName,
                currentPassportNumber
            )
        ) return
        val customer = Customers(
            currentName,
            currentPassportNumber
        )

        viewModelScope.launch {
            insert(customer)

            _navigateToCustomers.value = true
        }

    }

    private fun checkIfTextNull(
        currentName: String?,
        currentPassprotNumber: String?

    ): Boolean {
        when {
            currentName == null || currentName.isEmpty() -> {
                _snackBarText.value = "Name Can't be empty"
                return true
            }
            currentPassprotNumber == null || currentPassprotNumber.isEmpty() -> {
                _snackBarText.value = "Passport Number Can't be empty"
                return true
            }
            else -> return false
        }

    }


    fun setSnackEmpty() {
        _snackBarText.value = null
    }
}