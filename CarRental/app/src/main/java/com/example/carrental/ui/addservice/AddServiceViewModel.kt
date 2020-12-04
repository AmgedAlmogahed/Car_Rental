package com.example.carrental.ui.addservice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.carrental.database.Cars
import com.example.carrental.database.Services
import com.example.carrental.database.ServicesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddServiceViewModel(val database: ServicesDao, application: Application,val carId : Long ) :
    AndroidViewModel(application) {

    // Two-way databinding, exposing MutableLiveData
    val serviceType = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val price = MutableLiveData<String>()

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String>
        get() = _snackBarText


    /**
     * Variable that tells the fragment whether it should navigate to [CarsFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToCars = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [Cars]
     */
    val navigateToCars: LiveData<Boolean?>
        get() = _navigateToCars


    /**
     * Call this immediately after navigating to [CarsFragment]
     */
    fun doneNavigating() {
        _navigateToCars.value = null
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun update(service: Services) {
        withContext(Dispatchers.IO) {
            database.update(service)
        }
    }

    private suspend fun insert(service: Services) {
        withContext(Dispatchers.IO) {
            database.insert(service)
        }
    }

    fun addServiceToTheDatabase() {


        val currentServiceType = serviceType.value
        val currentPrice = price.value


        if (checkIfTextNull(
                currentServiceType,
                currentPrice
            )
        ) return

        val service = Services(
            currentServiceType,
            currentPrice?.toInt(),
            carId = carId
        )

        viewModelScope.launch {
            insert(service)

            _navigateToCars.value = true
        }

    }

    private fun checkIfTextNull(
        currentServiceType: String?,
        currentPrice: String?
    ): Boolean {
        return when {
            currentServiceType == null || currentServiceType.isEmpty() -> {
                _snackBarText.value = "Type Can't be empty"
                true
            }
            currentPrice == null || currentPrice.isEmpty() -> {
                _snackBarText.value = "Price Can't be empty"
                true
            }
            else -> false
        }

    }


    fun setSnackEmpty() {
        _snackBarText.value = null
    }
}