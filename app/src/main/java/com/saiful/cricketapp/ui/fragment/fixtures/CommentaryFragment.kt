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
import com.saiful.cricketapp.adapter.CommentaryAdapter
import com.saiful.cricketapp.databinding.FragmentCommentaryBinding
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant
import com.saiful.cricketapp.util.Constant.Companion.LOCAL_TEAM
import com.saiful.cricketapp.util.Constant.Companion.VISITOR_TEAM
import com.saiful.cricketapp.viewmodels.CricketViewModel


class CommentaryFragment : Fragment() {
    private var _binding: FragmentCommentaryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel
    private lateinit var utilsSharedPreferences: UtilsSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommentaryBinding.inflate(inflater, container, false)
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
            binding.localTeam.text = it.localTeamName
            binding.visitorTeam.text = it.visitorTeamName
            teamCommentary(it.fixturesId.toString(), it.localTeamId)

            binding.localTeam.setOnClickListener { _ ->
                changeBackgroundColor(LOCAL_TEAM)
                teamCommentary(it.fixturesId.toString(), it.localTeamId)
            }

            binding.visitorTeam.setOnClickListener { _ ->
                changeBackgroundColor(VISITOR_TEAM)
                teamCommentary(it.fixturesId.toString(), it.visitorTeamId)
            }
        }

        changeBackgroundColor(LOCAL_TEAM)
    }

    private fun teamCommentary(fixturesId: String, teamId: String) {
        viewModel.getFixtureBalls(fixturesId, teamId).observe(viewLifecycleOwner) {
            Log.d("TAG", "Found! ${it.size} balls rows in this fixture")
            // Recycler
            val recycler = binding.commentaryRecycler
            recycler.setHasFixedSize(true)
            recycler.adapter = CommentaryAdapter(it)
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