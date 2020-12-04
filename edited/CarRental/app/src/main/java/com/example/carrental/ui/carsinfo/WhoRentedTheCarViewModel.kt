package com.example.carrental.ui.carsinfo

import com.example.carrental.database.CustomerWithRent
import java.text.SimpleDateFormat


data class WhoRentedTheCarViewModel(val database: CustomerWithRent) {



    val carName get() = database.name
    val price get() = database.price
    val startDate = dateFormat.format(database.startDate).toString()
    val endDate = dateFormat.format(database.endDate).toString()

    companion object {
        private val dateFormat = SimpleDateFormat("yyyy/MMM/d hh:mm")
    }
}