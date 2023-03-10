package com.saiful.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saiful.cricketapp.R
import com.saiful.cricketapp.adapter.FixturesMatchAdapter
import com.saiful.cricketapp.databinding.FragmentLeaguesBinding
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.LEAGUE
import com.saiful.cricketapp.viewmodels.CricketViewModel

class LeaguesFragment : Fragment() {
    private var _binding: FragmentLeaguesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLeaguesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        val utilsSharedPreferences = UtilsSharedPreferences(requireContext())
        val leagueId = utilsSharedPreferences.getData(LEAGUE) ?: ""

        // title change
        (activity as AppCompatActivity).supportActionBar?.title =
            arguments?.getString("title") ?: "Leagues Matches"

        viewModel.getLeaguesMatches(leagueId).observe(viewLifecycleOwner) {
            Log.d("TAG", "Leagues matches successfully fetched. Total matches: ${it.size}")

            // recent matches
            val recycler = binding.leaguesRecycler
            recycler.setHasFixedSize(true)
            recycler.adapter = FixturesMatchAdapter(it)
        }

        // Bottom navigation hide
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }
}