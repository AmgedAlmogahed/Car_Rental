package com.example.carrental.ui.carsinfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.carrental.database.*
import kotlinx.coroutines.launch

class CarInfoViewModel(
    val database: CarsDao,
    application: Application,
    val carId: Long
) :
    AndroidViewModel(application) {
    val whoRentedTheCar: LiveData<List<CustomerWithRent>> = database.getRentingWithCustomers(carId)


    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _baseNumber = MutableLiveData<String>()
    val baseNumber: LiveData<String>
        get() = _baseNumber

    private val _model = MutableLiveData<String>()
    val model: LiveData<String>
        get() = _model

    private val _type = MutableLiveData<String>()
    val type: LiveData<String>
        get() = _type

    private val _color = MutableLiveData<String>()
    val color: LiveData<String>
        get() = _color

    private val _income = MutableLiveData<Long>()
    val income: LiveData<Long>
        get() = _income

    private val _outcome = MutableLiveData<Long>()
    val outcome: LiveData<Long>
        get() = _outcome

    private val _advantage = MutableLiveData<Long>()
    val advantage: LiveData<Long>
        get() = _advantage

    private val _advantageText = MutableLiveData<String>()
    val advantageText: LiveData<String>
        get() = _advantageText



    init {
        sendNameAndPlatNumber()
        getCarStatistics()
    }


    private fun sendNameAndPlatNumber() {
        viewModelScope.launch {
            val car = database.get(carId) ?: return@launch
            _name.value = car.name
            _baseNumber.value = car.platNumber
            _model.value = car.model
            _type.value = car.type
            _color.value = car.color
        }

    }

    private fun getCarStatistics() {
        viewModelScope.launch {
            val income = database.getCarIncome(carId) ?: 0
            _income.value = income

            val outcome = database.getCarOutcome(carId) ?: 0
            _outcome.value = outcome


            if (outcome == 0L) {
                _advantage.value = income
            } else {
                _advantage.value = income.minus(outcome)
            }

            if (income.minus(outcome) < 0L)
            {
                _advantageText.value = "Loss"
            }else {
                _advantageText.value = "Advantage"
            }
        }
    }


}
//
//    private fun getAdvantage(){
//        _income.value -= _outcome.value
//
//        _advantage.value = _income.value * _outcome.value
//    }

