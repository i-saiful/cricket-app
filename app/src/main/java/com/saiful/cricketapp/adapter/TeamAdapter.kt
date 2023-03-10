package com.saiful.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.divider.MaterialDivider
import com.saiful.cricketapp.R
import com.saiful.cricketapp.database.entity.Teams
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.TEAM_ID

class TeamAdapter(private val teamsList: List<Teams>) :
    RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {
    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamName: TextView = view.findViewById(R.id.team_name)
        val teamImage: ImageView = view.findViewById(R.id.team_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_list, parent, false)
        return TeamViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return teamsList.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teamsList[position]
        holder.teamName.text = team.name
        holder.teamImage.contentDescription = team.name
        Glide
            .with(holder.itemView.context)
            .load(team.image_path)
            .fitCenter()
            .thumbnail(
                Glide.with(holder.itemView.context)
                    .load(R.drawable.flag_loading)
            )
            .into(holder.itemView.findViewById(R.id.team_image))
        holder.itemView.setOnClickListener {
            val utilsSharedPreferences = UtilsSharedPreferences(it.context)
            utilsSharedPreferences.saveData(TEAM_ID, team.id.toString())
            val bundle = bundleOf("title" to "${team.name} Matches")
            it.findNavController().navigate(R.id.teamMatchFragment, bundle)
        }

        if (position == teamsList.size - 1) {
            holder.itemView.findViewById<MaterialDivider>(R.id.divider).visibility = View.GONE
        }
    }
}