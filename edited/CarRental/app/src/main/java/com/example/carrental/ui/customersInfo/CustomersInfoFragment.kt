package com.example.carrental.ui.customersInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.carrental.R
import com.example.carrental.database.AppDatabase
import com.example.carrental.databinding.FragmentCutomersInfoBinding
import com.example.carrental.ui.lesingInfo.LeasingInfoFragmentArgs

class CustomersInfoFragment : Fragment() {

    private lateinit var binding: FragmentCutomersInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCutomersInfoBinding.inflate(inflater, container, false)

        val arguments = CustomersInfoFragmentArgs.fromBundle(requireArguments())

        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).customersDao

        val viewModelFactory = CustomersInfoViewModelFactory(dataSource, application,arguments.customerId)

        val customersInfoViewModel =
            ViewModelProvider(this, viewModelFactory
            ).get(CustomersInfoViewModel::class.java)


        customersInfoViewModel.name.observe(viewLifecycleOwner, Observer { name ->
            binding.customerName.text = name
        })

        customersInfoViewModel.passport.observe(viewLifecycleOwner, Observer { passport ->
            binding.passportNunmber.text = passport
        })

        val adapter = RentedCarsAdapter()
        binding.rentedCarsAdapter.adapter = adapter

        customersInfoViewModel.rentedCars.observe(viewLifecycleOwner, Observer {
            it?.let {    if (it.isEmpty()) {
                binding.rentedCarsAdapter.visibility = View.GONE
                binding.isEmpty.visibility = View.VISIBLE
            } else {
                adapter.submitList(it)
                binding.rentedCarsAdapter.visibility = View.VISIBLE
                binding.isEmpty.visibility = View.GONE
            } }
        })
        return binding.root
    }


}