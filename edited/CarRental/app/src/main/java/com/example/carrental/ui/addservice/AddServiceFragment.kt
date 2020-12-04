package com.example.carrental.ui.addservice

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
import com.example.carrental.databinding.FragmentAddServiceBinding
import com.google.android.material.snackbar.Snackbar

class AddServiceFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddServiceBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_service, container, false)

        val arguments = AddServiceFragmentArgs.fromBundle(requireArguments())

        val application = requireNotNull(this.activity).application

        val database = AppDatabase.getInstance(application).servicesDao

        val viewModelFactory = AddServiceViewModelFactory(database, application,arguments.carId)

        val addServiceViewModel =
            ViewModelProvider(this, viewModelFactory)
            .get(AddServiceViewModel::class.java)

        binding.addServiceViewModel = addServiceViewModel

        binding.lifecycleOwner = this

        addServiceViewModel.snackBarText.observe(viewLifecycleOwner, Observer { text ->
            if (text != null) {
                Snackbar.make(binding.addCustomerView, text, Snackbar.LENGTH_LONG).show()
                addServiceViewModel.setSnackEmpty()
            }
        })

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        addServiceViewModel.navigateToCars.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    AddServiceFragmentDirections.actionAddServiceToCarInfo(arguments.carId)
                )
//                 Reset state to make sure we only navigate once, even if the device
//                 has a configuration change.
                addServiceViewModel.doneNavigating()
            }
        })

        return binding.root
    }

}