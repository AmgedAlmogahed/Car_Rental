package com.example.carrental.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.carrental.R
import com.example.carrental.database.AppDatabase
import com.example.carrental.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStatisticsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).rentingDao

        val viewModelFactory = StatisticsViewModelFactory(dataSource, application)

        val statisticsViewModel =
            ViewModelProvider(this, viewModelFactory
            ).get(StatisticsViewModel::class.java)
        
        binding.lifecycleOwner = this

        statisticsViewModel.income.observe(viewLifecycleOwner, Observer { income ->
            binding.income.text = income.toString()
        })

        statisticsViewModel.outcome.observe(viewLifecycleOwner, Observer { outcome ->
            binding.outcome.text = outcome.toString()
        })

        statisticsViewModel.advantage.observe(viewLifecycleOwner, Observer { advantage ->
            binding.advantage.text = advantage.toString()
        })

        statisticsViewModel.advantageText.observe(viewLifecycleOwner, Observer {advantage_text ->
            binding.advantageText.text = advantage_text
        })

        return binding.root
    }
}