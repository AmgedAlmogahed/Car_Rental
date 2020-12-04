package com.example.carrental.ui.customers

import android.app.Application
import androidx.lifecycle.*
import com.example.carrental.database.Customers
import com.example.carrental.database.CustomersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomerViewModel(val database: CustomersDao, application: Application) :
    AndroidViewModel(application) {

    val customers = database.getAllCustomers()

    var name :String? = ""


//init {
//    getName()
//}
//    fun getName() {
//       viewModelScope.launch {
//           val customer = database.get(customerId) ?: return@launch
//           name = customer.name
//       }
//
//    }

    private val _navigateToCustomersInfo = MutableLiveData<Long>()
    val navigateToCustomersInfo : LiveData<Long>
        get() = _navigateToCustomersInfo

    fun onCarClicked(id: Long) {
        _navigateToCustomersInfo.value = id
    }

    fun onCarLeasingInfoNavigated() {
        _navigateToCustomersInfo.value = null
    }

}