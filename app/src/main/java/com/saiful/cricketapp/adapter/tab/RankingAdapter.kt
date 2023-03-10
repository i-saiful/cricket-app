package com.saiful.cricketapp.adapter.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.saiful.cricketapp.model.tab.Tab
import com.saiful.cricketapp.ui.fragment.ranking.MenFragment
import com.saiful.cricketapp.ui.fragment.ranking.WomenFragment

class RankingAdapter(manager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(manager, lifecycle) {

    companion object {
        val tabList = listOf(
            Tab(MenFragment(), "Men"),
            Tab(WomenFragment(), "Women")
        )
    }

    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabList[position].fragment
    }
}