package com.example.carrental.ui.cars

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.carrental.database.Cars
import com.example.carrental.database.CarsDao
import com.example.carrental.database.Renting
import com.example.carrental.ui.AVAILABLE
import com.example.carrental.ui.RENTED
import com.example.carrental.ui.UNAVAILABLE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat


class CarsViewModel(
    val database: CarsDao,
    application: Application
) : AndroidViewModel(application) {


    val cars = database.getAllCars()

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    private val _navigateToLeasingInfo = MutableLiveData<Long>()
    val navigateToLeasingInfo: LiveData<Long>
        get() = _navigateToLeasingInfo

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String>
        get() = _showToast

    private val _showDialog = MutableLiveData<Long>()
    val showDialog: LiveData<Long>
        get() = _showDialog

    private val _navigateToCarInfo = MutableLiveData<Long>()
    val navigateToCarInfo: LiveData<Long>
        get() = _navigateToCarInfo

    fun onCarRentedFinished() {
        _showDialog.value = null
    }

    fun onCarShowToastFinished() {
        _showToast.value = null
    }

    fun onCarLeasingInfoNavigated() {
        _navigateToLeasingInfo.value = null
    }


    fun onCarClicked2(id: Long) {
        _navigateToCarInfo.value = id
    }

    fun onCariInfoNavigated() {
        _navigateToCarInfo.value = null
    }

//    private suspend fun clear(id : Long) {
//        withContext(Dispatchers.IO) {
//            database.deleteCar(id)
//        }
//    }

    private suspend fun update(car: Cars) {
        withContext(Dispatchers.IO) {
            database.update(car)
        }
    }

    private suspend fun updateRent(rent: Renting) {
        withContext(Dispatchers.IO) {
            database.updateRent(rent)
        }
    }

    private suspend fun insert(car: Cars) {
        withContext(Dispatchers.IO) {
            database.insert(car)
        }
    }

    fun onCarClicked(id: Long) {
        viewModelScope.launch {
            val car = database.get(id)

            when (car?.status) {
                RENTED -> {
                    _showDialog.value = id
                }
                UNAVAILABLE -> {
                    _showToast.value = "This Car Currently Unavailable And Can't be rented"
                }
                else -> {
                    _navigateToLeasingInfo.value = id
                }
            }
        }
    }

    fun updateStatusAndDate(id: Long,price: String) {
        viewModelScope.launch {
            val car = database.get(id) ?: return@launch
            car.status = AVAILABLE
            update(car)
            val rent = database.getRent(id) ?: return@launch

            rent.endDate = System.currentTimeMillis()

//            val end = dateFormat.format(rent.endDate)
//            val start =  dateFormat.format(rent.endDate)

//            val dateDifference =
//                getDateDiff(dateFormat, start, end).toInt()

            rent.price = price.toLong()

            updateRent(rent)




        }
    }

//    fun getDateDiff(format: SimpleDateFormat, oldDate: String?, newDate: String?): Long {
//        return try {
//            java.util.concurrent.TimeUnit.DAYS.convert(
//                format.parse(newDate).time - format.parse(oldDate).time,
//                java.util.concurrent.TimeUnit.MILLISECONDS
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//            0
//        }
//    }
//    fun delete(id: Long){
//        viewModelScope.launch { clear(id)}
//    }

}

//    // MutableLiveData is going to be used internally and LiveData is gonna be used out the class so that it can't be editable and reduce the bugs
//    private val _carName = MutableLiveData<String>()
//    val carName: LiveData<String>
//        get() = _carName
//
//    // MutableLiveData is going to be used internally and LiveData is gonna be used out the class so that it can't be editable and reduce the bugs
//    private val _platNumber = MutableLiveData<String>()
//    val platNumber: LiveData<String>
//        get() = _platNumber
//
//    // MutableLiveData is going to be used internally and LiveData is gonna be used out the class so that it can't be editable and reduce the bugs
//    private val _baseNumber = MutableLiveData<String>()
//    val baseNumber: LiveData<String>
//        get() = _baseNumber
//
//    // MutableLiveData is going to be used internally and LiveData is gonna be used out the class so that it can't be editable and reduce the bugs
//    private val _model = MutableLiveData<String>()
//    val model: LiveData<String>
//        get() = _model
//
//    // MutableLiveData is going to be used internally and LiveData is gonna be used out the class so that it can't be editable and reduce the bugs
//    private val _type = MutableLiveData<String>()
//    val type: LiveData<String>
//        get() = _type
//
//    // MutableLiveData is going to be used internally and LiveData is gonna be used out the class so that it can't be editable and reduce the bugs
//    private val _rentPerHour = MutableLiveData<Long>()
//    val rentPerHour: LiveData<Long>
//        get() = _rentPerHour
//
//    val rentString = Transformations.map(rentPerHour) { rent ->
//        rent.toString()
//    }
//
//    // MutableLiveData is going to be used internally and LiveData is gonna be used out the class so that it can't be editable and reduce the bugs
//    private val _color = MutableLiveData<String>()
//    val color: LiveData<String>
//        get() = _color
