package com.example.carrental.ui.addCar

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.carrental.R
import com.example.carrental.database.AppDatabase
import com.example.carrental.databinding.FragmentAddCarBinding
import com.example.carrental.ui.CAMERA_REQUEST
import com.example.carrental.ui.GALLERY_REQUEST
import com.google.android.material.snackbar.Snackbar


class AddCarFragment : Fragment() {

    lateinit var imageView: ImageView
    lateinit var application:Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentAddCarBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_car, container, false
        )

        imageView = binding.imageView

         application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).carsDao

        val viewModelFactory = AddCarViewModelFactory(dataSource, application)

        val addCarViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(AddCarViewModel::class.java)

        binding.addCarViewModel = addCarViewModel

        binding.lifecycleOwner = this



        ArrayAdapter.createFromResource(
            application,
            R.array.type_spinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.typeSpinner.adapter = adapter
        }

        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    addCarViewModel.setType(0)
                } else if (position == 1) {
                    addCarViewModel.setType(1)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        addCarViewModel.snackBarText.observe(viewLifecycleOwner, Observer { text ->
            if (text != null) {
                Snackbar.make(binding.addCarView, text, Snackbar.LENGTH_LONG).show()
                addCarViewModel.setSnackEmpty()
            }
        })

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        addCarViewModel.navigateToCars.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    AddCarFragmentDirections.actionAddCarToNavigationCar()
                )
//                 Reset state to make sure we only navigate once, even if the device
//                 has a configuration change.
                addCarViewModel.doneNavigating()
            }
        })


        binding.takeImage.setOnClickListener {
            val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")

            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Choose car picture").setIcon(R.drawable.ic_baseline_photo_camera_24)

            builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
                when {
                    options[item] == "Take Photo" -> {
                        takePicture()
                    }
                    options[item] == "Choose from Gallery" -> {
                        chooseFromGallery()
                    }
                    options[item] == "Cancel" -> {
                        dialog.dismiss()
                    }
                }
            })
            builder.show()        }


        return binding.root
    }

    private fun chooseFromGallery() {
        val pickPhoto =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, GALLERY_REQUEST)
    }

    private fun takePicture() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePicture, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                CAMERA_REQUEST -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = data.extras!!["data"] as Bitmap?
                    imageView.setImageBitmap(selectedImage)
                }
                GALLERY_REQUEST -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = data.data
                        imageView.setImageURI(selectedImage)
                    }
                }
            }
        }
    }



