package com.example.carrental.ui.carsinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.database.CustomerWithRent
import com.example.carrental.databinding.RecyclerViewCarInfoItemBinding

class WhoRentedTheCarAdapter() :
    ListAdapter<CustomerWithRent, WhoRentedTheCarAdapter.ViewHolder>(CarsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val res = holder.itemView.context.resources
        holder.bind(getItem(position)!!)
    }

    class ViewHolder private constructor(val binding: RecyclerViewCarInfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
//            clickListener: CarListener,
            item: CustomerWithRent
        ) {
            binding.customerWithRent = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                val binding = RecyclerViewCarInfoItemBinding.inflate(view, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class CarsDiffCallback :
        DiffUtil.ItemCallback<CustomerWithRent>() {
        override fun areItemsTheSame(oldItem: CustomerWithRent, newItem: CustomerWithRent): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CustomerWithRent, newItem: CustomerWithRent): Boolean {
            return oldItem == newItem
        }

    }

//    class CarListener(val clickListener: (carId: Long) -> Unit) {
//        fun onClick(car: CustomerWithRent) = clickListener(car.carId)
//    }
}