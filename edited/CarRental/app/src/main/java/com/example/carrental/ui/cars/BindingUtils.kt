package com.example.carrental.ui.cars

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.carrental.R
import com.example.carrental.database.Cars
import com.example.carrental.ui.RENTED
import com.example.carrental.ui.UNAVAILABLE

@BindingAdapter("carName")
fun TextView.setCarName(item: Cars) {
    item.let {
        text = item.name
    }
}

@BindingAdapter("carPlatNumber")
fun TextView.setPlatNumber(item: Cars) {
    item.let {
        text = item.platNumber
    }
}

@BindingAdapter("carBaseNumber")
fun TextView.setCarBaseNumber(item: Cars) {
    item.let {
        text = item.baseNumber
    }
}

@BindingAdapter("carModel")
fun TextView.setCarModel(item: Cars) {
    item.let {
        text = item.model
    }
}

@BindingAdapter("carType")
fun TextView.setCarType(item: Cars) {
    item.let {
        text = item.type
    }
}

@BindingAdapter("carColor")
fun TextView.setCarColor(item: Cars) {
    item.let {
        text = item.color
    }
}

@BindingAdapter("carPrice")
fun TextView.setCarPrice(item: Cars) {
    item.let {
        text = item.rentPerHour.toString()
    }
}

@BindingAdapter("carStatus")
fun TextView.setCarStatus(item: Cars) {
    item.let {
        text = item.status
    }
}

@BindingAdapter("set_background")
fun Button.setBackRound(item: Cars) {
    item.let {
        text = when (item.status) {
            RENTED -> {
                setBackgroundResource(R.drawable.red_gradient_cornered_background)
                context.getString(R.string.stop)
            }
            UNAVAILABLE -> {
                setBackgroundResource(R.drawable.black_gradient_cornered_background)
                UNAVAILABLE
            }
            else -> {
                setBackgroundResource(R.drawable.black_gradient_cornered_background)
                context.getString(R.string.leasing)
            }
        }

    }
}


