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
import com.saiful.cricketapp.R
import com.saiful.cricketapp.model.PlayerName
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.PLAYER_ID

class PlayerAdapter(private val players: List<PlayerName>) :
    RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerName: TextView = view.findViewById(R.id.player_name)
        val playerCountryName: TextView = view.findViewById(R.id.player_country_name)
        val playerImage: ImageView = view.findViewById(R.id.player_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.player_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.playerName.text = player.fullName
        holder.playerCountryName.text = player.countryName
        holder.playerImage.contentDescription = player.fullName
        Glide
            .with(holder.itemView.context)
            .load(player.imagePath)
            .fitCenter()
            .thumbnail(
                Glide.with(holder.itemView.context)
                    .load(R.drawable.player_avater)
            )
            .error(R.drawable.ic_no_image)
            .into(holder.itemView.findViewById(R.id.player_image))

        // save player id in shared preferences
        holder.itemView.setOnClickListener {
            val utilsSharedPreferences = UtilsSharedPreferences(it.context)
            utilsSharedPreferences.saveData(PLAYER_ID, player.id.toString())
            val bundle = bundleOf("title" to player.fullName)
            it.findNavController().navigate(R.id.playerInfoFragment, bundle)
        }
    }
}