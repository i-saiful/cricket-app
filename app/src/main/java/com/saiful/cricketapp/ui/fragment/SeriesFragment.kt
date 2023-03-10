package com.saiful.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saiful.cricketapp.R
import com.saiful.cricketapp.adapter.SeriesAdapter
import com.saiful.cricketapp.databinding.FragmentSeriesBinding
import com.saiful.cricketapp.viewmodels.CricketViewModel

class SeriesFragment : Fragment() {
    private var _binding: FragmentSeriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        viewModel.getLeagues.observe(viewLifecycleOwner) {
            Log.d("TAG", "Leagues successfully fetched. Total leagues:  ${it.size}")

            // recent matches
            val recycler = binding.seriesRecycler
            recycler.setHasFixedSize(true)
            recycler.adapter = SeriesAdapter(it)
        }

        // Bottom navigation show
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }
}