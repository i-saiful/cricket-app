package com.saiful.cricketapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.saiful.cricketapp.R
import com.saiful.cricketapp.adapter.tab.RankingAdapter
import com.saiful.cricketapp.adapter.tab.RankingAdapter.Companion.tabList
import com.saiful.cricketapp.databinding.FragmentRankingBinding
import com.saiful.cricketapp.util.Constant.Companion.FETCH_FROM_INTERNET
import com.saiful.cricketapp.util.Constant.Companion.INTERNET_OFFLINE_MESSAGE
import com.saiful.cricketapp.util.Internet.Companion.isOnline
import com.saiful.cricketapp.viewmodels.CricketViewModel


class RankingFragment : Fragment() {
    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        // Tab layout
        val tabLayout = binding.tabLayoutRanking
        val viewPage = binding.viewPager2

        val tabAdapter = RankingAdapter(childFragmentManager, lifecycle)
        viewPage.adapter = tabAdapter
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = tabList[position].title
        }.attach()

        // Swipe to Refresh
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            Log.d("TAG", "User pull to refresh....")
            if (isOnline(requireContext())) {
                viewModel.fetchTeamsRanking()
                Snackbar.make(binding.root, FETCH_FROM_INTERNET, Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(binding.root, INTERNET_OFFLINE_MESSAGE, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.getRankingCount.observe(viewLifecycleOwner) {
            Log.d("TAG", "Ranking table has ${it.count} rows of data")
            if (it.count == 0) {
                viewModel.fetchTeamsRanking()
            }
        }

        // Bottom navigation hide
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }
}