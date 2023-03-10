package com.saiful.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDivider
import com.saiful.cricketapp.R
import com.saiful.cricketapp.database.entity.Ranking
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.TEAM_ID

class RankingAdapter(private val ranking: List<Ranking>) :
    RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {
    class RankingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rankPosition: TextView = view.findViewById(R.id.ranking_position)
        val rankName: TextView = view.findViewById(R.id.ranking_name)
        val rankMatches: TextView = view.findViewById(R.id.ranking_matches)
        val rankPoints: TextView = view.findViewById(R.id.ranking_points)
        val rankRating: TextView = view.findViewById(R.id.ranking_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.ranking_list, parent, false)
        return RankingViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return ranking.size
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val rank = ranking[position]

        holder.rankPosition.text = rank.position.toString()
        holder.rankName.text = rank.name
        holder.rankMatches.text = rank.matches.toString()
        holder.rankPoints.text = rank.points.toString()
        holder.rankRating.text = rank.rating.toString()
        Glide
            .with(holder.itemView.context)
            .load(rank.image_path)
            .fitCenter()
            .thumbnail(
                Glide.with(holder.itemView.context)
                    .load(R.drawable.flag_loading)
            )
            .into(holder.itemView.findViewById(R.id.ranking_image_path))

        // save team id in shared preferences
        holder.itemView.setOnClickListener {
            val utilsSharedPreferences = UtilsSharedPreferences(it.context)
            utilsSharedPreferences.saveData(TEAM_ID, rank.teamId.toString())
            val bundle = bundleOf("title" to "${rank.name} Matches")
            it.findNavController().navigate(R.id.teamMatchFragment, bundle)
        }

        if (position == ranking.size - 1) {
            holder.itemView.findViewById<MaterialDivider>(R.id.divider).visibility = View.GONE
        }
    }
}