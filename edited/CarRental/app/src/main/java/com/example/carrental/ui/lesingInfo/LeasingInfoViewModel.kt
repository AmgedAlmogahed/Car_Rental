package com.example.carrental.ui.lesingInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.carrental.database.*
import com.example.carrental.ui.RENTED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.util.*

class LeasingInfoViewModel(
    private val CustomerId: Long = 0L, private val CarId: Long = 0L,
    private val database: RentingDao,
    application: Application
) : AndroidViewModel(application) {


    private val _name =MutableLiveData<String>()
    val name : LiveData<String>
        get() = _name
    // Two-way databinding, exposing MutableLiveData
    val startDate = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val endDate = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val price = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val moveAgent = MutableLiveData<String>()

    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText

    /**
     * Variable that tells the fragment whether it should navigate to [CarsFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToCars = MutableLiveData<Boolean?>()

    init {
        getCustomerName()
    }
    /**
     * When true immediately navigate back to the [Cars]
     */
    val navigateToCars: LiveData<Boolean?>
        get() = _navigateToCars


    fun doneNavigatingToCars() {
        _navigateToCars.value = null
    }


    fun setSnackEmpty() {
        _snackbarText.value = null
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun insert(rent: Renting) {
        withContext(Dispatchers.IO) {
            database.insert(rent)
        }
    }


    private suspend fun updateCar(car: Cars) {
        withContext(Dispatchers.IO) {
            database.updateCar(car)
        }
    }


    private fun getCustomerName(){
            viewModelScope.launch {
                val customer = database.getCustomer(CustomerId) ?: return@launch
                _name.value = customer.name
            }
        }


    fun addCarToTheDatabase() {
//            val calendar: Calendar = Calendar.getInstance().calendarType
//            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh-mm-ss")
//            val dateTime = simpleDateFormat.format(calendar.time)

        val currentPrice = price.value
        val currentMoveAgent = moveAgent.value


        if (checkIfTextNull(
                CarId,
                CustomerId,
                currentMoveAgent
            )
        ) return


        val rent = Renting(
            currentMoveAgent,
            carId = CarId,
            customerId = CustomerId
        )



        viewModelScope.launch {
            insert(rent)
            
            val car =database.getCar(CarId) ?: return@launch
            car.status = RENTED
            
            updateCar(car)

            _navigateToCars.value = true
        }

    }


    private fun checkIfTextNull(
        carId: Long,
        customerId: Long,
        currentMoveAgent: String?
    ): Boolean {
        return when {
            customerId == 0L -> {
                _snackbarText.value = "You have to choose a customer"
                true
            }
            carId == 0L -> {
                _snackbarText.value = "You have to choose a Car"
                true
            }
            currentMoveAgent == null || currentMoveAgent.isEmpty() -> {
                _snackbarText.value = "Move Agent Can't be empty"
                true
            }

            else -> false
        }


    }
}