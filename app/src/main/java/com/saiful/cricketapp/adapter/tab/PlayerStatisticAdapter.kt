package com.saiful.cricketapp.adapter.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.saiful.cricketapp.model.tab.Tab
import com.saiful.cricketapp.ui.fragment.playerStatistic.BattingFragment
import com.saiful.cricketapp.ui.fragment.playerStatistic.BowlingFragment
import com.saiful.cricketapp.ui.fragment.playerStatistic.PlayerProfileFragment

class PlayerStatisticAdapter(manager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(manager, lifecycle) {

    companion object {
        val tabList = listOf(
            Tab(PlayerProfileFragment(), "Info"),
            Tab(BattingFragment(), "Batting"),
            Tab(BowlingFragment(), "Bowling")
        )
    }

    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabList[position].fragment
    }
}