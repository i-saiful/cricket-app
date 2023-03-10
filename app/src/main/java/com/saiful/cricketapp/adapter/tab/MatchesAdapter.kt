package com.saiful.cricketapp.adapter.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.saiful.cricketapp.model.tab.Tab
import com.saiful.cricketapp.ui.fragment.matches.AbandonedFragment
import com.saiful.cricketapp.ui.fragment.matches.FinishedFragment
import com.saiful.cricketapp.ui.fragment.matches.UpcomingFragment

class MatchesAdapter(manager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(manager, lifecycle) {
    companion object {
        val tabList = listOf(
            Tab(UpcomingFragment(), "Upcoming"),
            Tab(FinishedFragment(), "Finished"),
            Tab(AbandonedFragment(), "Abandoned")
        )
    }

    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabList[position].fragment
    }
}