package com.example.carrental.ui.customers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.carrental.R
import com.example.carrental.database.AppDatabase
import com.example.carrental.databinding.FragmentCustomersBinding
import com.example.carrental.ui.lesingInfo.LeasingInfoFragmentArgs


class CustomersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCustomersBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_customers, container, false
        )
        val arguments = CustomersFragmentArgs.fromBundle(requireArguments())

        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).customersDao

        val viewModelFactory = CustomerViewModelFactory(dataSource, application)

        val customerViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(CustomerViewModel::class.java)

        binding.customerViewModel = customerViewModel

        binding.lifecycleOwner = this

        val adapter = CustomersAdapter(CustomersAdapter.CustomerListener { customerId ->
            customerViewModel.onCarClicked(customerId)
        })
        binding.customerList.adapter = adapter


        customerViewModel.navigateToCustomersInfo.observe(viewLifecycleOwner, Observer { customer ->

            customer?.let {

                if (arguments.destination == 0) {
                    this.findNavController().navigate(
                        CustomersFragmentDirections.actionCustomersToCutomersInfo(customer)
                    )

                } else if (arguments.destination == 1) {

                    this.findNavController().navigate(
                        CustomersFragmentDirections.actionNavigationCustomersToLeasingInfo(
                            arguments.carId,
                            customer
                        )
                    )
                }
                customerViewModel.onCarLeasingInfoNavigated()
            }
        })

        customerViewModel.customers.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    adapter.submitList(it)
                    binding.customerList.visibility = View.VISIBLE
                    binding.isEmpty.visibility = View.GONE
                } else {
                    binding.customerList.visibility = View.GONE
                    binding.isEmpty.visibility = View.VISIBLE
                }
            }
        })

        binding.floatingActionButton.setOnClickListener {
            this.findNavController().navigate(
                CustomersFragmentDirections.actionNavigationCustomersToAddCustomer(
                    arguments.destination,
                    arguments.carId
                )
            )
        }
        return binding.root
    }
}