package com.example.carrental.ui.carsinfo

import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.carrental.R
import com.example.carrental.database.AppDatabase
import com.example.carrental.databinding.FragmentCarInfoBinding

class CarInfoFragment : Fragment() {

    private lateinit var binding: FragmentCarInfoBinding
    private lateinit var arguments: CarInfoFragmentArgs
    private lateinit var application: Application
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCarInfoBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        arguments = CarInfoFragmentArgs.fromBundle(requireArguments())

        application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).carsDao

        val viewModelFactory = CarInfoViewModelFactory(dataSource, application, arguments.carId)

        val carInfoViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(CarInfoViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewmodel = carInfoViewModel


        val adapter = WhoRentedTheCarAdapter()
        binding.whoRentedTheCarAdapter.adapter = adapter


        carInfoViewModel.whoRentedTheCar.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isEmpty()) {
                    binding.whoRentedTheCarAdapter.visibility = View.GONE
                    binding.isEmpty.visibility = View.VISIBLE
                } else {
                    adapter.submitList(it)
                    binding.whoRentedTheCarAdapter.visibility = View.VISIBLE
                    binding.isEmpty.visibility = View.GONE
                }
            }
        })

            return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addService -> {
                this.findNavController().navigate(
                    CarInfoFragmentDirections.actionCarInfoToAddService(arguments.carId)
                )
                true
            }

            else -> return super.onOptionsItemSelected(item)

        }

    }
}