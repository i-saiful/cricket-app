package com.saiful.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDivider
import com.saiful.cricketapp.R
import com.saiful.cricketapp.database.entity.ScoreboardsBowling
import kotlin.math.roundToLong

class ScoreboardsBowlerAdapter(private val scoreboardsBowling: List<ScoreboardsBowling>) :
    RecyclerView.Adapter<ScoreboardsBowlerAdapter.ScoreboardBowlerViewHolder>() {
    class ScoreboardBowlerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bowlerName: TextView = view.findViewById(R.id.bowler_name)
        val bowlerOver: TextView = view.findViewById(R.id.bowler_over)
        val bowlerMedians: TextView = view.findViewById(R.id.bowler_medians)
        val bowlerRun: TextView = view.findViewById(R.id.bowler_run)
        val bowlerWickets: TextView = view.findViewById(R.id.bowler_wickets)
        val bowlerWide: TextView = view.findViewById(R.id.bowler_wide)
        val bowlerNoBall: TextView = view.findViewById(R.id.bowler_noball)
        val bowlerRate: TextView = view.findViewById(R.id.bowler_rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreboardBowlerViewHolder {
        return ScoreboardBowlerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bowler_scoreboards, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return scoreboardsBowling.size
    }

    override fun onBindViewHolder(holder: ScoreboardBowlerViewHolder, position: Int) {
        val bowler = scoreboardsBowling[position]

        holder.bowlerName.text = bowler.bowler_name
        holder.bowlerOver.text = bowler.overs.toString()
        holder.bowlerMedians.text = bowler.medians.toString()
        holder.bowlerRun.text = bowler.runs.toString()
        holder.bowlerWickets.text = bowler.wickets.toString()
        holder.bowlerWide.text = bowler.wickets.toString()
        holder.bowlerNoBall.text = bowler.noball.toString()
        holder.bowlerRate.text = bowler.rate.roundToLong().toString()

        if (position == scoreboardsBowling.size - 1) {
            holder.itemView.findViewById<MaterialDivider>(R.id.divider).visibility = View.GONE
        }
    }
}