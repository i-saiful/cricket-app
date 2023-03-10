package com.saiful.cricketapp.ui.fragment.matches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.saiful.cricketapp.adapter.FixturesMatchAdapter
import com.saiful.cricketapp.databinding.FragmentUpcomingBinding
import com.saiful.cricketapp.util.Constant.Companion.MATCH_STATUS_UPCOMING
import com.saiful.cricketapp.viewmodels.CricketViewModel

class UpcomingFragment : Fragment() {
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

    }

    override fun onResume() {
        super.onResume()
        val recycler = binding.fixtureRecycler
        recycler.setHasFixedSize(true)
        viewModel.fixtureMatches(MATCH_STATUS_UPCOMING)
            .observe(viewLifecycleOwner) {
                Log.d("TAG", "Found! Upcoming ${it.size} matches");
                recycler.adapter = FixturesMatchAdapter(it)
            }
    }
}