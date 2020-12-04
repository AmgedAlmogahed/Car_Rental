package com.example.carrental.ui.addCar

import android.app.Application
import androidx.lifecycle.*
import com.example.carrental.database.Cars
import com.example.carrental.database.CarsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCarViewModel(
    val database: CarsDao,
    application: Application
) : AndroidViewModel(application) {


    // Two-way databinding, exposing MutableLiveData
    val carName = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val platNumber = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val baseNumber = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val model = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val type = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val rentPerHour = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val color = MutableLiveData<String>()

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> get() = _snackBarText


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

    fun setType(a: Int) {
        if (a == 0) {
            type.value = "Full Option"
        } else if (a == 1) {
            type.value = "Original"
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun update(car: Cars) {
        withContext(Dispatchers.IO) {
            database.update(car)
        }
    }

    private suspend fun insert(car: Cars) {
        withContext(Dispatchers.IO) {
            database.insert(car)
        }
    }

    fun addCarToTheDatabase() {


        val currentName = carName.value
        val currentPlatNumber = platNumber.value
        val currentBaseNumber = baseNumber.value
        val currentModel = model.value
        val currentType = type.value
        val currentRentPerHour = rentPerHour.value
        val currentColor = color.value

        if (checkIfTextNull(
                currentName,
                currentPlatNumber,
                currentBaseNumber,
                currentModel,
                currentType,
                currentRentPerHour,
                currentColor
            )
        ) return
        val car = Cars(
            currentName,
            currentPlatNumber,
            currentBaseNumber,
            currentModel,
            currentType,
            currentRentPerHour?.toLong(),
            currentColor
        )

        viewModelScope.launch {
            insert(car)

            _navigateToCars.value = true
        }

    }

    private fun checkIfTextNull(
        currentName: String?,
        currentPlatNumber: String?,
        currentBaseNumber: String?,
        currentModel: String?,
        currentType: String?,
        currentRentPerHour: String?,
        currentColor: String?
    ): Boolean {
        when {
            currentName == null || currentName.isEmpty() -> {
                _snackBarText.value = "Name Can't be empty"
                return true
            }
            currentBaseNumber == null || currentBaseNumber.isEmpty() -> {
                _snackBarText.value = "Base Number Can't be empty"
                return true
            }
            currentPlatNumber == null || currentPlatNumber.isEmpty() -> {
                _snackBarText.value = "Plat Number Can't be empty"
                return true
            }
            currentModel == null || currentModel.isEmpty() -> {
                _snackBarText.value = "Model Can't be empty"
                return true
            }
            currentType == null || currentType.isEmpty() -> {
                _snackBarText.value = "Type Can't be empty"
                return true
            }
            currentRentPerHour == null || currentRentPerHour.isEmpty() -> {
                _snackBarText.value = "RentPerHour Can't be empty"
                return true
            }
            currentColor == null || currentColor.isEmpty() -> {
                _snackBarText.value = "Color Can't be empty"
                return true
            }
            else -> return false
        }

    }


    fun setSnackEmpty() {
        _snackBarText.value = null
    }
}








