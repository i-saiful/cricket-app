package com.saiful.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDivider
import com.saiful.cricketapp.R
import com.saiful.cricketapp.database.entity.ScoreboardsBatting
import kotlin.math.roundToLong

class ScoreboardsBattingAdapter(private val scoreboardsBatting: List<ScoreboardsBatting>) :
    RecyclerView.Adapter<ScoreboardsBattingAdapter.ScoreboardViewHolder>() {
    class ScoreboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val batsmanName: TextView = view.findViewById(R.id.batsman_name)
        val batsmanRun: TextView = view.findViewById(R.id.batsman_run)
        val batsmanBalls: TextView = view.findViewById(R.id.batsman_balls)
        val batsman4s: TextView = view.findViewById(R.id.batsman_4s)
        val batsman6s: TextView = view.findViewById(R.id.batsman_6s)
        val batsmanRate: TextView = view.findViewById(R.id.batsman_rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreboardViewHolder {
        return ScoreboardViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.batsman_scoreboards, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return scoreboardsBatting.size
    }

    override fun onBindViewHolder(holder: ScoreboardViewHolder, position: Int) {
        val batsman = scoreboardsBatting[position]

        holder.batsmanName.text = batsman.batsman_name
        holder.batsmanRun.text = batsman.score.toString()
        holder.batsmanBalls.text = batsman.ball.toString()
        holder.batsman4s.text = batsman.four_x.toString()
        holder.batsman6s.text = batsman.six_x.toString()
        holder.batsmanRate.text = batsman.rate.roundToLong().toString()

        if (position == scoreboardsBatting.size - 1) {
            holder.itemView.findViewById<MaterialDivider>(R.id.divider).visibility = View.GONE
        }
    }
}