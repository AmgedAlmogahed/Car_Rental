package com.example.carrental.ui.customersInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.database.CarsWithRent
import com.example.carrental.databinding.RecyclerViewCustomerInfoItemBinding

class RentedCarsAdapter() :
    ListAdapter<CarsWithRent, RentedCarsAdapter.ViewHolder>(CarsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val res = holder.itemView.context.resources
        holder.bind(getItem(position)!!)
    }

    class ViewHolder private constructor(val binding: RecyclerViewCustomerInfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
//            clickListener: CarListener,
            item: CarsWithRent
        ) {
            binding.viewModel = RentedCarsViewModel(item)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                val binding = RecyclerViewCustomerInfoItemBinding.inflate(view, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class CarsDiffCallback :
        DiffUtil.ItemCallback<CarsWithRent>() {
        override fun areItemsTheSame(oldItem: CarsWithRent, newItem: CarsWithRent): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CarsWithRent, newItem: CarsWithRent): Boolean {
            return oldItem == newItem
        }

    }

//    class CarListener(val clickListener: (carId: Long) -> Unit) {
//        fun onClick(car: CarsWithRent) = clickListener(car.carId)
//    }
}