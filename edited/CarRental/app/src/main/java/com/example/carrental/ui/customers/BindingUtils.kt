package com.example.carrental.ui.customers

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.carrental.database.Customers

@BindingAdapter("customerName")
fun TextView.setCustomerName(item: Customers){
    item.let {
        text = item.name
    }
}

@BindingAdapter("passportNumber")
fun TextView.setPassportNumber(item: Customers){
    item.let {
        text = item.passportNumber.toString()
    }
}

//
//@BindingAdapter("carBaseNumber")
//fun TextView.setCustomerImage(item: Customers){
//    item.let {
//        text = item.customerImage
//    }
//}
