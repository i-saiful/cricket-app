package com.saiful.cricketapp.adapter.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.saiful.cricketapp.model.tab.Tab
import com.saiful.cricketapp.ui.fragment.fixtures.CommentaryFragment
import com.saiful.cricketapp.ui.fragment.fixtures.FixturesInfoFragment
import com.saiful.cricketapp.ui.fragment.fixtures.ScoreboardsFragment
import com.saiful.cricketapp.ui.fragment.fixtures.SquadFragment

class MatchAdapter(manager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(manager, lifecycle) {
    companion object {
        val tabList = listOf(
            Tab(FixturesInfoFragment(), "Info"),
            Tab(SquadFragment(), "Squad"),
            Tab(ScoreboardsFragment(), "Scoreboards"),
            Tab(CommentaryFragment(), "Commentary")
        )
    }

    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabList[position].fragment
    }
}