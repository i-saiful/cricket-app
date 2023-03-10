package com.saiful.cricketapp.ui.fragment.fixtures

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.saiful.cricketapp.R
import com.saiful.cricketapp.adapter.ScoreboardsBattingAdapter
import com.saiful.cricketapp.adapter.ScoreboardsBowlerAdapter
import com.saiful.cricketapp.databinding.FragmentScoreboardsBinding
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant
import com.saiful.cricketapp.util.Constant.Companion.LOCAL_TEAM
import com.saiful.cricketapp.util.Constant.Companion.VISITOR_TEAM
import com.saiful.cricketapp.viewmodels.CricketViewModel

class ScoreboardsFragment : Fragment() {
    private var _binding: FragmentScoreboardsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel
    private lateinit var utilsSharedPreferences: UtilsSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScoreboardsBinding.inflate(inflater, container, false)
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
        val fixturesId = utilsSharedPreferences.getData(Constant.FIXTURES_ID) ?: ""
        viewModel.getFixtureMatchInfo(fixturesId).observe(viewLifecycleOwner) {
            scoreboardsBatting(it.fixturesId.toString(), it.localTeamId)
            scoreboardsBowling(it.fixturesId.toString(), it.visitorTeamId)

            binding.localTeam.text = it.localTeamName
            binding.visitorTeam.text = it.visitorTeamName

            // local team
            binding.localTeam.setOnClickListener { _ ->
                changeBackgroundColor(LOCAL_TEAM)
                scoreboardsBatting(it.fixturesId.toString(), it.localTeamId)
                scoreboardsBowling(it.fixturesId.toString(), it.visitorTeamId)
            }

            // visitor team
            binding.visitorTeam.setOnClickListener { _ ->
                changeBackgroundColor(VISITOR_TEAM)
                scoreboardsBatting(it.fixturesId.toString(), it.visitorTeamId)
                scoreboardsBowling(it.fixturesId.toString(), it.localTeamId)
            }
        }

        changeBackgroundColor(LOCAL_TEAM)
    }

    private fun scoreboardsBowling(fixturesId: String, teamId: String) {
        viewModel.getScoreboardsBowling(fixturesId, teamId).observe(viewLifecycleOwner) {
            Log.d("TAG", "Found! ${it.size} balls in this fixture scoreboard");

            // Recycler
            val recycler = binding.bowlerRecycler
            recycler.setHasFixedSize(true)
            recycler.adapter = ScoreboardsBowlerAdapter(it)
        }
    }

    private fun scoreboardsBatting(fixturesId: String, teamId: String) {
        viewModel.getScoreboardsBatting(fixturesId, teamId).observe(viewLifecycleOwner) {
            Log.d("TAG", "Found! ${it.size} batting in this fixture scoreboard")

            // Recycler
            val recycler = binding.batsmanRecycler
            recycler.setHasFixedSize(true)
            recycler.adapter = ScoreboardsBattingAdapter(it)
        }
    }

    // change background
    private fun changeBackgroundColor(team: String) {
        val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
        val colorPlum = ContextCompat.getColor(requireContext(), R.color.plum)
        val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
        when (team) {
            LOCAL_TEAM -> {
                binding.localTeam.setBackgroundColor(colorPlum)
                binding.localTeam.setTextColor(colorWhite)

                binding.visitorTeam.setBackgroundColor(colorWhite)
                binding.visitorTeam.setTextColor(colorBlack)
                binding.visitorTeam.setStrokeColorResource(R.color.plum)
            }
            else -> {
                binding.visitorTeam.setBackgroundColor(colorPlum)
                binding.visitorTeam.setTextColor(colorWhite)

                binding.localTeam.setBackgroundColor(colorWhite)
                binding.localTeam.setTextColor(colorBlack)
                binding.localTeam.setStrokeColorResource(R.color.plum)
            }
        }
    }
}