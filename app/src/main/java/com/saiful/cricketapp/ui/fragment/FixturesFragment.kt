package com.saiful.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.saiful.cricketapp.R
import com.saiful.cricketapp.adapter.tab.MatchAdapter
import com.saiful.cricketapp.adapter.tab.MatchAdapter.Companion.tabList
import com.saiful.cricketapp.databinding.FragmentFixturesBinding
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant
import com.saiful.cricketapp.util.Constant.Companion.FIXTURES_ID
import com.saiful.cricketapp.viewmodels.CricketViewModel

class FixturesFragment : Fragment() {
    private var _binding: FragmentFixturesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFixturesBinding.inflate(inflater, container, false)
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
        val fixturesId = utilsSharedPreferences.getData(FIXTURES_ID) ?: ""

        // title change
        (activity as AppCompatActivity).supportActionBar?.title =
            arguments?.getString("title") ?: "Fixture"

//        viewModel.fetchFixturesById(fixturesId, view)
        viewModel.fixturesLineupCount(fixturesId).observe(viewLifecycleOwner) {
            Log.d("TAG", "Fixtures lineup successfully fetched. Total lineup: ${it.count}")
            if (it.count == 0) {
                viewModel.fetchFixturesById(fixturesId)
            }
        }

        // fixture team vs team
        viewModel.getFixtureTeamVsTeam(fixturesId).observe(viewLifecycleOwner) {
            binding.localTeamCode.text = it.localteam_name
            binding.visitorTeamCode.text = it.visitorteam_name


            it.localteam_overs?.let { _ ->
                binding.localTeamRunWicket.text = getString(
                    R.string.run_wicket,
                    it.localteam_score.toString(),
                    it.localteam_wickets.toString()
                )
                binding.localTeamOver.text =
                    getString(R.string.match_over, it.localteam_overs.toString())
            } ?: run {
                binding.layoutLocalTeamRunWicketOver.visibility = View.GONE
            }

            it.visitorteam_overs?.let { _ ->
                binding.visitorTeamRunWicket.text = getString(
                    R.string.run_wicket,
                    it.visitorteam_score.toString(),
                    it.visitorteam_wickets.toString()
                )
                binding.visitorTeamOver.text =
                    getString(R.string.match_over, it.visitorteam_overs.toString())
            } ?: run {
                binding.layoutVisitorTeamRunWicketOver.visibility = View.GONE
            }

            Glide
                .with(requireContext())
                .load(it.localteam_image)
                .fitCenter()
                .thumbnail(
                    Glide.with(requireContext())
                        .load(R.drawable.flag_loading)
                )
                .into(binding.localTeamImage)

            Glide
                .with(requireContext())
                .load(it.visitorteam_image)
                .fitCenter()
                .thumbnail(
                    Glide.with(requireContext())
                        .load(R.drawable.flag_loading)
                )
                .into(binding.visitorTeamImage)
        }

        viewModel.getFixtureMatchInfo(fixturesId).observe(viewLifecycleOwner) {
            binding.layoutFixtureLocalTeam.setOnClickListener { _ ->
                utilsSharedPreferences.saveData(Constant.TEAM_ID, it.localTeamId)
                val bundle = bundleOf("title" to "${it.localTeamName} Matches")
                findNavController().navigate(R.id.teamMatchFragment, bundle)
            }

            binding.layoutFixtureVisitorTeam.setOnClickListener { _ ->
                utilsSharedPreferences.saveData(Constant.TEAM_ID, it.visitorTeamId)
                val bundle = bundleOf("title" to "${it.visitorTeamName} Matches")
                findNavController().navigate(R.id.teamMatchFragment, bundle)
            }
        }

        val tabLayout = binding.tabLayoutMatch
        val viewPage = binding.viewPager2

        val tabAdapter = MatchAdapter(childFragmentManager, lifecycle)
        viewPage.adapter = tabAdapter
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = tabList[position].title
        }.attach()

        // Bottom navigation hide
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }
}