package com.saiful.cricketapp.ui.fragment.matches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.saiful.cricketapp.adapter.FixturesMatchAdapter
import com.saiful.cricketapp.databinding.FragmentAbandonedBinding
import com.saiful.cricketapp.util.Constant.Companion.MATCH_STATUS_ABANDONED
import com.saiful.cricketapp.viewmodels.CricketViewModel

class AbandonedFragment : Fragment() {
    private var _binding: FragmentAbandonedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAbandonedBinding.inflate(inflater, container, false)
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
        viewModel.fixtureMatches(MATCH_STATUS_ABANDONED).observe(viewLifecycleOwner) {
            Log.d("TAG", "Found! Abandoned ${it.size} matches");
            recycler.adapter = FixturesMatchAdapter(it)
        }
    }
}