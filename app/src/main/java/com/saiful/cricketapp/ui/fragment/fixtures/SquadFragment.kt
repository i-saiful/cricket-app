package com.saiful.cricketapp.ui.fragment.fixtures

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.saiful.cricketapp.adapter.SquadPlayerAdapter
import com.saiful.cricketapp.databinding.FragmentSquadBinding
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant
import com.saiful.cricketapp.util.Constant.Companion.FIXTURES_ID
import com.saiful.cricketapp.viewmodels.CricketViewModel

class SquadFragment : Fragment() {
    private var _binding: FragmentSquadBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel
    private lateinit var utilsSharedPreferences: UtilsSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSquadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        utilsSharedPreferences = UtilsSharedPreferences(requireContext())
    }

    override fun onResume() {
        super.onResume()

        val fixturesId = utilsSharedPreferences.getData(FIXTURES_ID) ?: ""
        val localTeamId = utilsSharedPreferences.getData(Constant.LOCAL_TEAM_ID) ?: ""
        val visitorTeamId = utilsSharedPreferences.getData(Constant.VISITOR_TEAM_ID) ?: ""
        Log.d("TAG", "squad fragment: $fixturesId");
        Log.d("TAG", "squad local team: $localTeamId");
        Log.d("TAG", "squad visitor team: $visitorTeamId");

        viewModel.getLocalTeamSquad(fixturesId, localTeamId).observe(viewLifecycleOwner) {
            Log.d("TAG", "Local team squad successfully fetched. Total players: ${it.size}");
            // Recycler
            val recycler = binding.localTeamRecycler
            recycler.setHasFixedSize(true)
            recycler.adapter = SquadPlayerAdapter(it)
        }

        viewModel.getVisitorTeamSquad(fixturesId, visitorTeamId).observe(viewLifecycleOwner) {
            Log.d("TAG", "visitor team squad successfully fetched. Total players: ${it.size}")
            // Recycler
            val recycler = binding.visitorTeamRecycler
            recycler.setHasFixedSize(true)
            recycler.adapter = SquadPlayerAdapter(it)
        }
    }
}