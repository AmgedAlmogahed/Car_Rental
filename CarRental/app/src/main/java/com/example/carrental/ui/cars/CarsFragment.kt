package com.example.carrental.ui.cars

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.R
import com.example.carrental.database.AppDatabase
import com.example.carrental.databinding.FragmentCarsBinding
import kotlinx.android.synthetic.main.recycler_view_cars_item.*


class CarsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCarsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cars, container, false
        )
        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).carsDao

        val viewModelFactory = CarsViewModelFactory(dataSource, application)

        val carViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(CarsViewModel::class.java)

        binding.carViewModel = carViewModel

        binding.lifecycleOwner = this

        val adapter = CarsAdapter(
            CarsAdapter.CarListener { carId ->
            carViewModel.onCarClicked(carId)
        }, CarsAdapter.CarListener { carId ->
            carViewModel.onCarClicked2(carId)
        })
        binding.carsList.adapter = adapter

        carViewModel.showToast.observe(viewLifecycleOwner, Observer { t ->
           if(t != null) {
               Toast.makeText(application, t, Toast.LENGTH_LONG).show()
               carViewModel.onCarShowToastFinished()
           }
        })

        carViewModel.showDialog.observe(viewLifecycleOwner, Observer {car ->
            car?.let{
                val builder = AlertDialog.Builder(requireActivity())
                val dialogLayout = inflater.inflate(R.layout.alert_dialog_with_edittext, null)
                val editText = dialogLayout.findViewById<EditText>(R.id.editText)

                builder.setTitle("Alert")
                builder.setMessage("Are you sure you want to stop the leasing")
                builder.setView(dialogLayout)
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    val price = editText.text.toString()
                    if (price == null || price.isEmpty()) {
                        Toast.makeText(
                            context,
                            "You have to enter a price", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        carViewModel.updateStatusAndDate(car, price)
                        Toast.makeText(
                            application,
                            "car is back now", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(application,
                        android.R.string.no, Toast.LENGTH_SHORT).show()
                }
                builder.show()

                carViewModel.onCarRentedFinished()
            }

        })

        carViewModel.navigateToLeasingInfo.observe(viewLifecycleOwner, Observer { car ->
            car?.let {
                    this.findNavController().navigate(
                        CarsFragmentDirections.actionNavigationCarToLeasingInfo(car, 0)
                    )
                carViewModel.onCarLeasingInfoNavigated()
            }
        })

        carViewModel.navigateToCarInfo.observe(viewLifecycleOwner, Observer { car ->
            car?.let {
                this.findNavController().navigate(
                    CarsFragmentDirections.actionNavigationCarToCarStatistics(car)
                )
                carViewModel.onCariInfoNavigated()
            }
        })

        carViewModel.cars.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    adapter.submitList(it)
                    binding.carsList.visibility = View.VISIBLE
                    binding.isEmpty.visibility = View.GONE
                } else {
                    binding.carsList.visibility = View.GONE
                    binding.isEmpty.visibility = View.VISIBLE
                }
            }
        })

        binding.floatingAdd.setOnClickListener {
            this.findNavController().navigate(
                CarsFragmentDirections.actionNavigationCarToAddCar()
            )
        }

        return binding.root
    }


}


//        val itemTouchHelperCallback =
//            object :
//                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//
//                    return false
//                }
//
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    carViewModel.delete(adapter.getItemId(viewHolder.adapterPosition))
//                    Toast.makeText(
//                        application,
//                        getString(R.string.car_deleted),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//            }

//        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
//        itemTouchHelper.attachToRecyclerView(binding.carsList)