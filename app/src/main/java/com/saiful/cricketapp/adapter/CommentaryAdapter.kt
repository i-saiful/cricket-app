package com.saiful.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saiful.cricketapp.R
import com.saiful.cricketapp.database.entity.Balls

class CommentaryAdapter(private val commentaryList: List<Balls>) :
    RecyclerView.Adapter<CommentaryAdapter.CommentaryViewHolder>() {
    class CommentaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ball: TextView = view.findViewById(R.id.commentary_ball)
        val run: TextView = view.findViewById(R.id.commenttary_run)
        val batsman: TextView = view.findViewById(R.id.commentary_batsman)
        val bowler: TextView = view.findViewById(R.id.commentary_bowler)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentaryViewHolder {
        return CommentaryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.commentary_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return commentaryList.size
    }

    override fun onBindViewHolder(holder: CommentaryViewHolder, position: Int) {
        val balls = commentaryList[position]
        holder.ball.text = balls.ball
        holder.run.text = balls.score
        holder.batsman.text = balls.batsman_name
        holder.bowler.text = balls.bowler_name
    }
}