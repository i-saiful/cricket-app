package com.saiful.cricketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saiful.cricketapp.R
import com.saiful.cricketapp.database.entity.FixturesLineup
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.PLAYER_ID

class SquadPlayerAdapter(private val fixturesLineup: List<FixturesLineup>) :
    RecyclerView.Adapter<SquadPlayerAdapter.SquadPlayerViewHolder>() {
    class SquadPlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerName: TextView = view.findViewById(R.id.squad_player_name)
        val playerPosition: TextView = view.findViewById(R.id.squad_player_position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadPlayerViewHolder {
        return SquadPlayerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.squad_player, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return fixturesLineup.size
    }

    override fun onBindViewHolder(holder: SquadPlayerViewHolder, position: Int) {
        val player = fixturesLineup[position]
        holder.playerName.text = player.players_name
        holder.playerPosition.text = player.players_position

        Glide
            .with(holder.itemView.context)
            .load(player.players_image)
            .fitCenter()
            .thumbnail(
                Glide.with(holder.itemView.context)
                    .load(R.drawable.player_avater)
            )
            .into(holder.itemView.findViewById(R.id.squad_player_image))

        // save player id in shared preferences
        holder.itemView.setOnClickListener {
            val utilsSharedPreferences = UtilsSharedPreferences(it.context)
            utilsSharedPreferences.saveData(PLAYER_ID, player.players_id)
            val bundle = bundleOf("title" to player.players_name)
            it.findNavController().navigate(R.id.playerInfoFragment, bundle)
        }
    }
}