package com.saiful.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saiful.cricketapp.R
import com.saiful.cricketapp.model.BowlingCareer

class CareerBowlingAdapter(private val bowlingCareer: List<BowlingCareer>) :
    RecyclerView.Adapter<CareerBowlingAdapter.CareerBowlingViewHolder>() {
    class CareerBowlingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bowlingTitle: TextView = view.findViewById(R.id.bowling_title)
        val bowlingAverage: TextView = view.findViewById(R.id.bowling_average)
        val bowlingEconRate: TextView = view.findViewById(R.id.bowling_econ_rate)
        val bowlingFiveWickets: TextView = view.findViewById(R.id.bowling_five_wickets)
        val bowlingFourWickets: TextView = view.findViewById(R.id.bowling_four_wickets)
        val bowlingInnings: TextView = view.findViewById(R.id.bowling_innings)
        val bowlingMatches: TextView = view.findViewById(R.id.bowling_matches)
        val bowlingMedians: TextView = view.findViewById(R.id.bowling_medians)
        val bowlingNoBall: TextView = view.findViewById(R.id.bowling_no_ball)
        val bowlingOvers: TextView = view.findViewById(R.id.bowling_overs)
        val bowlingRate: TextView = view.findViewById(R.id.bowling_rate)
        val bowlingRun: TextView = view.findViewById(R.id.bowling_run)
        val bowlingStrikeRate: TextView = view.findViewById(R.id.bowling_strike_rate)
        val bowlingTenWickets: TextView = view.findViewById(R.id.bowling_ten_wickets)
        val bowlingWickets: TextView = view.findViewById(R.id.bowling_wickets)
        val bowlingWide: TextView = view.findViewById(R.id.bowling_wide)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareerBowlingViewHolder {
        return CareerBowlingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bowling_career, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return bowlingCareer.size
    }

    override fun onBindViewHolder(holder: CareerBowlingViewHolder, position: Int) {
        val bowler = bowlingCareer[position]

        holder.bowlingTitle.text = bowler.careerType
        holder.bowlingAverage.text = bowler.average.toString()
        holder.bowlingEconRate.text = bowler.econRate.toString()
        holder.bowlingFiveWickets.text = bowler.fiveWickets.toString()
        holder.bowlingFourWickets.text = bowler.fourWickets.toString()
        holder.bowlingInnings.text = bowler.innings.toString()
        holder.bowlingMatches.text = bowler.matches.toString()
        holder.bowlingMedians.text = bowler.medians.toString()
        holder.bowlingNoBall.text = bowler.noball.toString()
        holder.bowlingOvers.text = bowler.overs.toString()
        holder.bowlingRate.text = bowler.rate.toString()
        holder.bowlingRun.text = bowler.runs.toString()
        holder.bowlingStrikeRate.text = bowler.strikeRate.toString()
        holder.bowlingTenWickets.text = bowler.tenWickets.toString()
        holder.bowlingWickets.text = bowler.wickets.toString()
        holder.bowlingWide.text = bowler.wide.toString()
    }
}