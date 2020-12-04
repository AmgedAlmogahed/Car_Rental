package com.example.carrental.ui.carsinfo

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.carrental.database.Cars
import com.example.carrental.database.CustomerWithRent
import java.text.SimpleDateFormat


private val dateFormat = SimpleDateFormat("yyyy/MMM/d hh:mm")

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("customerName")
fun TextView.setCarName(item: CustomerWithRent) {
    item.let {
        text = item.name
    }
}

@BindingAdapter("price")
fun TextView.setPrice(item: CustomerWithRent) {
    item.let {
        text = item.price
    }
}

@BindingAdapter("startDate")
fun TextView.setStartDate(item: CustomerWithRent) {
    item.let {
        text = dateFormat.format(item.startDate).toString()
    }
}

@BindingAdapter("endDate")
fun TextView.setEndDate(item: CustomerWithRent) {
    item.let {
        text = dateFormat.format(item.endDate).toString()
    }
}


