package com.example.carrental.ui.customers


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.database.Customers
import com.example.carrental.databinding.RecyclerViewCustomerItemBinding

class CustomersAdapter(val clickListener: CustomerListener) :
    ListAdapter<Customers, CustomersAdapter.ViewHolder>(CustomersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val res = holder.itemView.context.resources
        holder.bind(clickListener, getItem(position)!!)
    }

    class ViewHolder private constructor(val binding: RecyclerViewCustomerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            clickListener: CustomerListener,
            item: Customers
        ) {
            binding.customer= item
            binding.customerClickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                val binding = RecyclerViewCustomerItemBinding.inflate(view, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class CustomersDiffCallback :
        DiffUtil.ItemCallback<Customers>() {
        override fun areItemsTheSame(oldItem: Customers, newItem: Customers): Boolean {
            return oldItem.customerId == newItem.customerId
        }

        override fun areContentsTheSame(oldItem: Customers, newItem: Customers): Boolean {
            return oldItem == newItem
        }

    }

    class CustomerListener(val clickListener: (customerId: Long) -> Unit) {
        fun onClick(customer: Customers) = clickListener(customer.customerId)
    }
}