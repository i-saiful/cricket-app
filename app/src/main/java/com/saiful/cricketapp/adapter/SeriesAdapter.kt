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
import com.saiful.cricketapp.database.entity.Leagues
import com.saiful.cricketapp.sharedPreferences.UtilsSharedPreferences
import com.saiful.cricketapp.util.Constant.Companion.LEAGUE

class SeriesAdapter(val leagues: List<Leagues>) :
    RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {
    class SeriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val seriesName: TextView = view.findViewById(R.id.series_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.series_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return leagues.size
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val series = leagues[position]
        holder.seriesName.text = series.name
        Glide
            .with(holder.itemView.context)
            .load(series.image_path)
            .fitCenter()
            .thumbnail(
                Glide.with(holder.itemView.context)
                    .load(R.drawable.flag_loading)
            )
            .into(holder.itemView.findViewById(R.id.series_image))

        holder.itemView.setOnClickListener {
            val utilsSharedPreferences = UtilsSharedPreferences(it.context)
            utilsSharedPreferences.saveData(LEAGUE, series.id.toString())
            val bundle = bundleOf("title" to "${series.name} Matches")
            it.findNavController().navigate(R.id.leaguesFragment, bundle)
        }
    }
}