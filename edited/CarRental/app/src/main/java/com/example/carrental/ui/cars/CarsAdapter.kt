package com.example.carrental.ui.cars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.database.Cars
import com.example.carrental.databinding.RecyclerViewCarsItemBinding

class CarsAdapter(val clickListener: CarListener,val clickListener2: CarListener) :
    ListAdapter<Cars, CarsAdapter.ViewHolder>(CarsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val res = holder.itemView.context.resources
        holder.bind(clickListener,clickListener2, getItem(position)!!)
    }

    class ViewHolder private constructor(val binding: RecyclerViewCarsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            clickListener: CarListener,
            clickListener2: CarListener,
            item: Cars
        ) {
            binding.car = item
            binding.carClickListener = clickListener
            binding.carClickListener2 = clickListener2
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                val binding = RecyclerViewCarsItemBinding.inflate(view, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class CarsDiffCallback :
        DiffUtil.ItemCallback<Cars>() {
        override fun areItemsTheSame(oldItem: Cars, newItem: Cars): Boolean {
            return oldItem.carId == newItem.carId
        }

        override fun areContentsTheSame(oldItem: Cars, newItem: Cars): Boolean {
            return oldItem == newItem
        }

    }

    class CarListener(val clickListener: (carId: Long) -> Unit) {
        fun onClick(car: Cars) = clickListener(car.carId)
    }


}