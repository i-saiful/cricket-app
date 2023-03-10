package com.saiful.cricketapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.saiful.cricketapp.R
import com.saiful.cricketapp.adapter.tab.PlayerStatisticAdapter
import com.saiful.cricketapp.adapter.tab.PlayerStatisticAdapter.Companion.tabList
import com.saiful.cricketapp.databinding.FragmentPlayerInfoBinding
import com.saiful.cricketapp.model.PlayerInfo
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.PLAYER_ID
import com.saiful.cricketapp.viewmodels.CricketViewModel

class PlayerInfoFragment : Fragment() {
    private var _binding: FragmentPlayerInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CricketViewModel
    private var playerInfo: PlayerInfo? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlayerInfoBinding.inflate(inflater, container, false)
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
        val playerId = utilsSharedPreferences.getData(PLAYER_ID) ?: ""

        // title change
        (activity as AppCompatActivity).supportActionBar?.title =
            arguments?.getString("title") ?: "Player Profile"

        viewModel.playerInfo(playerId).observe(viewLifecycleOwner) {
            playerInfo = it
            playerInfo()
        }

        viewModel.countryId(playerId).observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                fetchCountry(playerId)
            }
        }

        val tabLayout = binding.tabLayoutPlayerInfo
        val viewPage = binding.viewPager2

        val tabAdapter = PlayerStatisticAdapter(childFragmentManager, lifecycle)
        viewPage.adapter = tabAdapter
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = tabList[position].title
        }.attach()

        // player avatar
        playerInfo()

        // Bottom navigation hide
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }

    private fun fetchCountry(playerId: String) {
        viewModel.playerCountryId(playerId).observe(viewLifecycleOwner) {
            viewModel.fetchCountryById(it.countryId)
        }
    }

    private fun playerInfo() {
        view?.let {
            Glide
                .with(it)
                .load(playerInfo?.image_path)
                .thumbnail(
                    Glide.with(it)
                        .load(R.drawable.player_avater)
                )
                .fitCenter()
                .into(binding.playerAvatar)
        }
        binding.playerName.text = playerInfo?.fullName ?: ""
        binding.playerPosition.text = "(${playerInfo?.position_name})"
    }

}