package com.example.carrental.ui.lesingInfo

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
import com.example.carrental.databinding.FragmentLeasingInfoBinding
import com.google.android.material.snackbar.Snackbar


class LeasingInfoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLeasingInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_leasing_info, container, false)

        val arguments = LeasingInfoFragmentArgs.fromBundle(requireArguments())

        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).rentingDao

        val viewModelFactory = LeasingInfoViewModelFactory(arguments.customerId,arguments.carId,dataSource, application)

        val leasingInfoViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(LeasingInfoViewModel::class.java)

        binding.viewModel = leasingInfoViewModel

        binding.lifecycleOwner = this


        leasingInfoViewModel.snackbarText.observe(viewLifecycleOwner, Observer { text ->
            if (text != null) {
                Snackbar.make(binding.leasingInfoView, text, Snackbar.LENGTH_LONG).show()
                leasingInfoViewModel.setSnackEmpty()
            }
        })

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        leasingInfoViewModel.navigateToCars.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    LeasingInfoFragmentDirections.actionLeasingInfoToNavigationCar()
                )
//                 Reset state to make sure we only navigate once, even if the device
//                 has a configuration change.
                leasingInfoViewModel.doneNavigatingToCars()
            }
        })

        binding.chooseCustomer.setOnClickListener {
            this.findNavController().navigate(
                LeasingInfoFragmentDirections.actionLeasingInfoToNavigationCustomers(1,arguments.carId)
            )

        }

        leasingInfoViewModel.name.observe(viewLifecycleOwner, Observer { name ->
            binding.customerNameEditText.text = name
        })





        return binding.root
    }


}
