package com.example.carrental.ui.addcustomer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.carrental.R
import com.example.carrental.database.AppDatabase
import com.example.carrental.databinding.FragmentAddCustomerBinding
import com.google.android.material.snackbar.Snackbar

class AddCustomerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentAddCustomerBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_add_customer, container, false
        )

        val arguments =  AddCustomerFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).customersDao

        val viewModelFactory = AddCustomerViewModelFactory(dataSource,application)

        val addCustomerViewModel = ViewModelProvider(this,viewModelFactory).get(AddCustomerViewModel::class.java)

        binding.addCustomerViewModel = addCustomerViewModel

        binding.lifecycleOwner = this

        addCustomerViewModel.snackBarText.observe(viewLifecycleOwner, Observer { text ->
            if (text != null) {
                Snackbar.make(binding.addCustomerView, text, Snackbar.LENGTH_LONG).show()
                addCustomerViewModel.setSnackEmpty()
            }
        })

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        addCustomerViewModel.navigateToCustomers.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    AddCustomerFragmentDirections.actionRegisterCustomerToNavigationCustomers(arguments.destination,arguments.carId)
                )
//                 Reset state to make sure we only navigate once, even if the device
//                 has a configuration change.
                addCustomerViewModel.doneNavigating()
            }
        })



        return binding.root
    }


}