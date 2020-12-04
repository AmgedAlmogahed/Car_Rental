package com.example.carrental.ui.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.carrental.database.RentingDao
import kotlinx.coroutines.launch

class StatisticsViewModel(val database: RentingDao, application: Application):AndroidViewModel(application){

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
        getStatistics()
    }
    private fun getStatistics() {
        viewModelScope.launch {
            val income = database.getIncome() ?: 0
            _income.value = income

            val outcome = database.getOutcome() ?: 0
            _outcome.value = outcome


            if (outcome == 0L) {
                _advantage.value = income
            } else {
                _advantage.value = income.minus(outcome)
            }

            if (income.minus(outcome) < 0L)
            {
                _advantageText.value = "Loss"
            }
        }
    }
}