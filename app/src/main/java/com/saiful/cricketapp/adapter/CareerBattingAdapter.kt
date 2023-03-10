package com.saiful.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saiful.cricketapp.R
import com.saiful.cricketapp.model.BattingCareer

class CareerBattingAdapter(private val career: List<BattingCareer>) :
    RecyclerView.Adapter<CareerBattingAdapter.CareerBattingViewHolder>() {
    class CareerBattingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val battingTitle: TextView = view.findViewById(R.id.batting_title)
        val battingAverage: TextView = view.findViewById(R.id.batting_average)
        val battingBallsFaced: TextView = view.findViewById(R.id.batting_balls_faced)
        val battingFifties: TextView = view.findViewById(R.id.batting_fifties)
        val battingFours: TextView = view.findViewById(R.id.batting_four)
        val battingFowBalls: TextView = view.findViewById(R.id.batting_fow_balls)
        val battingFowScore: TextView = view.findViewById(R.id.batting_fow_score)
        val battingHighestInningScore: TextView =
            view.findViewById(R.id.batting_highest_inning_score)
        val battingInnings: TextView = view.findViewById(R.id.batting_innings)
        val battingHundreds: TextView = view.findViewById(R.id.batting_hundreds)
        val battingMatches: TextView = view.findViewById(R.id.batting_matches)
        val battingNotOuts: TextView = view.findViewById(R.id.batting_not_outs)
        val battingRunsScored: TextView = view.findViewById(R.id.batting_runs_scored)
        val battingSix: TextView = view.findViewById(R.id.batting_six)
        val battingStrikeRate: TextView = view.findViewById(R.id.batting_strike_rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareerBattingViewHolder {
        return CareerBattingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.batting_career, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return career.size
    }

    override fun onBindViewHolder(holder: CareerBattingViewHolder, position: Int) {
        val playerCareer = career[position]

        holder.battingTitle.text = playerCareer.careerType
        holder.battingAverage.text = playerCareer.average.toString()
        holder.battingBallsFaced.text = playerCareer.ballsFaced.toString()
        holder.battingFifties.text = playerCareer.fifties.toString()
        holder.battingFours.text = playerCareer.fourX.toString()
        holder.battingFowBalls.text = playerCareer.foWBalls.toString()
        holder.battingFowScore.text = playerCareer.foWScore.toString()
        holder.battingHighestInningScore.text = playerCareer.highestInningScore.toString()
        holder.battingInnings.text = playerCareer.innings.toString()
        holder.battingHundreds.text = playerCareer.hundreds.toString()
        holder.battingMatches.text = playerCareer.matches.toString()
        holder.battingNotOuts.text = playerCareer.notOuts.toString()
        holder.battingRunsScored.text = playerCareer.runsScored.toString()
        holder.battingSix.text = playerCareer.sixX.toString()
        holder.battingStrikeRate.text = playerCareer.strikeRate.toString()
    }
}